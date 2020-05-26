package algorithms.union_find

import scala.annotation.tailrec

class QuickUnion(n: Int) {

  // O(n)
  val array = Array.tabulate(n)(identity)

  // O(n)
  def union(a: Int, b: Int): Unit = {
    val p = root(a)
    val q = root(b)
    array(p) = q
    println(show)
  }

  @tailrec private def root(a: Int): Int = if(array(a) == a) a else root(array(a))

  // O(n)
  def connected(a: Int, b: Int): Boolean = root(a) == root(b)

  def show: String = array.mkString(" ")

}

object QuickUnionApp extends App {
  val qf = new QuickUnion(10)

  qf.union(4, 3)
  qf.union(3, 8)
  qf.union(6, 5)
  qf.union(9, 4)
  qf.union(2, 1)
  qf.union(8, 9)

}
