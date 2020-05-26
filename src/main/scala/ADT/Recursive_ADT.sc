import scala.annotation.tailrec

sealed trait IntList {
  def length: Int = _length(this)
  @tailrec
  private def _length(list: IntList, total: Int = 0): Int = list match {
    case End => total
    case Pair(_, tail) => _length(tail, total + 1)
  }
  def length2: Int
}
final case object End extends IntList {
  val length2: Int = 0
}
final case class Pair(head: Int, tail: IntList) extends IntList {
  def length2: Int =  1 + tail.length2 /*_length2(this)
  @tailrec
  def _length2(list: IntList, total: Int): Int = ???
  // Challenge: make length2 tailrec without using End.length2 */
}

/*
lenght2 is small
but mixes data model with business logic
https://youtu.be/P8jrvyxHodU?t=1816
*/
/*sum*/

val example = Pair(1, Pair(2, Pair(3, End)))

def sum(list: IntList): Int = list match {
  case End => 0
  case Pair(head, tail) => head + sum(tail)
}

@tailrec
def _sum(list: IntList, total: Int = 0): Int = list match {
  case End => total
  case Pair(head, tail) => _sum(tail, head + total)
}
assert(_sum(example) == 6)
assert(_sum(example.tail) == 5)
assert(_sum(End) == 0)

/*length*/

val example2 = Pair(1, Pair(2, Pair(3, End)))
assert(example2.length2 == 3)
assert(example2.tail.length2 == 2)
assert(End.length2 == 0)

assert(example2.length == 3)
assert(example2.tail.length == 2)
assert(End.length == 0)