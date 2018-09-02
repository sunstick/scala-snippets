package languages.annotations

case class People(
  val first: String,
  val last: String,
  val age: Int,
  val address: String,
  @transient val dummyField: String)
