package algorithms.union_find

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

class WeightedQuickUnion(n: Int) {

  // O(n)
  val array = ArrayBuffer.tabulate(n)(identity)
  val sizes = ArrayBuffer.tabulate(n) (_ => 1)

  // log(n)
  def union(a: Int, b: Int): Unit = {
    val p = root(a)
    val q = root(b)
    if (p == q) return
    if (sizes(p) < sizes(q)) {
      array(p) = q
      sizes(q) += sizes(p)
    }
    else {
      array(q) = p
      sizes(p) += sizes(q)
    }
    //println(show)
  }

  @tailrec private def root(a: Int): Int =
    if(array(a) == a) a
    else {
      array(a) = array(array(a)) // path compression
      root(array(a))
    }

  // log(n)
  def connected(a: Int, b: Int): Boolean = root(a) == root(b)

  //returns the largest element in the connected component containing a
  def find(a: Int): Int = {
    val r = root(a)
    for (i <- (n - 1) to 0 by -1)
      if(array(i) == r)
        return i
    r
  }

  // O(n) only for debugging purposes
  def show: String = array.mkString(" ") //+ "\n" + sizes.mkString(" ")

}

object WeightedQuickUnionApp extends App {
  val qf = new WeightedQuickUnion(10)

  qf.union(4, 3)
  qf.union(3, 8)
  qf.union(6, 5)
  qf.union(9, 4)
  qf.union(2, 1)
  qf.union(5, 0)
  qf.union(7, 2)
  qf.union(6, 1)
  qf.union(6, 1)
  qf.union(7, 3)
  qf.union(6, 4)

  println(qf.find(3))

}
