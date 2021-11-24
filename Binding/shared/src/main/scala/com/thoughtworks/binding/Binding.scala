package com.thoughtworks.binding

import scalaz.-\/
import scalaz.Applicative
import scalaz.DList
import scalaz.Equal
import scalaz.FingerTree
import scalaz.Free
import scalaz.IList
import scalaz.Memo
import scalaz.Monad
import scalaz.MonadPlus
import scalaz.Monoid
import scalaz.Nondeterminism
import scalaz.Reducer
import scalaz.StreamT
import scalaz.StreamT.Done
import scalaz.StreamT.Skip
import scalaz.StreamT.Yield
import scalaz.UnitReducer
import scalaz.\/-

import scala.concurrent.Future

object Binding:
  opaque type BindingT[M[_], A] <: StreamT[M, A] = StreamT[M, A]
  object BindingT:
    given [M[_]](using M: Nondeterminism[M]): Monad[[X] =>> BindingT[M, X]] with
      def point[A](a: => A) = StreamT.StreamTMonadPlus.point(a)
      def bind[A, B](fa: BindingT[M, A])(f: A => BindingT[M, B]): BindingT[M, B] =
        given Equal[B] = Equal.equalA[B]
        fa.flatMapLatest(f).distinctUntilChanged
      override def map[A, B](fa: BindingT[M, A])(f: A => B): BindingT[M, B] =
        given Equal[B] = Equal.equalA[B]
        fa.map(f).distinctUntilChanged

  opaque type BindingSeqT[M[_], A] <: StreamT[M, BindingSeqT.Patch[A]] = StreamT[M, BindingSeqT.Patch[A]]
  object BindingSeqT:
    sealed trait Patch[A]:
      private[BindingSeqT] def withOffset(offset: Int): Patch[A]
      private[BindingSeqT] def sizeIncremental: Int

    object Patch:
      // TODO: Support move items
      // final case class Move[A](oldIndex: Int, offset: Int, moveCount: Int) extends Patch[A]
      final case class Splice[A](index: Int, deleteCount: Int, newItems: Iterable[A]) extends Patch[A]:
        private[BindingSeqT] def withOffset(offset: Int) = copy(index = index + offset)
        private[BindingSeqT] def sizeIncremental = newItems.size - deleteCount

    given [M[_]](using M: Nondeterminism[M]): Monad[[X] =>> BindingSeqT[M, X]] with
      def point[A](a: => A): BindingSeqT[M, A] =
        Applicative[[X] =>> StreamT[M, X]].point(Patch.Splice[A](0, 0, List(a)))

      /** Returns a new data-binding sequence by applying a function to all elements of this sequence and using the
        * elements of the resulting collections.
        *
        * Whenever `fa` or one of the subsequence changes, the result sequence changes accordingly. The time complexity
        * to update the result sequence, when `fa` changes, is `O(log(n) + c)`, where `n` is the size of `fa`, and `c`
        * is number of elements in `fa`, mapped to mutable subsequences by `f`, of which the indices in `fa` are
        * changing. For example, if the size of `fa` is 10 and 4 elements of them mapped to mutable subsequences, when
        * an element is prepended to `fa`, `c` is 4. However when an element is appended to `fa`, `c` is zero, because
        * no element's index in `fa is changed.
        *
        * The time complexity to update the result sequence, when one of the subsequence changes, is `O(log(n))`, where
        * `n` is the size of `fa`.
        */
      def bind[A, B](fa: BindingSeqT[M, A])(f: A => BindingSeqT[M, B]): BindingSeqT[M, B] =
        val toStepB = { (a: A) =>
          f(a).step
        }
        type EventStep[A] = StreamT.Step[Patch[A], BindingSeqT[M, A]]
        final case class SliceEvent(eventStep: EventStep[B], sliceIndex: Int)
        final case class Measure(
            numberOfSlices: Int,
            numberOfElements: Int,
            // TODO: Create an opaque type implementation for DList for better performance
            sliceEventBuilder: Option[(numberOfPreviousSlices: Int) => M[SliceEvent]]
        )
        sealed trait Slice:
          def size: Int
        final case class ActiveSlice(size: Int, eventQueue: M[EventStep[B]]) extends Slice
        final case class InactiveSlice(size: Int) extends Slice
        val emptyEventBuilder = { (numberOfPreviousSlices: Int) => DList.mkDList[M[SliceEvent]](Free.pure) }
        given Monoid[Measure] with
          def zero = Measure(0, 0, None)
          def append(f1: Measure, f2: => Measure) =
            val numberOfSlices1 = f1.numberOfSlices
            Measure(
              f1.numberOfSlices + f2.numberOfSlices,
              f1.numberOfElements + f2.numberOfElements,
              (f1.sliceEventBuilder, f2.sliceEventBuilder) match
                case (None, None) =>
                  None
                case (None, Some(builder2)) =>
                  Some(Memo.immutableHashMapMemo { (numberOfPreviousSlices: Int) =>
                    builder2(numberOfPreviousSlices + numberOfSlices1)
                  })
                case (someBuilder1: Some[_], None) =>
                  someBuilder1
                case (Some(builder1), Some(builder2)) =>
                  Some(Memo.immutableHashMapMemo { (numberOfPreviousSlices: Int) =>
                    M.map(
                      M.choose(builder1(numberOfPreviousSlices), builder2(numberOfPreviousSlices + numberOfSlices1))
                    ) {
                      case -\/((sliceEvent1, _)) => sliceEvent1
                      case \/-((_, sliceEvent2)) => sliceEvent2
                    }
                  })
            )
        given Reducer[Slice, Measure] = UnitReducer { (slice: Slice) =>
          Measure(
            1,
            slice.size,
            slice match {
              case InactiveSlice(_) =>
                None
              case ActiveSlice(_, eventQueue) =>
                Some(Memo.immutableHashMapMemo { (numberOfPreviousSlices: Int) =>
                  M.map(eventQueue)(SliceEvent(_, numberOfPreviousSlices))
                })
            }
          )
        }

        def mergeEventQueue(
            upstreamEventQueueOption: Option[M[EventStep[A]]],
            sliceTree: FingerTree[Measure, Slice]
        ): Option[M[EventStep[B]]] =
          def handleUpstreamEvent(upstreamEvent: EventStep[A]): EventStep[B] =
            upstreamEvent match
              case Yield(Patch.Splice(patchIndex, numberOfSlicesDeleted, newItems), s) =>
                // TODO: Avoid unnecessary FingerTree operations to optimize the performance
                val (left, notLeft) = sliceTree.split(_.numberOfSlices < patchIndex)
                val (deleted, right) = notLeft.split(_.numberOfSlices < numberOfSlicesDeleted)
                val Measure(`patchIndex`, mappedIndex, _) = left.measureMonoid
                val Measure(`numberOfSlicesDeleted`, numberOfItemsDeleted, _) = deleted.measureMonoid
                val numberOfSlicesAdd = newItems.size
                val newSliceTree = newItems.foldLeft(left) { (tree, a) =>
                  tree :+ ActiveSlice(0, toStepB(a))
                } <++> right
                val Some(next) = mergeEventQueue(Some(s().step), newSliceTree)
                if (numberOfItemsDeleted > 0) {
                  Yield(Patch.Splice(mappedIndex, numberOfItemsDeleted, Nil), () => StreamT(next))
                } else {
                  Skip(() => StreamT(next))
                }
              case Skip(s) =>
                val Some(next) = mergeEventQueue(Some(s().step), sliceTree)
                Skip(() => StreamT(next))
              case Done() =>
                mergeEventQueue(None, sliceTree) match
                  case Some(next) =>
                    Skip(() => StreamT(next))
                  case None =>
                    Done()
          def handleSliceEvent(sliceEvent: SliceEvent): EventStep[B] =
            val sliceIndex = sliceEvent.sliceIndex
            val (left, ActiveSlice(numberOfElementsInActiveSlice, _), right) =
              sliceTree.split1(_.numberOfSlices < sliceIndex)
            sliceEvent.eventStep match
              case Yield(patch, s) =>
                val newSliceTree =
                  left.add1(ActiveSlice(numberOfElementsInActiveSlice + patch.sizeIncremental, s().step), right)
                val Some(next) = mergeEventQueue(upstreamEventQueueOption, newSliceTree)
                val patchWithOffset = left.measure
                  .map { leftMeasure =>
                    patch.withOffset(leftMeasure.numberOfElements)
                  }
                  .getOrElse(patch)
                Yield(patchWithOffset, () => StreamT(next))
              case Skip(s) =>
                val newSliceTree = left.add1(ActiveSlice(numberOfElementsInActiveSlice, s().step), right)
                val Some(next) = mergeEventQueue(upstreamEventQueueOption, newSliceTree)
                Skip(() => StreamT(next))
              case Done() =>
                val newSliceTree = left <++> right
                mergeEventQueue(upstreamEventQueueOption, newSliceTree) match
                  case Some(next) =>
                    Skip(() => StreamT(next))
                  case None =>
                    Done()
          val chooseSliceEventOption = sliceTree.measure.toOption.flatMap { measure =>
            measure.sliceEventBuilder.map(_(0))
          }
          upstreamEventQueueOption match
            case None =>
              chooseSliceEventOption match
                case None =>
                  None
                case Some(chooseEvent) =>
                  Some(M.map(chooseEvent)(handleSliceEvent))
            case Some(upstreamEventQueue) =>
              chooseSliceEventOption match
                case None =>
                  Some(M.map(upstreamEventQueue) { upstreamEvent =>
                    handleUpstreamEvent(upstreamEvent)
                  })
                case Some(chooseEvent) =>
                  Some(M.map(M.choose(upstreamEventQueue, chooseEvent)) {
                    case \/-((_, sliceEvent)) =>
                      handleSliceEvent(sliceEvent)
                    case -\/((upstreamEvent, _)) =>
                      handleUpstreamEvent(upstreamEvent)
                  })
        val Some(mergedEventQueue) = mergeEventQueue(Some(fa.step), FingerTree.empty)
        StreamT(mergedEventQueue)

  /** The data binding expression of a sequence, essentially an asynchronous stream of patches describing how the
    * sequence is changing.
    */
  type BindingSeq[A] = BindingSeqT[Future, A]

type Binding[A] = Binding.BindingT[Future, A]
