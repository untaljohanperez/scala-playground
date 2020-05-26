sealed trait Tree {
  def sum2: Int
}
final case class Node(left: Tree, right: Tree, value: Int) extends Tree {
  def sum2: Int = value + left.sum2 + right.sum2
}
final case class Leaf(value: Int) extends Tree {
  val sum2: Int = value
}

/* Pattern matching */
def sum(tree: Tree): Int = tree match {
  case Leaf(value) => value
  case Node(left, right, value) => value + sum(left) + sum(right)
}

/*
         5
      2     6
 */

assert(sum(Node(Leaf(2), Leaf(6), 5)) == 13)

/* Polymorphism */
// sum2

assert(Node(Leaf(2), Leaf(6), 5).sum2 == 13)