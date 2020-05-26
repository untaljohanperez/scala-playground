package concurrent

package object JVMConcurrency {
  def thread(body: => Unit): Thread = {
    val t = new Thread(() => body)
    t.start()
    t
  }

  def log(s: String): Unit =
    println(s"${Thread.currentThread().getName}: $s")
}
