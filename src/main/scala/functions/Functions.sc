sealed trait IntList {
  def length: Int = fold[Int](0, (_, b) => 1 + b)
  def product: Int = fold[Int](1, (a, b) => a * b)
  def sum: Int = fold[Int](0, (a, b) => a + b)
  def double: IntList = fold[IntList](End, (a, b) => Pair(a * 2, b))

  def fold[A](end: A, f: (Int, A) => A): A =
    this match {
      case End => end
      case Pair(hd, tl) => f(hd, tl.fold(end, f))
    }
}
final case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

val example = Pair(1, Pair(2, Pair(3, End)))

example.double
