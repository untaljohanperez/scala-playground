package concurrent.futures_and_promises

import scala.concurrent.Promise
import scala.util.Failure

/*
Implement an abstraction called a single-assignment variable, represented by the IVar class:
When created, the IVar class does not contain a value, and calling apply results in an exception.
 After a value is assigned using the := method, subsequent calls to := throw an exception, and the
 apply method returns the previously assigned value. Use only futures and promises, and avoid the synchronization
  primitives from the previous chapters.
 */

class IVar[T] {
  val value = Promise[T]
  def apply(): T = value.future.value.getOrElse(Failure(new NoSuchElementException)).get
  def :=(x: T): Unit = value success x
}

object IVarApp extends App {
  import concurrent.traditionalbuildingblocks._

  var iVar = new IVar[String]

  execute( iVar := "hello")
  execute { println(iVar()) }

  Thread.sleep(2000)

  execute { println(iVar()) }
  Thread.sleep(2000)


}
