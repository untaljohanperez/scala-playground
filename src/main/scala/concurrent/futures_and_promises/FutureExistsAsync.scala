package concurrent.futures_and_promises

import java.io.FileNotFoundException

import scala.concurrent._
import scala.concurrent.duration.Duration

import scala.async.Async._

/*Repeat the previous exercise, but use the Scala Async framework.*/

object FutureExistsAsyncApp extends App {

  import ExecutionContext.Implicits.global

  implicit class FutureExists[T](self: Future[T]) {

    def exists(p: T => Boolean): Future[Boolean] = async {
      p(await(self))
    } recover { case _ => false }
  }

  val f: Future[Int] = Future {
    if(true) throw new FileNotFoundException()
    2
  }

  val g: Future[Boolean] = f exists (_ % 2 == 0)

  val result = Await.result(g, Duration.Inf)
  println(result)

}
