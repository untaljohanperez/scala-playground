import scala.collection.LinearSeq

IndexedSeq
LinearSeq

Stream(1, 2, 3)
Stream.empty.#::(3).#::(2).#::(1)
1 #:: 2 #:: 3 #:: Stream.empty

def streamOnes: Stream[Int] = 1 #:: streamOnes
streamOnes

streamOnes.take(5).toList

import scala.collection.JavaConverters._

Seq(1, 2, 3).asJava

val list: java.util.List[Int] = new java.util.ArrayList[Int]() // list: java.util.List[Int] = []
list.asScala
list.asScala += 5 // res: scala.collection.mutable.Buffer[Int] = Buffer(5)
list

List.tabulate(5)(_ + 1)

