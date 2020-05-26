package concurrent.JVMConcurrency

import scala.annotation.tailrec
import scala.collection.mutable

object PriorityTaskPoolApp extends  App {

  class PriorityTaskPool(workers: Int, important: Int) {
    private val tasks = mutable.PriorityQueue.empty[(Int, () => Unit)](Ordering.by(_._1))

    class Worker extends Thread {
      setDaemon(true)
      def poll(): () => Unit = tasks.synchronized {
        while (tasks.isEmpty) tasks.wait()
        tasks.dequeue()_2
      }
      override def run(): Unit = while(true) {
        val task = poll()
        task()
      }
    }

    def asynchronous(priority: Int)(task: =>Unit): Unit = tasks.synchronized {
      tasks.enqueue((priority, () => task))
      tasks.notify()
    }

    def start(): Unit = {
      (1 to workers).foreach { _ =>
        new Worker().start()
      }
    }

    def shutdown(): Unit = tasks.synchronized {
      val importantTasks = tasks.filter(_._1 > important)
      tasks.dequeueAll
      tasks ++= importantTasks
      tasks.notifyAll()
    }

  }

  val threadPool = new PriorityTaskPool(workers = 2, important = 4)
  threadPool.asynchronous(5) { log("Hello") }
  threadPool.asynchronous(2) { log("Bye") }
  threadPool.asynchronous(5) { log("Uranus!") }
  threadPool.asynchronous(1) { log("Pluto!") }

  threadPool.start()

  threadPool.shutdown()

  Thread.sleep(2000)
}
