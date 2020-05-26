sealed trait LinkeddList[+T]  {
  def fold[A](end: A, f: (T, A) => A): A =
    this match {
      case End() => end
      case Pair(hd, tl) => f(hd, tl.fold(end, f))
    }
}
final case class End[T]() extends LinkeddList[T]
final case class Pair[T](head: T, tail: LinkeddList[T]) extends LinkeddList[T]

val example = Pair(1, Pair(2, Pair(3, End())))

val length: (Int, Int) => Int = (_, b) => 1 + b
example.fold(0, length)

val sum: (Int, Int) => Int = (a, b) => a + b
example.fold(0, sum)

val double: (Int, LinkeddList[Int]) => LinkeddList[Int] = (a, b) => Pair(a * 2, b)
example.fold(End(), double)
