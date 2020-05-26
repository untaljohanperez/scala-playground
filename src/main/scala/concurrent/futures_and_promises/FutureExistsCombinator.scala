package concurrent.futures_and_promises
import java.io.FileNotFoundException

import scala.concurrent._
import scala.concurrent.duration.Duration

/*
Extend the Future[T] type with the exists method, which takes a predicate and returns a Future[Boolean] object:
The resulting future is completed with true if and only if the original future is completed and the predicate returns
 true, and false otherwise. You can use future combinators, but you are not allowed to create any Promise objects in the implementation.
 */

object FutureExistsApp extends App {

  import ExecutionContext.Implicits.global

  implicit class FutureExists[T](self: Future[T]) {
    def exists(p: T => Boolean): Future[Boolean] =
      self.map(p(_)).recover { case _ => false }
  }

  val f: Future[Int] = Future{
    if(true) throw new FileNotFoundException()
    2
  }

  val g: Future[Boolean] = f exists (_ % 2 == 0)

  val result = Await.result(g, Duration.Inf)
  println(result)

}
