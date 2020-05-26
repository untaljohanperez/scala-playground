sealed trait Sum[+A, +B] {
  def fold[C](fa: A => C, fb: B => C): C =
    this match {
      case Failure(value) => fa(value)
      case Success(value) => fb(value)
    }
  def map[C](f: B => C): Sum[A, C] =
    this match {
      case Failure(value) => Failure(value)
      case Success(value) => Success(f(value))
    }
  def flatMap[AA >: A, C](f: B => Sum[AA, C]): Sum[AA, C] =
    this match {
      case Failure(value) => Failure(value)
      case Success(value) => f(value)
    }
}
final case class Failure[A, B](value: A) extends Sum[A, B]
final case class Success[A, B](value: B) extends Sum[A, B]

Failure[Int, String](1).value
Success[Int, String]("foo").value
val sum: Sum[Int, String] = Success("foo")

sum match {
  case Failure(x) => x.toString
  case Success(x) => x
}

// Either -> generic sum type

