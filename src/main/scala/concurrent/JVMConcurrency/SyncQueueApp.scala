package concurrent.JVMConcurrency

import scala.util.Try

class SyncQueue[T](n: Int) {
  val queue = scala.collection.mutable.Queue.empty[T]

  def isEmpty: Boolean = this.synchronized(queue.isEmpty)
  def nonEmpty: Boolean = this.synchronized(!isEmpty)
  def isFull: Boolean = this.synchronized(queue.size >= n)

  def put(t: T): Unit = this.synchronized {
    if (this.isFull)
      throw new Exception("Queue fulled")
    else
      queue enqueue t
  }

  def get: T = this.synchronized {
    if (this.isEmpty)
      throw new Exception("Queue Empty")
    else
      queue.dequeue
  }

  def getWait: T = this.synchronized {
    Try {
      this.notifyAll
      this.get
    } getOrElse {
      while (this.isEmpty)
        this.wait()

      this.notifyAll
      this.get
    }
  }

  def putWait(t: T): Unit = this.synchronized {
    Try {
      this.put(t)
      this.notifyAll
    } getOrElse {
      while(this.isFull)
        this.wait

      this.put(t)
      this.notifyAll
    }
  }
}

object BusyWaiting extends App {

  val container = new SyncQueue[Int](5)

  val producer = thread {
    (0 to 10).foreach { value =>
      while (container.isFull) {}
      container.put(value)
    }
  }

  val consumer = thread {
    (0 to 10).foreach { _ =>
      while (container.isEmpty) {}
      println(container.get)
    }
  }

  consumer.join()
}

object WaitingGuardedLock extends App {

  val container = new SyncQueue[Int](5)

  val producer = thread {
    (0 to 10).foreach { value =>
      container.putWait(value)
    }
  }

  val consumer = thread {
    (0 to 10).foreach { _ =>
      println(container.getWait)
    }
  }

  consumer.join()

}