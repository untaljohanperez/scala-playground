sealed trait Maybe[+A] {
  def fold[B](default: B)(f: A => B): B =
    this match {
      case Full(value) => f(value)
      case Empty => default
    }

  def map[B](f: A => B) : Maybe[B] =
    this match {
      case Empty => Empty
      case Full(value) => Full(f(value))
    }

  def flatMap[B](f: A => Maybe[B]): Maybe[B] =
    this match {
      case Empty => Empty
      case Full(value) => f(value)
    }

  def mapInTermsOfFlatMap[B](f: A => B): Maybe[B] =
    flatMap(a => Full(f(a)))
}
final case class Full[A](value: A) extends Maybe[A]
final case object Empty extends Maybe[Nothing]

val perhaps: Maybe[Int] = Empty
val perhaps2: Maybe[Int] = Empty
val perhaps3: Maybe[String] = Full(1).map(_.toString)

val listMaybe = List(Full(3), Full(2), Full(1))
listMaybe.map(a => a.flatMap[Int](a => if (a % 2 == 0) Full(a) else Empty))
