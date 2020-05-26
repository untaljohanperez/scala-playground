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

sealed trait Expr {
  def eval: Sum[String, Double] =
    this match {
      case Add(left, right) => left.eval.flatMap(a => right.eval.map(b => a + b))
      case Sub(left, right) => left.eval.flatMap(a => right.eval.map(b => a - b))
      case Division(left, right) => right.eval.flatMap(b =>
        if (b == 0)
          Failure("Division by zero")
        else
          left.eval.map(a => a / b)
      )
      case SquareRoot(value) => value.eval.flatMap(a =>
        if (a < 0)
          Failure("Square root of negative number")
        else
          Success(Math.sqrt(a))
      )
      case Number(value) => Success(value)
    }
}
final case class Add(left: Expr, right: Expr) extends Expr
final case class Sub(left: Expr, right: Expr) extends Expr
final case class Division(left: Expr, right: Expr) extends Expr
final case class SquareRoot(value: Expr) extends Expr
final case class Number(value: Double) extends Expr


assert(Add(Number(1), Number(2)).eval == Success(3))
assert(SquareRoot(Number(-1)).eval == Failure("Square root of negative number"))
assert(Division(Number(4), Number(0)).eval == Failure("Division by zero"))
assert(Division(Add(Sub(Number(8), Number(6)), Number(2)), Number(2)).eval == Success (2.0))
