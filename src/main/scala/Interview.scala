import scala.collection.mutable.ArrayBuffer

/*
Run-length encoding is a fast and simple method of encoding strings.
The basic idea is to represent repeated successive characters as a single count and character.
For example, the string "111122233411" would be encoded as "4132231421".
Write class that will implement Iterator interface and would be able to iterate over the encoded string
to produce decoded result. However, for simplicity, we will only operate with numbers.
Please, note that 0 is a valid number in the sequence.

count - number

Example sequence #1:
1, 3, 4, 1
Example output #1:
31111

Example sequence #2:
4, 8, 2, 7, 1, 4
Example output #2:
8888774
*/

class SuperIterator(iterator: Iterator[String]) {
// SPACE O(n)

  val array = ArrayBuffer.empty[String]

  while (iterator.hasNext) {
    val count = iterator.next()
    val number = iterator.next()
    if (count != "0")
      array ++= (number * count.toInt).split("")
  }

  val superIterator = array.iterator

  def hasNext: Boolean =
    superIterator.hasNext

  def next(): String =
    superIterator.next()

}

class SuperIteratorConstantSpace(iterator: Iterator[String]) {

  // SPACE O(1)
  def hasNext: Boolean = iterator.hasNext

  def next(): String = {
    val count = iterator.next()
    val number = iterator.next()
    if (count != "0")
      number * count.toInt
    else
      next
  }
}


object Interview extends App {

  val superIterator = new SuperIteratorConstantSpace(Iterator("1", "3", "4", "1"))
  while (superIterator.hasNext)
    println(superIterator.next())

  Iterable


/*
  def converter(iterator: Iterator[String]): Iterator[String] = {

    val array = ArrayBuffer.empty[String]

    while (iterator.hasNext) {
      val count = iterator.next()
      val number = iterator.next()
      if (count != "0")
        array ++= (number * count.toInt).split("")
    }
    array.iterator
  }

  val iterator = Iterator("1", "3", "4", "1")

  //converter(iterator).foreach(println(_))

  val iterator2 = Iterator(4, 8, 2, 7, 1, 4).map(_.toString)

  converter(iterator2).foreach(println(_))
*/


}
