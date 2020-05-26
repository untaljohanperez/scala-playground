package concurrent.reactive_extensions

import rx.lang.scala._
import concurrent.JVMConcurrency.log

import scala.io.Source

object ObservableSync extends App {

  val o = Observable.items("Pascal", "Java", "Scala")
  o.subscribe(name => log(s"learned the $name language"))
  o.subscribe(name => log(s"forgot the $name language"))

}

import scala.concurrent.duration._

object ObservablesTimer extends App {
  val o = Observable.timer(1.second)
  o.subscribe(_ => log("Timeout!"))
  o.subscribe(_ => log("Another timeout!"))
  Thread.sleep(2000)
}

object ObservablesExceptions extends App {
  val exc = new RuntimeException
  val o = Observable.items(1, 2) ++
    Observable.error(exc) ++
    Observable.items(3, 4)

  o.subscribe(
    x => log(s"number $x"),
    t => log(s"an error occurred: $t")
  )
}

object ObservablesLifetime extends App {
  val classics = List("Good, bad, ugly", "Titanic", "Die Hard")
  val movies = Observable.from(classics)
  movies.subscribe(new Observer[String] {
    override def onNext(m: String) = log(s"Movies Watchlist - $m")
    override def onError(e: Throwable) = log(s"Ooops - $e!")
    override def onCompleted() = log(s"No more movies.")
  })
}


object ObservablesCreate extends App {
  val vms = Observable.apply[String] { obs =>
    obs.onNext("JVM")
    obs.onNext("DartVM")
    obs.onNext("V8")
    obs.onCompleted()
    Subscription()
  }
  vms.subscribe(log _, e => log(s"oops - $e"), () => log("Done!"))
}

import scala.concurrent._
import ExecutionContext.Implicits.global

object ObservablesCreateFuture extends App {
  val f = Future { "Back to the Future(s)" }
  val o = Observable.create[String] { obs =>
    f foreach { case s => obs.onNext(s); obs.onCompleted() }
    f.failed foreach { case t => obs.onError(t) }
    Subscription()
  }
  o.subscribe(log _)

  val o2 = Observable.from(Future { "Back to the Future(s)" })
}

object CompositionMapAndFilter extends App {
  val odds = Observable.interval(0.5.seconds)
    .filter(_ % 2 == 1)
    .map(n => s"num $n")
    .take(5)

  odds.subscribe(
    log _,
    e => log(s"unexpected $e"),
    () => log("no more odds"))

  Thread.sleep(4000)
}

object ForComprehensionUsage extends App {
  val evens = for (n <- Observable.from(0 until 9); if n % 2 == 0)
    yield s"even number $n"
  evens.subscribe(log _)
}

object HigherOrderEventStream extends App {
  import scala.io.Source
  def fetchQuote(): Future[String] = Future {
    blocking {
      val url = "http://quotes.stormconsultancy.co.uk/random.json?" +
        "show_permalink=false&show_source=false"
      Source.fromURL(url).getLines.mkString
    }
  }

  def fetchQuoteObservable(): Observable[String] = {
    Observable.from(fetchQuote())
  }

  def quotes: Observable[Observable[String]] =
    Observable.interval(0.5 seconds).take(4).map {
      n => fetchQuoteObservable().map(txt => s"$n) $txt")
    }

  log(s"Using concat")
  quotes.concat.subscribe(log _)
  Thread.sleep(6000)
  log(s"Now using flatten")
  quotes.flatten.subscribe(log _)
  Thread.sleep(6000)

  /*
  we need to be careful, because for-comprehensions on Observable objects do not maintain the relative order of the events in the way that the for-comprehensions on collections do. In the proceeding example, as soon as we can pair a n number with some quote txt, the s"$n) $txt" event is emitted, irrespective of the events associated with the preceding n number
   */
  val qs = for {
    n   <- Observable.interval(0.5 seconds).take(5)
    txt <- fetchQuoteObservable()
  } yield s"$n) $txt"
  qs.subscribe(log _)

}

 object CompositionRetry extends App {
   def randomQuote = Observable.create[String] { obs =>
     val url = "http://www.iheartquotes.com/api/v1/random?" +
       "show_permalink=false&show_source=false"
     obs.onNext(Source.fromURL(url).getLines.mkString)
     obs.onCompleted()
     Subscription()
   }

   import Observable._
   def errorMessage = items("Retrying...") ++ error(new Exception)
   def quoteMessage = for {
     text    <- randomQuote
     message <- if (text.size < 100) items(text) else errorMessage
   } yield message

   quoteMessage.retry(5).subscribe(log _)
   Thread.sleep(2500)
 }

object CompositionScan extends App {
  CompositionRetry.quoteMessage
    .retry
    .repeat
    .take(100)
    .scan(0) {
      (n, q) => if (q == "Retrying...") n + 1 else n
    } subscribe(n => log(s"$n / 100"))
}

object CompositionErrors extends App {
  import Observable._
  val status = items("ok", "still ok") ++ error(new Exception) ++ items("last never printed")

  val fixedStatus =
    status.onErrorReturn(_ => "exception occurred.")
  fixedStatus.subscribe(log _)

 val continuedStatus =
    status.onErrorResumeNext(_ => items("better", "much better"))
  continuedStatus.subscribe(log _)

}
