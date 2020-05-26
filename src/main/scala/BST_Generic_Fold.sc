sealed trait Tree[A] {
  def fold(end: A)(f: (A, A) => A): A =
    this match {
      case Leaf(value) => value
      case Node(left, right) => f(left.fold(end)(f), right.fold(end)(f))
    }
}
final case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

val tree: Tree[String] = Node(
  Node(Leaf("To"), Leaf("iterate")),
  Node(
    Node(Leaf("is"), Leaf("human,")), Node(Leaf("to"), Node(Leaf("recurse"), Leaf("divine")))))
tree.fold("")(_ + " " +  _)

Leaf("To").fold(""){_ + _}

Node(Leaf("hola"), Leaf("Mundo")).fold(""){_ + _}
