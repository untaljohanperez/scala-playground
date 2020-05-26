package concurrent.traditionalbuildingblocks

class LazyCell[T](initialization: =>T) {

  @volatile var initialized = false
  var value: T = _

  def apply(): T = {
    if (initialized)
      value
    else this.synchronized {
      if (!initialized) {
        println("initializing...")
        value = initialization
        initialized = true
      }
      value
    }
  }
}

object LazyCellApp extends App {
  val lazyValue = new LazyCell(1)

  (0 to 15) foreach {
    _ => execute { lazyValue() }
  }

  Thread.sleep(1000)
}
