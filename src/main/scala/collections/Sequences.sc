def smallestFrom(seq: Seq[Int]): Int =
  seq.foldLeft(Int.MaxValue)(Math.min)

smallestFrom(Seq(5, 8, 1, 6, 9, 2))

def unique(seq: Seq[Int]): Seq[Int] =
  seq.foldLeft(Seq.empty[Int]){
    (b, a) =>
      if (b.contains(a))
        b
      else
        b :+ a
  }

unique(Seq(1, 1, 2, 4, 3, 4) )

def reverse(seq: Seq[Int]): Seq[Int] =
  seq.foldLeft(Seq.empty[Int]) { (b, a) => a +: b }

reverse(List(1, 2, 3, 4, 5, 6))

// Write map in terms of foldRight
def map[A, B](seq: Seq[A])(f: A => B): Seq[B] =
  seq.foldLeft(Seq.empty[B]) { (b, a) =>  b :+ f(a) }

map(List(1, 2, 3, 4, 5, 6))( _ + 2)

//Write your own implementation of foldLeft that uses foreach and mutable state
def foldLeft[A, B](seq: Seq[A])(ini: B)(f: (B, A) => B): B = {
  var result = ini
  seq.foreach(a => result = f(result, a))
  result
}

foldLeft(Seq(1, 1, 2, 4, 3, 4))(Seq.empty[Int]) { (b, a) => a +: b }
