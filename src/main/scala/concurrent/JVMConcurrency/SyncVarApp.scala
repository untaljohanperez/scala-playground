package concurrent.JVMConcurrency

import scala.util.Try

class SyncVar[T] {
  var value: Option[T] = None

  def get(): T = value match {
    case Some(x) => {
      value = None
      x
    }
    case None => throw new Exception("No value")
  }

  def put(x: T): Unit = value match {
    case None => value = Some(x)
    case Some(_) => throw new Exception("Already filled")
  }

  def getWait(): T = this.synchronized {
    Try {
      this.notifyAll()
      this.get()
    } getOrElse  {
      while (this.isEmpty)
        this.wait()

      this.notifyAll()
      this.get()
    }
  }

  def putWait(x: T): Unit = this.synchronized {
    Try {
      this.put(x)
      this.notifyAll()
    } getOrElse {
      while (this.nonEmpty)
        this.wait()

      this.notifyAll()
      this.put(x)
    }
  }

  def isEmpty: Boolean = value.isEmpty

  def nonEmpty: Boolean = !isEmpty
}

object BusyAwaiting extends App {
  val container: SyncVar[Int] = new SyncVar[Int]

  val producer = thread {
    (0 until 10).foreach { value =>
      while (container.nonEmpty) {}
      container.put(value)
    }
  }

  val consumer = thread {
    (0 until 10).foreach { _ =>
      while (container.isEmpty) {}
      println(container.get())
    }
  }
}

object WaitGuardedLock extends App {
  val container = new SyncVar[Int]

  val producer = thread {
    (0 until 10).foreach { value =>
      container.putWait(value)
    }
  }

  val consumer = thread {
    (0 until 10).foreach { _ =>
      println(container.getWait())
    }
  }

}


