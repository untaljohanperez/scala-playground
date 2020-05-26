def sum(optionA: Option[Int], optionB: Option[Int]): Option[Int] =
  optionA.flatMap(a => optionB.map(b => a + b))

Option(1).flatMap(x => Option(2).map(y => y + x))

def addOptions(a: Option[Int], b: Option[Int]): Option[Int] =
  for {
    x <- a
    y <- b
  } yield x + y

addOptions(Some(1), Some(1))

def addAllOptions(a: Option[Int], b: Option[Int], c: Option[Int]): Option[Int] =
  for {
    x <- a
    y <- b
    z <- c
  } yield x + y + z

def SumAllOptions(a: Option[Int], b: Option[Int], c: Option[Int]): Option[Int] =
  a.flatMap(x => b.flatMap(y => c.map(z => x + y + z)))

def divide(a: Int, b: Int): Option[Int] =
  if (b == 0)
    None
  else
    Some(a / b)

def divideOptions(a: Option[Int], b: Option[Int]): Option[Int] =
  for {
    x <- a
    y <- b
    z <- divide(x, y)
  } yield z

def readInt(str: String): Option[Int] = if(str matches "\\d+") Some(str.toInt) else None

def calculator(operand1: String, operator: String, operand2: String): Unit = {
  val result = for {
    x <- readInt(operand1)
    y <- readInt(operand2)
    ans <- operator match {
      case "+" => Some(x + y)
      case "-" => Some(x - y)
      case "*" => Some(x * y)
      case "/" => divide(x, y)
      case _ => None
    }
  } yield ans

  println("The result is " + result.getOrElse("undefined"))
}

calculator("1", "+", "2")
calculator("1", "/", "0")

import scala.util.Try
val opt1 = Some(1)
val opt2 = Some(2)
val opt3 = Some(3)
val seq1 = Seq(1)
val seq2 = Seq(2)
val seq3 = Seq(3)
val try1 = Try(1)
val try2 = Try(2)
val try3 = Try(3)


for {
  a <- opt1
  b <- opt2
  c <- opt3
} yield a + b + c






