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
import scala.collection.IndexedSeqView
import scalaz.StreamT.Step
import scala.annotation.unchecked.uncheckedVariance
import scalaz.Functor
import com.thoughtworks.dsl.keywords.Suspend
import com.thoughtworks.dsl.Dsl
import com.thoughtworks.binding.StreamT.*

// Ideally StreamT should be covariant. Mark it as `@unchecked` as a workaround.
opaque type CovariantStreamT[M[_], +A] >: StreamT[
  M,
  A @uncheckedVariance
] <: StreamT[
  M,
  A @uncheckedVariance
] = StreamT[
  M,
  A @uncheckedVariance
]
object CovariantStreamT:
  export com.thoughtworks.binding.StreamT.{noSkip, memoize, runStreamT, apply}

  def apply[M[_], A]: StreamT[M, A] =:= CovariantStreamT[M, A] =
    summon

  extension [M[_], A](binding: CovariantStreamT[M, A])

    def mergeWith(that: CovariantStreamT[M, A])(using
        Nondeterminism[M]
    ): CovariantStreamT[M, A] =
      (binding: StreamT[M, A]).mergeWith(that: StreamT[M, A])

  def mergeAll[M[_], A](
      streams: Iterable[CovariantStreamT[M, A]]
  )(using Nondeterminism[M]): CovariantStreamT[M, A] =
    if streams.isEmpty then StreamT.empty
    val indexedSeqOps = streams match {
      case indexedSeqOps: IndexedSeqView.SomeIndexedSeqOps[
            CovariantStreamT[M, A] @unchecked
          ] =>
        indexedSeqOps
      case _ =>
        streams.toIndexedSeq
    }
    def mergeView(begin: Int, end: Int): CovariantStreamT[M, A] =
      if begin + 1 == end then indexedSeqOps(begin)
      else
        val middle = (begin + end) / 2
        mergeView(begin, middle).mergeWith(mergeView(middle, end))
    mergeView(0, indexedSeqOps.length)

  def pure[M[_], A](a: A)(using Applicative[M]) = a :: StreamT.empty[M, A]

  // It should be Applicative[M] once the PR get merged: https://github.com/scalaz/scalaz/pull/2251
  given [M[_], A](using
      M: Monad[M]
  ): Dsl.Lift.OneStep[M[A], CovariantStreamT[M, A]] =
    StreamT.StreamTHoist.liftM(_)

  given [Keyword, Functor[_], Domain, Value](using
      dsl: Dsl.Searching[Keyword, CovariantStreamT[Functor, Domain], Value],
      applicative: Applicative[Functor]
  ): Dsl.Composed[Suspend[Keyword], CovariantStreamT[Functor, Domain], Value] =
    Dsl.Composed {
      (
          keyword: Suspend[Keyword],
          handler: Value => CovariantStreamT[Functor, Domain]
      ) =>
        CovariantStreamT(
          applicative.pure(
            Skip(() => dsl(Suspend.apply.flip(keyword)(), handler))
          )
        )
    }
