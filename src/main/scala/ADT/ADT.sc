sealed trait Calculation
final case class Success(result: Int) extends Calculation
final case class Failure(reason: String) extends Calculation

object Calculator {
  def +(a: Calculation, b: Int): Calculation = a match {
    case Failure(reason) => Failure(reason)
    case Success(result) => Success(result + b)
  }

  def -(a: Calculation, b: Int): Calculation = a match {
    case Failure(reason) => Failure(reason)
    case Success(result) => Success(result - b)
  }

  def /(a: Calculation, b: Int): Calculation = (a, b) match {
    case (Failure(reason), _) => Failure(reason)
    case (_, 0) => Failure("Division by zero")
    case (Success(b), c) => Success(b / c)
  }
}

assert(Calculator.+(Success(1), 1) == Success(2))
assert(Calculator.-(Success(1), 1) == Success(0))
assert(Calculator.+(Failure("Badness"), 1) == Failure("Badness"))

assert(Calculator./(Success(4), 2) == Success(2))
assert(Calculator./(Success(4), 0) == Failure("Division by zero"))
assert(Calculator./(Failure("Badness"), 0) == Failure("Badness"))
