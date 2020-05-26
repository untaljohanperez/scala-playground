package concurrent.JVMConcurrency

import scala.collection.mutable

class ConcurrentBiMap[K, V] {
  val keys: mutable.Map[K, V] = mutable.Map.empty[K, V]
  val values: mutable.Map[V, K] = mutable.Map.empty[V, K]

  def put(k: K, v: V): Option[(K, V)] = keys.synchronized {
    values.synchronized {
      val value = keys.put(k, v)
      val key = values.put(v, k)
      return value.flatMap(x => key.map(y => (y, x)))
    }
  }
  def removeKey(k: K): Option[V] = None
  def removeValue(v: V): Option[K] = None
  def getValue(k: K): Option[V] = None
  def getKey(v: V): Option[K] = None
  def size: Int = 0
  def iterator: Iterator[(K, V)] = Iterator.empty
}

object ConcurrentBiMapApp extends App {
  val biMap = new ConcurrentBiMap[String, Int]
  println(biMap.put("a", 1))
  println(biMap.put("a", 2))
}
