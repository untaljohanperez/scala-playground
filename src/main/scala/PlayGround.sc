import scala.collection.mutable
import util.control.Breaks._

/*def hourglassSum(arr: Array[Array[Int]]): Int = {

  def getHourGlassSum(row: Int, col: Int): Int = {
    val values = for {
      i <- row to row + 2
      j <- col to col + 2
    } yield arr(i)(j)
    values.sum - arr(row + 1)(col) - arr(row + 1)(col + 2)
  }

  (for {
    i <- 0 to 3
    j <- 0 to 3
  } yield getHourGlassSum(i, j))
    .max
}

val arr = Array(
Array(1, 1, 1, 0, 0, 0),
Array(0, 1, 0, 0, 0, 0),
Array(1, 1, 1, 0, 0, 0),
Array(0, 0, 2, 4, 4, 0),
Array(0, 0, 0, 2, 0, 0),
Array(0, 0, 1, 2, 4, 0)
)
hourglassSum(arr)


def rotLeft(a: Array[Int], d: Int): Seq[Int] = {
  (if (d <= a.length) 1 to d else 1 to (d - (a.length * d / a.length)))
    .foldLeft(a) {
      (list, _) => list.tail :+ list.head
    }
}

rotLeft(Array(1, 2, 3, 4, 5), 4) // 5, 1, 2, 3, 4

*/

def minimumBribes(q: Array[Int]): String = {
  val count = mutable.Map.empty[Int, Int]

  for {
    i <- 0 until q.length
    j <- i + 1 until q.length
  } {
      //println(s"${q(i)} > ${q(j)}")
      if (q(i) > q(j)) {
        val temp = q(j)
        q(j) = q(i)
        q(i) = temp
        val swaps = count.get(q(j)).getOrElse(0) + 1
        if (swaps > 2)
          return "Too chaotic"
        count(q(j)) = swaps
    }
  }
  count.values.sum.toString
}

minimumBribes(Array(2, 1, 5, 3, 4)) // 3

minimumBribes(Array(2, 5, 1, 3, 4)) // Too chaotic

minimumBribes(Array(5, 1, 2, 3, 7, 8, 6, 4)) // Too chaotic

minimumBribes(Array(1, 2, 5, 3, 7, 8, 6, 4)) // 7

minimumBribes(Array(1, 2, 5, 3, 4, 7, 8, 6)) // 4

type S = String

new S("")



