package concurrent.futures_and_promises

import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.sys.process._



/*
Implement the spawn method, which takes a command-line string, asynchronously
 executes it as a child process, and returns a future with the exit code of the child process:
def spawn(command: String): Future[Int] =
 Make sure that your implementation does not cause thread starvation.
 */

object SpawnFuture extends App {

  import ExecutionContext.Implicits.global

  def spawn(command: String): Future[Int] = Future {
    blocking { command.! }
  }

  print(Await.result(spawn("java -version"), Duration.Inf))
}
