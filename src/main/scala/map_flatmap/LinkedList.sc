sealed trait LinkeddList[A] {
  def map[B](fn: A => B): LinkeddList[B] =
    this match {
      case Pair(hd, tl) => Pair(fn(hd), tl.map(fn))
      case End() => End[B]()
    }
  def flatMap[B](fn: A => LinkeddList[B]): LinkeddList[B] = {
    this match {
      case Pair(hd, tl) => Pair(fn(hd), tl.map(fn))
      case End() => End[B]()
    }
  }
}
final case class End[A]() extends LinkeddList[A]
final case class Pair[A](head: A, tail: LinkeddList[A]) extends LinkeddList[A]

val list: LinkeddList[Int] = Pair(1, Pair(2, Pair(3, End())))

list.map(x => x * 2)

val add: Int => Int = x => (x + 1): Int
list.map(add)

list.map(_ / 3)

