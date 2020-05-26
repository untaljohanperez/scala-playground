List(1, 2, 3) match {
  case ::(head, tail) => s"head $head tail $tail"
  case Nil => "empty"
}

Nil match {
  case List(a) => "length 1"
  case Nil => "length 0"
}

List(1, 2, 3) match {
  case Nil => "length 0"
  case a :: Nil => s"length 1 starting $a"
  case a :: b :: Nil => s"length 2 starting $a $b" case a :: b :: c :: _ => s"length 3+ starting $a $b $c"
}

val X = "Foo" // X: String = Foo
val Y = "Bar" // Y: String = Bar
val Z = "Baz" // Z: String = Baz

"Bar" match {
  case X => "It's foo!"
  case Y => "It's bar!"
  case Z => "It's baz!"
}

"Bar" match {
  case X | Y => "It's foo or bar!"
  case Z => "It's baz!"
}

case class Person(name: String, lastName: String)
Person("Dave", "Gurnell") match {
  case p @ Person(_, s) => s"The person $p has the surname $s"
}

object Email {
  def unapply(str: String): Option[(String, String)] = {
    val parts = str.split("@")
    if (parts.length == 2)
      Some((parts(0), parts(1)))
    else
      None
  }
}
"dave@underscore.io" match {
  case Email(user, domain) => List(user, domain)
}

"dave" match {
  case Email(user, domain) => List(user, domain)
  case _ => Nil
}

object Uppercase {
  def unapply(str: String): Option[String] =
    Some(str.toUpperCase)
}

Person("Dave", "Gurnell") match {
  case Person(f, Uppercase(l)) => s"$f $l"
}

object Words {
  def unapplySeq(str: String) =
    Some(str.split(" ").toSeq)
}

"the quick brown fox" match {
  case Words(a, b, c) => s"3 words: $a $b $c"
  case Words(a, b, c, d) => s"4 words: $a $b $c $d"
}

List(1, 2, 3, 4, 5) match {
  case List(a, b, _*) => a + b
}

"the quick brown fox" match {
  case Words(a, b, rest @ _*) => rest
}

object Positive {
  def unapply(arg: Int): Option[Int] =
    if (arg > 0) Some(arg) else None
}

assert( "No" == (0 match { case Positive(_) => "Yes" case _ => "No" }) )
assert( "Yes" == (42 match { case Positive(_) => "Yes" case _ => "No" }) )

object TitleCase {
  def unapply(arg: String): Option[String] =
    Some(arg.split(" ")
      .map(x => x.substring(0, 1).toUpperCase + x.substring(1))
      .mkString(" "))
}

assert(
  "Sir Lord Doctor David Gurnell" ==
    ("sir lord doctor david gurnell" match {
      case TitleCase(str) => str
    })
)

"sir lord doctor david gurnell" match {
  case TitleCase(str) => str
}

