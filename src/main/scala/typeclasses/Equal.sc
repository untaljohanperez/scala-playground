trait Equal[A] {
  def equal(x: A, y: A): Boolean
}

object Equal {
  def apply[A](implicit instance: Equal[A]): Equal[A] =
    instance
}

implicit class EqualOps[A](x: A) {
  def ===(y: A)(implicit instance: Equal[A]): Boolean =
    instance.equal(x, y)
}

case class Person(name: String, email: String)

object EmailEqualImplicit {
  implicit object EmailEqual extends Equal[Person] {
    def equal(x: Person, y: Person): Boolean =
      x.email == y.email
  }
}

object NameAndEmailImplicit {
  implicit val instance = new Equal[Person] {
    def equal(x: Person, y: Person): Boolean =
      x.email == y.email && x.name == y.name
  }
}

object Eq {
  def apply[A](x: A, y: A)(implicit instance: Equal[A]): Boolean = instance.equal(x, y)
}

import EmailEqualImplicit._
Eq(Person("Noel", "noel@example.com"), Person("Noel", "noel@example.com"))

import NameAndEmailImplicit._
Equal[Person].equal(Person("Noel", "noel@example.com"), Person("Noel", "noel@example.com"))

Person("Noel", "noel@example.com") === Person("Noel", "noel@example.com")


implicitly[Equal[Person]]