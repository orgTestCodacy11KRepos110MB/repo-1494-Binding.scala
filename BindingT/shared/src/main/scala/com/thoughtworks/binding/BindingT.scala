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
import com.thoughtworks.dsl.Dsl

// Ideally StreamT should be covariant. Mark it as `@unchecked` as a workaround.
opaque type BindingT[M[_], +A] >: StreamT[M, A @uncheckedVariance] <: StreamT[
  M,
  A @uncheckedVariance
] = StreamT[
  M,
  A @uncheckedVariance
]
object BindingT:

  def apply[M[_], A]: StreamT[M, A] =:= BindingT[M, A] =
    summon

  extension [M[_], A](binding: BindingT[M, A])
    // Polyfill of https://github.com/scalaz/scalaz/pull/2249
    def collect[B](
        pf: PartialFunction[A, B]
    )(using M: Functor[M]): BindingT[M, B] =
      StreamT(M.map(binding.step) {
        case Yield(pf(b), s) =>
          Yield(b, () => s().collect(pf))
        case Yield(_, s) =>
          Skip(() => s().collect(pf))
        case Skip(s) =>
          Skip(() => s().collect(pf))
        case Done() =>
          Done()
      })

    def mergeWith(that: BindingT[M, A])(using
        Nondeterminism[M]
    ): BindingT[M, A] =
      (binding: StreamT[M, A]).mergeWith(that: StreamT[M, A])

  def mergeAll[M[_], A](
      streams: Iterable[BindingT[M, A]]
  )(using Nondeterminism[M]): BindingT[M, A] =
    if streams.isEmpty then
      StreamT.empty
    val indexedSeqOps = streams match {
      case indexedSeqOps: IndexedSeqView.SomeIndexedSeqOps[
            BindingT[M, A] @unchecked
          ] =>
        indexedSeqOps
      case _ =>
        streams.toIndexedSeq
    }
    def mergeView(begin: Int, end: Int): BindingT[M, A] =
      if begin + 1 == end then
        indexedSeqOps(begin)
      else
        val middle = (begin + end) / 2
        mergeView(begin, middle).mergeWith(mergeView(middle, end))
    mergeView(0, indexedSeqOps.length)

  def pure[M[_], A](a: A)(using Applicative[M]) = a :: StreamT.empty[M, A]

  given [M[_]](using M: Nondeterminism[M]): Monad[[X] =>> BindingT[M, X]] with
    def point[A](a: => A) = BindingT.pure(a)
    def bind[A, B](upstream: BindingT[M, A])(
        f: A => BindingT[M, B]
    ): BindingT[M, B] =
      given [B]: Equal[B] = Equal.equalA[B]
      // We could avoid the `asInstanceOf` by wrapping `f(_)` like this:
      //   upstream.flatMapLatest(f(_)).distinctUntilChanged
      // but it will create an unnecessary function object.
      // Use `asInstanceOf` anyway
      upstream
        .flatMapLatest(f.asInstanceOf[A => StreamT[M, B]])
        .distinctUntilChanged
    override def map[A, B](upstream: BindingT[M, A])(
        f: A => B
    ): BindingT[M, B] =
      given Equal[B] = Equal.equalA[B]
      upstream.map(f).distinctUntilChanged

  given [M[_], A](using Applicative[M]): Dsl.Lift[A, BindingT[M, A]] = pure(_)
