package algorithms.union_find

import scala.util.Try

//https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php

class Percolation(val n: Int) {

  case class Site(n: Int, open: Boolean = false)

  val TOP_VIRTUAL_NODE = n * n
  val BOTTOM_VIRTUAL_NODE = n * n + 1
  var openSites = 0

  // creates n-by-n grid, with all sites initially blocked
  val array: Array[Array[Site]] = Array.tabulate(n, n) { (row, col) => Site(col + (n * row)) }
  val qu = new WeightedQuickUnion((n * n) + 2)


  // opens the site (row, col) if it is not open already
  def open(row: Int, col: Int): Unit = {
    val site = array(row)(col)
    if (site.open) return
    array(row)(col) = site.copy(open=true)
    openSites += 1
    connectSiteToOpenNeighbor(site, row - 1, col)
    connectSiteToOpenNeighbor(site, row, col - 1)
    connectSiteToOpenNeighbor(site, row, col + 1)
    connectSiteToOpenNeighbor(site, row + 1, col)
    if (row == 0) qu.union(TOP_VIRTUAL_NODE, site.n)
    if (row == array.length - 1) qu.union(BOTTOM_VIRTUAL_NODE, site.n)
  }

  private def connectSiteToOpenNeighbor(site: Site, row: Int, col: Int): Unit = Try {
    val neighbor = array(row)(col)
    if (neighbor.open)
      qu.union(neighbor.n, site.n)
  }

  // is the site (row, col) open?
  def isOpen(row: Int, col: Int): Boolean = array(row)(col).open

  // is the site (row, col) full?
  def isFull(row: Int, col: Int): Boolean =
    isOpen(row, col) && qu.connected(TOP_VIRTUAL_NODE, array(row)(col).n)

  // returns the number of open sites
  def numberOfOpenSites: Int = openSites

  // does the system percolate?
  def percolates: Boolean = qu.connected(TOP_VIRTUAL_NODE, BOTTOM_VIRTUAL_NODE)

  // O(n2) only for debugging purposes
  def show: String = array.map(x => x.mkString(" ")).mkString("\n")
}

object PercolationApp extends App {
  val percolation = new Percolation(3)
  percolation.open(1, 1)
  percolation.open(0, 1)
  percolation.open(1, 0)
  percolation.open(2, 0)

  println(percolation.show)
  println(percolation.qu.show)

  println(percolation.isFull(1, 0))
  println(percolation.percolates)
}
