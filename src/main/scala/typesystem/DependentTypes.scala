package typesystem


object depType {

  trait Foo {

    case class Bar(s: String)

    def baz(bar: Bar) = bar

    def baz2(bar: Foo#Bar) = bar
  }

  object x extends Foo

  object y extends Foo

  val xbar = x.Bar("x") // evals to xbar: x.Bar = Bar(x)
  val ybar = y.Bar("y") // evals to ybar: y.Bar = Bar(y)

  x.baz(xbar)
  // x.baz(ybar) compilation fails due to type mismatch
  x.baz2(ybar)
}
