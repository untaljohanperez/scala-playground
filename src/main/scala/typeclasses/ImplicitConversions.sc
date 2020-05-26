class SuperInt(i: Int) {
  def times(f: Int => Unit): Unit =
    (1 to i).foreach(f(_))
}

implicit def intToSuperInt(i: Int): SuperInt = new SuperInt(i)

3.times(i => println(s"Look - it's the number $i!"))
