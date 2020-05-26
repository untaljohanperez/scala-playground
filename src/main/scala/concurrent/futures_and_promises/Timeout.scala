package concurrent.futures_and_promises

import scala.concurrent.Future

/*
Implement another version of the timeout method shown in this chapter, but without using the
blocking construct or Thread.sleep. Instead use the java.util.Timer class from the JDK.
What are the advantages of this new implementation?
*/

object Timeout {

  def timeout(t: Long): Future[String] = ???

}
