//https://zio.dev/docs/overview/overview_background
//Immutable data structures that model procedural effects are called functional effects.

sealed trait Console[A] {
  def map[B](f: A => B): Console[B] =
    flatMap(a => succeed(f(a)))

  def flatMap[B](f: A => Console[B]): Console[B] =
    this match {
      case Return(value) => f(value())
      case PrintLine(value, next)  => PrintLine(value, next.flatMap(f))
      case ReadLine(next) => ReadLine(line => next(line).flatMap(f))
    }
}
final case class Return[A](value: () => A) extends Console[A]
final case class PrintLine[A](value: String, rest: Console[A]) extends Console[A]
final case class ReadLine[A](rest: String => Console[A]) extends Console[A]

def myProgram: Console[Unit] =
  PrintLine("What is your name?",
    ReadLine(name =>
      PrintLine(s"Hello $name", Return(() => ()))))

def interpret[A](program: Console[A]): A =
  program match {
    case Return(value) =>
      value()
    case PrintLine(value, rest) =>
      println(value)
      interpret(rest)
    case ReadLine(f) =>
      interpret(f(scala.io.StdIn.readLine()))
  }

//interpret(myProgram)

//Functional composition

def succeed[A](value: => A): Console[A] =
  Return(() => value)
def printLine[A](value: String): Console[Unit] =
  PrintLine(value, succeed(value))
def readLine: Console[String] =
  ReadLine(line => succeed(line))

printLine("What's your name?")
val name = readLine
printLine(s"Hello $name")


for {
  _ <- printLine("What's your name?")
  name <- readLine
  _ <- printLine(s"Hello $name")
} yield name


printLine("hola").flatMap(_ => printLine("mundo"))

readLine.flatMap(in => printLine(in))

java.util.concurrent.ConcurrentHashMap

Runtime.getRuntime.availableProcessors()