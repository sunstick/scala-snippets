package languages

import org.scalatest.FunSuite

class MethodScopingSuite extends FunSuite {
  test("a simple expression should compile") {
    assertCompiles("val x = 1")
  }

  test("package statement should not compile") {
    assertDoesNotCompile(
      """
        | package abc {
        |   class A {}
        | }
      """.stripMargin)
  }

  test("public scope should compile") {
    assertCompiles(
      """
        | import languages.top.middle.A
        | val a = new A
        | a.publicScope
      """.stripMargin)
  }

  test("package scope should not compile") {
    assertDoesNotCompile(
      """
        | import languages.top.middle.A
        | val a = new A
        | a.topScope
      """.stripMargin)
  }

  test("protected method can only be referred as super.") {
    assertCompiles(
      """
        | object B extends languages.top.middle.A {
        |   super.protectedScope
        |   publicScope
        | }
      """.stripMargin)

    assertDoesNotCompile(
      """
        | import languages.top.middle.A
        | object B extends languages.top.middle.A {
        |   val a = new A
        |   a.protectedScope
        | }
      """.stripMargin)
  }
}
