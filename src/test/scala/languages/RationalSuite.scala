package languages

import languages.oo.Rational
import org.scalatest.FunSuite

class RationalSuite extends FunSuite {
  val r0 = Rational(6, 4)
  val r1 = Rational(1, 3)
  val r2 = Rational(2, 6)
  val r3 = Rational(-3, -9)
  val r4 = Rational(-1, 3)
  val r5 = Rational(2, -6)
  val r6 = Rational(0, 1)
  val r7 = Rational(0, 5)


  test("rational should be simplified") {
    assert(r0.numer == 3)
    assert(r0.denom == 2)
  }

  test("rational equality") {
    assert(r1 == r2)
    assert(r1 == r3)
    assert(r2 == r3)
    assert(r4 == r5)
    assert(r6 == r7)
  }


  test("test to string") {
    assert(r0.toString == "3/2")
    assert(r1.toString == "1/3")
    assert(r2.toString == "1/3")
    assert(r3.toString == "1/3")
    assert(r4.toString == "-1/3")
    assert(r5.toString == "-1/3")
    assert(r6.toString == "0")
    assert(r7.toString == "0")
  }

  test("zero denom should not be allowed") {
    intercept[IllegalArgumentException] {
      Rational(3, 0)
    }
  }
}
