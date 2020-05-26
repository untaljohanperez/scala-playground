implicit class IntOps(i: Int) {
  def yeah(): Unit = {
    times(_ => println("Oh yeah!"))
  }

  def times(f: Int => Unit): Unit =
    (1 to i).foreach(f(_))
}

3.yeah

(-1).yeah

3.times(i => println(s"Look - it's the number $i!"))

