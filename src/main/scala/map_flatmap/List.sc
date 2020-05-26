val list = List(1, 2, 3)

def negation(a: Int): List[Int] = List(a, a * -1)

list.flatMap(negation _)


