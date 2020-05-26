sealed trait LinkeddList[T] {
  def length: Int =
    this match {
      case End() => 0
      case Pair(_, tail) => 1 + tail.length
    }
  def contains(value: T): Boolean =
    this match {
      case End() => false
      case Pair(head, tail) =>
        if (head == value)
          true
        else
          tail.contains(value)
    }
  //The key insight is that if the index is zero, we’re selecting the current element
  def apply(nth: Int): Result[T] =
    this match {
      case End() => Failure("Index out of bounds, bitch")
      case Pair(head, tail) =>
        if(nth == 0)
          Success(head)
        else
          tail(nth  - 1)
    }

    // Implementation using state :palmface
  /*def find(nth: Int, count: Int = 0): T =
    this match {
      case End() => throw new Exception("No such element bitch!")
      case Pair(head, tail) =>
        if(nth == count)
          head
        else
          tail.find(nth, count + 1)
    }*/
}
final case class End[T]() extends LinkeddList[T]
final case class Pair[T](head: T, tail: LinkeddList[T]) extends LinkeddList[T]

sealed trait Result[A]
case class Success[A](result: A) extends Result[A]
case class Failure[A](reason: String) extends Result[A]

val example = Pair(1, Pair(2, Pair(3, End())))
assert(example.length == 3)
assert(example.tail.length == 2)
assert(End().length == 0)


assert(example.contains(3) == true)
assert(example.contains(4) == false)
assert(End().contains(0) == false)
// This should not compile
// example.contains("not an Int")

assert(example(0) == Success(1))
assert(example(1) == Success(2))
assert(example(2) == Success(3))
assert(example(3) == Failure("Index out of bounds, bitch"))
