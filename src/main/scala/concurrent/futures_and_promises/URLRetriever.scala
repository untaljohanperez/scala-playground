package concurrent.futures_and_promises

import scala.concurrent._
import scala.io.Source
import scala.concurrent.duration._
import java.util._

/*
Implement a command-line program that asks the user to input a URL of some website,
and displays the HTML of that website. Between the time that the user hits ENTER and
the time that the HTML is retrieved, the program should repetitively print a . to the standard4
 output every 50 milliseconds, with a 2 second timeout. Use only futures and promises,
  and avoid synchronization primitives from the previous chapters. You may reuse the timeout method
  defined in this chapter.
 */

object URLRetriever {
  import ExecutionContext.Implicits.global

  implicit class FutureOps[T](self: Future[T]) {
    def or(other: Future[T]): Future[T] = {
      val promise = Promise[T]
      self.onComplete { case x => promise tryComplete x }
      other.onComplete { case x => promise tryComplete x }
      promise.future
    }
  }

  private val timer = new Timer(true)

  def getUrl(url: String): Future[String]  = Future {
    val page = Source.fromURL(url)
    try page.getLines().take(3).mkString("\n") finally page.close()
  }

  def timeout(t: Long): Future[String] = {
    val p = Promise[String]
    timer.schedule(new TimerTask {
      def run(): Unit = {
        p success "timeout!"
        timer.cancel()
      }
    }, t)
    p.future
  }


  def loadingBar: Future[String]  = Future {
    blocking {
      while (true) {
        print(".")
        Thread.sleep(20)
      }
    }
    ""
  }

  def loadingBarWithTimeOut(int: Int): Future[String] = loadingBar or timeout(int)

}


object URLRetrieverApp extends App {

  import URLRetriever._

  println("Welcome!")
  println("Please, enter and url: ")
  val url = "http://www.w3.org/Addressing/URL/url-spec.txt" //scala.io.StdIn.readLine()

  val result = loadingBarWithTimeOut(2000) or getUrl(url)

  println(Await.result(result, Duration.Inf))
}
