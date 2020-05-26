package concurrent.parallel_collections

object ParallelCollections extends App {
  import scala.collection._
  import scala.util.Random

  val numbers = Random.shuffle(Vector.tabulate(5000000)(i => i))
  val seqtime = timed { numbers.max }
  println(s"Sequential time $seqtime ms")
  val partime = timed { numbers.par.max }
  println(s"Parallel time $partime ms")


}
