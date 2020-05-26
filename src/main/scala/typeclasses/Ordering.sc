val absOrdering = Ordering.fromLessThan[Int]((a, b) => Math.abs(a) < Math.abs(b))

assert(List(-4, -1, 0, 2, 3).sorted(absOrdering) == List(0, -1, 2, 3, -4))


final case class Rational(numerator: Int, denominator: Int)

val ratOrdering = Ordering.fromLessThan[Rational]((a, b) => (a.numerator.toFloat / a.denominator) < (b.numerator.toFloat / b.denominator))

List(Rational(1, 2), Rational(3, 4), Rational(1, 3)).sorted(ratOrdering)