package concurrent

import scala.collection.GenIterable

package object parallel_collections {

  @volatile var dummy: Any = _
  def timed[T](body: =>T): Double = {
    val start = System.nanoTime
    dummy = body
    val end = System.nanoTime
    ((end - start) / 1000) / 1000.0
  }

  def warmedTimed[T](n: Int = 200)(body: =>T): Double = {
    for (_ <- 0 until n) body
    timed(body)
  }

  GenIterable
  Traversable
}
