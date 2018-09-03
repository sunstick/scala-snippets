package languages

import languages.oo.ParameterlessMethods.A
import org.scalatest.FunSuite

class ParameterlessMethodsSuite extends FunSuite {
  val a = new A

  test("you can call paramless method only without parenthesis") {
    assertCompiles("a.paramlessMethod")
    assertDoesNotCompile("a.paramlessMethod()") // this actually translates to a.paramlessMethod.apply()
  }


  test("you can call empty param method both with or without parenthesis") {
    assertCompiles(
      """
        | a.emptyParamMethod
        | a.emptyParamMethod()
      """.stripMargin)
  }
}
