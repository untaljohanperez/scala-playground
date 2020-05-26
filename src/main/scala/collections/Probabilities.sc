final case class Distribution[A](events: List[(A, Double)]) {
  def map[B](f: A => B): Distribution[B] = Distribution(
    events.map { case (a, p) => f(a) -> p }
  )

  def flatMap[B](f: A => Distribution[B]): Distribution[B] = Distribution(
    events.flatMap { case (a, p) =>
      f(a).events map { case (b, p2) => b -> (p * p2) }
    }
  )
}

object Distribution {
  def uniform[A](list: List[A]): Distribution[A] = {
    val p = 1.0 / list.length
    Distribution[A](list.map(a => a -> p))
  }
}

