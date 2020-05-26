val subjects = List("Noel", "The cat", "The dog")

val verbs = Map(
  "Noel" -> List("wrote", "chased", "slept on"),
  "The cat" -> List("meowed at", "chased", "slept on"),
  "The dog" -> List("barked at", "chased", "slept on")
)

val objects = List("the book", "the ball", "the bed")

for {
  sub <- subjects
  verb <- verbs(sub)
  obj <- objects
} yield s"$sub $verb $obj"
