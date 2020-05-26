package concurrent.parallel_collections

object MeasuringObjectAllocation extends App {

  case class Dummy()

  @volatile var dummy: Dummy = _

  println(
    (1 to 100)
    .map(_ => warmedTimed() { dummy = Dummy() })
    .sum / 100
  )



}
