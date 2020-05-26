def intersection[A](a: Set[A], b: Set[A]): Set[A] = {
  a.foldLeft(Set.empty[A]) {
    (acc, element) =>
      if (b.contains(element))
        acc + element
      else
        acc
  }
}

intersection(Set(1, 2, 3), Set(4, 5, 6))
intersection(Set(1, 2, 3), Set(3, 4, 5))

def intersection[A](a: Map[A, Int], b: Map[A, Int]): Map[A, Int] = {
  a.foldLeft(Map.empty[A, Int]) {
    (acc, element) =>
      if (b.contains(element._1))
        acc + (element._1 -> (element._2 + b(element._1)))
      else
        acc
  }
}

intersection(Map('a' -> 1, 'b' -> 2), Map('a' -> 2, 'b' -> 4))


def genericIntersection[A, B](a: Map[A, B])( b: Map[A, B])(add: (B, B) => B): Map[A, B] = {
  a.foldLeft(Map.empty[A, B]) {
    (acc, element) =>
      if (b.contains(element._1))
        acc + (element._1 -> add(element._2, b(element._1)))
      else
        acc
  }
}

genericIntersection(Map('a' -> "1", 'b' -> "2"))(Map('a' -> "2", 'b' -> "4"))(_ + _)

