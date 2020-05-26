package algorithms.union_find

class QuickFind(val n: Int) {

  // O(n)
  val array = Array.tabulate(n)(identity)

  // O(n)
  def union(a: Int, b: Int): Unit = {
    if (connected(a, b)) return
    println(a, b)
    val old = array(a)
    for (x <- array.indices)
      if(array(x) == old)
        array(x) = array(b)
    println(show)
  }

  // O(1)
  def connected(a: Int, b: Int): Boolean = array(a) == array(b)

  def show: String = array.mkString(" ")

}

object QuickFindApp extends App {
  val qf = new QuickFind(10)

  qf.union(4, 3)
  qf.union(3, 8)
  qf.union(6, 5)
  qf.union(9, 4)
  qf.union(2, 1)
  qf.union(8, 9)

}
