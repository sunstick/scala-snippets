package languages.oo

sealed abstract class Element {

  import Element.elem

  def contents: Array[String]

  /*
  Such parameterless methods are quite common in Scala. By contrast, methods defined with empty parentheses,
  such as def height(): Int, are called empty-paren methods. The recommended convention is to use a parameterless
  method whenever there are no parameters and the method accesses mutable state only by reading fields of the
  containing object (in particular, it does not change mutable state). This convention supports the uniform access
  principle,[1] which says that client code should not be affected by a decision to implement an attribute as a field or method.
  The point is that clients of the Element class should not be affected when its internal implementation changes.


  To summarize, it is encouraged in Scala to define methods that take no parameters and
  have no side effects as parameterless methods (i.e., leaving off the empty parentheses).
  On the other hand, you should never define a method that has side-effects without parentheses,
  because invocations of that method would then look like a field selection.
  So your clients might be surprised to see the side effects.

  Similarly, whenever you invoke a function that has side effects,
  be sure to include the empty parentheses when you write the invocation.
  Another way to think about this is if the function you're calling performs an operation,
  use the parentheses. But if it merely provides access to a property, leave the parentheses off.
   */
  def height: Int = contents.length

  def width: Int = if (height == 0) 0 else contents(0).length

  def above(that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width
    elem(this1.contents ++ that1.contents)
  }

  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    elem(
      for ((line1, line2) <- this1.contents zip that1.contents)
        yield line1 + line2
    )
  }

  def widen(w: Int): Element =
    if (w <= width) this
    else {
      val left = elem(' ', (w - width) / 2, height)
      val right = elem(' ', w - width - left.width, height)
      left beside this beside right
    }

  def heighten(h: Int): Element =
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      val bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }


  override def toString = contents mkString "\n"
}

/**
  * Factory object which hides the class hierarchy.
  */
object Element {

  private class ArrayElement(override val contents: Array[String])
    extends Element // overriding a parameterless method with a field

  private class LineElement(s: String) extends Element {
    val contents: Array[String] = Array(s)

    override def width = s.length

    override def height = 1
  }

  private class UniformElement(ch: Char,
                               override val width: Int,
                               override val height: Int) extends Element {
    private val line = ch.toString * width

    final override def contents: Array[String] = Array.fill(height)(line)
  }

  // Clients would use these factory method to create objects.
  // This hiding will both make your library simpler for clients to understand,
  // because less detail is exposed, and provide you with more opportunities to change your library's
  // implementation later without breaking client code.
  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)

  def elem(chr: Char, width: Int, height: Int): Element =
    new UniformElement(chr, width, height)

  def elem(line: String): Element =
    new LineElement(line)
}

object Spiral {

  import Element.elem

  val space = elem(" ")
  val corner = elem("+")

  def spiral(nEdges: Int, direction: Int): Element = {
    if (nEdges == 1)
      elem("+")
    else {
      val sp = spiral(nEdges - 1, (direction + 3) % 4)

      def verticalBar = elem('|', 1, sp.height)

      def horizontalBar = elem('-', sp.width, 1)

      if (direction == 0)
        (corner beside horizontalBar) above (sp beside space)
      else if (direction == 1)
        (sp above space) beside (corner above verticalBar)
      else if (direction == 2)
        (space beside sp) above (horizontalBar beside corner)
      else
        (verticalBar above corner) beside (space above sp)
    }
  }

  def main(args: Array[String]) = {
    println(spiral(10, 0))
    println(spiral(6, 0))
  }
}