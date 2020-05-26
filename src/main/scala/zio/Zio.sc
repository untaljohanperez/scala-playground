import zio._

ZIO.succeed(42)

def getOption: Option[Int] = {
  println("hola")
  Some(13)
}

val runtime = Runtime.default
runtime.unsafeRun(ZIO.fromOption(getOption).map(x => x*2))

ZIO.effect(println("Hola"))


def forever(zio: ZIO[Any, Throwable, Unit]): ZIO[Any, Throwable, Nothing] =
  zio *> forever(zio)

runtime.unsafeRun{
  for {
    _ <- Task(println("Perra"))
    _ <- forever(ZIO.effect(println("Hola"))).ensuring(UIO(println("Fin")))
  } yield ZIO.effect(println("Fin"))
}
