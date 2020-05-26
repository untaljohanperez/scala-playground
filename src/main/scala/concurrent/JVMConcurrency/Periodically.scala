package concurrent.JVMConcurrency

object Periodically extends App {

  def periodically(duration: Long)(b: =>Unit): Unit = {
    thread {
      (1 to 5).foreach { _ =>
        b
        Thread.sleep(duration)
      }
    }.join()
  }

  periodically(1000) { log("hello") }
}
