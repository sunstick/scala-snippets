package languages.oo

import scala.annotation.tailrec

case class Rational(n: Int, d: Int) {
  require(d != 0, "denominator cannot be zero")

  private[this] val g = gcd(n.abs, d.abs)

  @tailrec
  private[this] def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  val numer = (n / g).abs
  val denom = (d / g).abs
  val isPos = (n >= 0 && d >= 0) || (n < 0 && d < 0)

  override def toString: String =
    if (numer == 0) {
      "0"
    } else {
      if (isPos) s"$numer/$denom" else s"-$numer/$denom"
    }

  def this(n: Int) = this(n, 1)

  def +(that: Rational): Rational =
    Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  def +(i: Int): Rational =
    Rational(numer + i * denom, denom)

  def -(that: Rational): Rational =
    Rational(
      numer * that.denom - that.numer * denom,
      denom * that.denom
    )

  def -(i: Int): Rational =
    Rational(numer - i * denom, denom)

  def *(that: Rational): Rational =
    Rational(numer * that.numer, denom * that.denom)

  def *(i: Int): Rational =
    Rational(numer * i, denom)

  def /(that: Rational): Rational =
    Rational(numer * that.denom, denom * that.numer)

  def /(i: Int): Rational =
    Rational(numer, denom * i)

  override def equals(other: scala.Any): Boolean = other match {
    case that: Rational =>
      numer == that.numer && denom == that.denom && isPos == that.isPos
    case _ => false
  }
}

object Rational {
  implicit def intToRational(x: Int) = new Rational(x)
}