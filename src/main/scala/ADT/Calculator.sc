sealed trait Expr {
  def eval: Calculation =
    this match {
      case Number(value) => Success(value)
      case Add(left, right) =>
        left.eval match {
          case Failure(reason) => Failure(reason)
          case Success(a) =>
            right.eval match {
              case Failure(reason) => Failure(reason)
              case Success(b) => Success(a + b)
            }
        }
      case Sub(left, right) =>
        left.eval match {
          case Failure(reason) => Failure(reason)
          case Success(a) =>
            right.eval match {
              case Failure(reason) => Failure(reason)
              case Success(b) => Success(a - b)
            }
        }
      case Division(left, right) =>
        left.eval match {
          case Failure(reason) => Failure(reason)
          case Success(a) =>
            right.eval match {
              case Failure(reason) => Failure(reason)
              case Success(b) if b > 0 => Success(a / b)
              case _ => Failure("Division by zero")
            }
        }
    }
}
final case class Add(left: Expr, right: Expr) extends Expr
final case class Sub(left: Expr, right: Expr) extends Expr
final case class Division(left: Expr, right: Expr) extends Expr
final case class Number(value: Double) extends Expr

sealed trait Calculation
final case class Success(value: Double) extends Calculation
final case class Failure(reason: String) extends Calculation


assert(Division(Number(4), Number(0)).eval == Failure("Division by zero"))


