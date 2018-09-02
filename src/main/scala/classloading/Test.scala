package classloading

object Test extends App {
  println(Test.getClass.getClassLoader)
  println(Int.getClass.getClassLoader)
}
