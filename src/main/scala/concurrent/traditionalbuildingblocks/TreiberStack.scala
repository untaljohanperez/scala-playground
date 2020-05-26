  package concurrent.traditionalbuildingblocks

import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec

class TreiberStack[T] {

  val stack: AtomicReference[List[T]] = new AtomicReference[List[T]](Nil)

  @tailrec final def push(x: T): Unit = {
    val list = stack.get
    val nList = x +: list
    if(!stack.compareAndSet(list, nList)) push(x)
  }

  @tailrec final def pop(): T = {
    val list = stack.get
    val nList = list.tail
    if(stack.compareAndSet(list, nList)) list.head
    else pop()
  }

}


object TreiberStackApp extends App {

  val stack = new TreiberStack[Int]

  execute {
    (0 to 10).foreach(value => stack.push(value))
  }

  Thread.sleep(1000)

  execute {
    (0 to 10).foreach { _ => println(stack.pop()) }
  }

  Thread.sleep(1000)
}
