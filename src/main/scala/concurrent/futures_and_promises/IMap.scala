package concurrent.futures_and_promises

import scala.concurrent._
import scala.collection.concurrent
import java.util.concurrent.ConcurrentHashMap

import scala.collection.convert.decorateAsScala._
import scala.concurrent.duration.Duration
import scala.util.Try

/*
Implement the IMap class, which represents a single-assignment map:
Pairs of keys and values can be added to the IMap object, but they can never be removed or
modified. A specific key can be assigned only once, and subsequent calls to update with that key
result in an exception. Calling apply with a specific key returns a future, which is completed after
that key is inserted into the map.
In addition to futures and promises, you may use the scala.collection.concurrent.Map class.
*/

class IMap[K, V] {

  val map: concurrent.Map[K, Promise[V]] = new ConcurrentHashMap[K, Promise[V]].asScala

  def update(k: K, v: V): Unit = {
    val promise = Promise[V]
    val prev = map.putIfAbsent(k, promise)
    prev match {
      case None => promise success v
      case Some(p) => p success v
    }
  }

  def apply(k: K): Future[V] =
    map.getOrElseUpdate(k, Promise[V]).future
}

object IMapApp extends App {
  val map = new IMap[String, Int]
  map.update("a", 1)
  Try(map.update("a", 2)).failed.map(println(_))

  val b = map("b")
  map.update("b", 1)
  println(Await.result(b, Duration.Inf))
}
