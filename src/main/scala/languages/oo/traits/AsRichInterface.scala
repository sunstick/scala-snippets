package languages.oo.traits

sealed trait Shape

case class Point(val x: Int, val y: Int) extends Shape

trait Rectangular extends Shape {
  def topLeft: Point

  def bottomRight: Point


  def left = topLeft.x

  def right = bottomRight.x

  def width = right - left

  def top = topLeft.y

  def bottom = bottomRight.y

  def height = top - bottom

  require(width >= 0)
  require(height >= 0)
}
