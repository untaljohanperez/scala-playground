package concurrent.futures_and_promises

import scala.concurrent._
import scala.concurrent.duration.Duration

class DAG[T](val value: T) {
  val edges = scala.collection.mutable.Set.empty[DAG[T]]
}

object DAGApp extends App {

  import ExecutionContext.Implicits.global

  def fold[T, S](g: DAG[T])(f: (T, Seq[S]) => S): Future[S] = {
    if (g.edges.isEmpty)
      Future { f(g.value, Seq.empty[S]) }
    else blocking {
        val futures: Seq[Future[S]] = g.edges.foldLeft(Seq.empty[Future[S]]) { (acc, elem) => acc :+ fold(elem)(f) }
        val ss: Seq[S] = futures.foldLeft(Seq.empty[S]) { (acc, elem) => acc :+ Await.result(elem, Duration.Inf) }
        Future { f(g.value, ss) }
      }
  }

  val a = new DAG("a")
  val b = new DAG("b")
  val c = new DAG("c")
  val d = new DAG("d")
  val e = new DAG("e")

  a.edges += b
  b.edges += c
  b.edges += d
  c.edges += e
  d.edges += e

  val future: Future[String] = fold(a)((t: String, s: Seq[String]) => (t +: s).mkString("|", "-", "|") )

  println(Await.result(future, Duration.Inf))

}