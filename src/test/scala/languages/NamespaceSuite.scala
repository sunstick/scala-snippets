package languages

import org.scalatest.FunSuite

class NamespaceSuite extends FunSuite {
  test("fields, methods, singleton objects, and packages are in the same namespace") {
    assertDoesNotCompile(
      """
        | object Test {
        |   val f = 1
        |   def f = 2
        | }
      """.stripMargin)

    assertCompiles(
      """
        | object Test {
        |   val f = 1
        |   object c {}
        | }
      """.stripMargin)

    assertDoesNotCompile(
      """
        | object Test {
        |   val f = 1
        |   object f {}
        | }
      """.stripMargin)
  }

  test("fields and classes are in different namespaces") {
    assertCompiles(
      """
        | object Test {
        |   val f = 1
        |   trait f {}
        | }
      """.stripMargin)
  }
}
