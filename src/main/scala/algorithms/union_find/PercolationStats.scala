package algorithms.union_find

import scala.collection.parallel.ParSeq
import scala.util.Random

//monte carlo simulation
class PercolationStats(val n: Int, val trial: Int) {

  val simulations: Seq[Percolation] = Seq.tabulate(trial)(_ => new Percolation(n))
  val thresholds: ParSeq[Double] = simulations.par.map(runSimulation(_))

  private def runSimulation(percolation: Percolation): Double = {
    while(!percolation.percolates)
      percolation.open(Random.nextInt(n), Random.nextInt(n))
    percolation.numberOfOpenSites / (n * n).toDouble
  }

  // sample mean of percolation threshold
  def mean: Double = thresholds.sum / thresholds.size

  // sample standard deviation of percolation threshold
  def stddev: Double = ???

  // low endpoint of 95% confidence interval
  def confidenceLo: Double = ???

  // high endpoint of 95% confidence interval
  def confidenceHi: Double = ???
}


object PercolationStatsApp extends App {
 val percolationStats = new PercolationStats(200, 100)
  println(percolationStats.mean)

}
