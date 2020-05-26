sealed trait Json {
  def print: String =
    this match {
      case JString(value) => "\"" + value + "\""
      case JNumber(value) => value.toString
      case JBoolean(value) => value.toString
      case JCons(head, tail) =>
        tail match {
          case JEndList => "[" + head.print + tail.print
          case JCons(_, _) => "[" + head.print + ", " + tail.print.replace("[", "")
        }
      case JEndList => "]"
      case JKey(key) => "\"" + key + "\" : "
      case JPair(key, value, tail) =>
        tail match {
          case JEndObject => "{\n  " + key.print + value.print + tail.print
          case JPair(_, _, _) => "{\n  " + key.print + value.print + ", "+ tail.print.replace("{", "")
        }
      case JEndObject => "\n}"
    }
}
final case class JString(value: String) extends Json
final case class JNumber(value: Double) extends Json
final case class JBoolean(value: Boolean) extends Json


sealed trait JObject extends Json
final case object JEndObject extends JObject
final case class JPair(key: JKey, value: Json, tail: JObject) extends JObject
final case class JKey(key: String) extends JObject


sealed trait JList extends Json
final case object JEndList extends JList
final case class JCons(head: Json, tail: JList) extends JList

println (JPair(JKey("a"), JNumber(1), JEndObject).print)

println(JCons(JString("a string"), JCons(JNumber(1.0), JCons(JBoolean(true), JEndList))).print)

val innerC = JPair(JKey("doh"), JBoolean(true), JPair(JKey("ray"), JBoolean(false), JPair(JKey("me"), JNumber(1), JEndObject)))
val c = JPair(JKey("c"), innerC, JEndObject)
val b = JPair(JKey("b"), JCons(JString("a"), JCons(JString("b"), JCons(JString("c"), JEndList))), c)
val a = JPair(JKey("a"), JCons(JNumber(1), JCons(JNumber(2), JCons(JNumber(3), JEndList))), b)
/*
{
  "a": [1,2,3],
  "b": ["a","b","c"],
  "c": { "doh":true, "ray":false, "me":1 }
}
*/