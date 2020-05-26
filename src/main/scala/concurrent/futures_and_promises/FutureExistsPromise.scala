package concurrent.futures_and_promises

import java.io.FileNotFoundException

import scala.concurrent._
import scala.concurrent.duration.Duration

/*
Repeat the previous exercise, but use Promise objects instead of future combinators.
 */

object FutureExistsPromiseApp extends App {

  import ExecutionContext.Implicits.global

  implicit class FutureExists[T](self: Future[T]) {
    def exists(p: T => Boolean): Future[Boolean] = {
      val promise = Promise[Boolean]
      self.onComplete { x => promise.complete(x.map(p(_))) }
      promise.future.recover { case _ => false }
    }
  }

  val f: Future[Int] = Future {
    //if(true) throw new FileNotFoundException()
    2
  }

  val g: Future[Boolean] = f exists (_ % 2 == 0)

  val result = Await.result(g, Duration.Inf)
  println(result)

}
