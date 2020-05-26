package concurrent.JVMConcurrency

object Parallel extends App {

  def parallel[A, B](a: =>A, b: =>B): (A, B) = {
    var result1: A = null.asInstanceOf[A]
    var result2: B = null.asInstanceOf[B]
    val t1 = thread { result1 = a }
    val t2 = thread { result2 = b }
    t1.join()
    t2.join
    (result1, result2)
  }

  println(parallel("hello", 123))
}
