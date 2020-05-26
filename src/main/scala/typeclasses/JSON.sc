import java.util.Date
sealed trait Visitor {
  def id: String
  def createdAt: Date
  def age: Long = new Date().getTime() - createdAt.getTime()
}
final case class Anonymous( val id: String, val createdAt: Date = new Date() ) extends Visitor
final case class User( val id: String, val email: String, val createdAt: Date = new Date() ) extends Visitor


sealed trait JsValue {
  def stringify: String
}

final case class JsObject(values: Map[String, JsValue]) extends JsValue {
  def stringify =
    values
    .map { case (name, value) => "\"" + name + "\":" + value.stringify }
      .mkString("{", ",", "}")
}

final case class JsString(value: String) extends JsValue {
  def stringify = "\"" + value.replaceAll("\\|\"", "\\\\$1") + "\""
}

trait JsWriter[A] {
  def write(a: A): JsValue
}

implicit class JsUtil[A](value: A) {
  def toJson(implicit jsWriter: JsWriter[A]): JsValue =
    jsWriter.write(value)
}

implicit object StringWriter extends JsWriter[String] {
  override def write(a: String): JsValue = JsString(a)
}

implicit object DateWriter extends JsWriter[Date] {
  override def write(a: Date): JsValue = JsString(a.toString)
}

implicit object AnonymousJsWriter extends JsWriter[Anonymous] {
  def write(user: Anonymous): JsValue =
    JsObject(Map(
      "id" -> user.id.toJson,
      "createdAt" -> user.createdAt.toJson
    ))
}

implicit object UserJsWriter extends JsWriter[User] {
  def write(user: User): JsValue =
    JsObject(Map(
      "id" -> user.id.toJson,
      "email" -> user.email.toJson,
      "createdAt" -> JsString(user.createdAt.getTime.toString)
    ))
}

implicit object VisitorWriter extends JsWriter[Visitor] {
  def write(value: Visitor) =
    value match {
      case anon: Anonymous => anon.toJson
      case user: User => user.toJson
    }
}

val visitors: Seq[Visitor] = Seq(Anonymous("001", new Date), User("003", "dave@xample.com", new Date ))

visitors.map(_.toJson)
