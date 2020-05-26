def sockMerchant(n: Int, ar: Array[Int]): Int = {
  ar.foldLeft(Map.empty[Int, Int]) {
    (acc, elem) =>
      acc + (elem -> (1 + acc.get(elem).getOrElse(0)))
  }.values
    .foldLeft(0) {
      (acc, value) => acc + (value / 2)
    }
}

sockMerchant(9, Array(10, 20, 20, 10, 10, 30, 50, 10, 20))

def countingValleys(n: Int, s: String): Int = {
  def stepToInt(step: String): Int = if (step == "U") 1 else -1
  s.split("")
    .foldLeft((0, 0)) {
      case ((level, acc), step) => {
        val newLevel = level + stepToInt(step)
        (newLevel, if (newLevel == -1 && level == 0) acc + 1 else acc)
      }
    }._2
}

countingValleys(8, "UDDDUDUU")

def jumpingOnClouds(c: Array[Int]): Int = {
  def jumping(jumps: Int, path: Array[Int]): Int = {
    if (path.isEmpty)
      jumps - 1
    else if (path.head == 1)
      jumping(jumps, path.tail)
    else if(!path.tail.isEmpty && path.tail.head == 0 && !path.tail.tail.isEmpty && path.tail.tail.head == 0)
      jumping(jumps + 1, path.tail.tail)
    else
      jumping(jumps + 1, path.tail)
  }
  jumping(0, c)
}

jumpingOnClouds(Array(0, 0, 1, 0, 0, 1, 0)) // 4

jumpingOnClouds(Array(0, 0, 0, 0, 1, 0)) // 3

jumpingOnClouds(Array(0, 0, 0, 1, 0, 0)) // 3

jumpingOnClouds(Array(0, 1, 0, 0, 0, 1, 0)) //3

def repeatedString(s: String, n: Long): Long = {
  val letters = s.split("")
  val numberOfAs = letters.filter(_ == "a").size

  val chunks = n / s.length
  val count = numberOfAs * chunks

  if (n % s.length == 0)
    return count

  val numberOfLetterLeft = n - (s.length * chunks)

  letters
    .take(numberOfLetterLeft.toInt)
    .foldLeft(count) {
      (acc, elem) => if (elem == "a") acc + 1 else acc
    }
}

repeatedString("aba", 10) // 7

repeatedString("a", 1000000000000L) // 1000000000000
