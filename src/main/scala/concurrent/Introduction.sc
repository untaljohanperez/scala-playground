import scala.annotation.tailrec
import scala.util.Try

def compose[A, B, C]
(g: B => C, f: A => B): A => C = f andThen(g)

def fuse[A, B]
(a: Option[A], b: Option[B]): Option[(A, B)] =
  for {
    x <- a
    y <- b
  } yield (x, y)

def check[T](xs: Seq[T])(predicate: T => Boolean): Boolean = {
  //functional by inefficient
  //xs.foldLeft(true)((r, x) => Try(predicate(x)).getOrElse(false) && r)

  //imperative by efficient
  Try {
    for (x <- xs) {
      if (!predicate(x))
        return false
    }
    true
  }.getOrElse(false)
}

check(0 until 10)(40 / _ > 0)


def permutations(x: String): Seq[String] = {
  if (x.size == 2) {
    Seq(x.head + x.tail, x.tail + x.head)
  } else {
    val xs: Seq[String] = x.split("")
    var list = Array.empty[String]
    for (i <- 0 until x.size) {
      val head = xs(i)
      val rest = xs.take(i) ++ xs.drop(i + 1)
      list ++= permutations(rest.mkString("")).map(head + _)
    }
    list
  }
}

permutations("ABC")
//ABC, ACB, BAC, BCA, CAB, CBA

def combinations(n: Int, xs: Seq[Int]): Seq[Seq[Int]] = {
  (for {
    i <- 0 until xs.length
    j <- i + 1 until xs.length
  } yield xs(i) +: xs.drop(j).take(n - 1)
    ).filter(_.size == n)
}

combinations(2, Seq(1, 4, 9, 16))
//Seq(1, 4), Seq(1, 9), Seq(1, 16), Seq(4, 9), Seq(4, 16), and Seq(9, 16)


def matcher(regex: String): PartialFunction[String, List[String]] = ???

Thread.currentThread.getName

Thread

import scala.concurrent._

ExecutionContext.global