package concurrent

import scala.concurrent._

package object traditionalbuildingblocks {

  def execute(body: =>Unit): Unit = ExecutionContext.global.execute(
    new Runnable { def run(): Unit = body }
  )
}
