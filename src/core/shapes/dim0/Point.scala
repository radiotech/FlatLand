package core.shapes.dim0

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object Point {
  def apply(x: Double, y: Double) = new PointImpl(x,y)
  val ORIGIN = new PointImpl(0,0)
  private def square(n: Double) = n*n
}

trait Point {
  val y: Double
  val x: Double
  lazy val castEast = Ray(this)
  def +(p: Point) = new PointImpl(x+p.x,y+p.y)
  def /(n: Int) = new PointImpl(x/n,y/n)
  def avg(p: Point) = new PointImpl((x+p.x)/2,(y+p.y)/2)
  def setX(n: Double) = new PointImpl(n,y)
  def setY(n: Double) = new PointImpl(x,n)
  def confine(r: Rect) = new PointImpl(Math.max(r.p1.x,Math.min(r.p2.x,x)),Math.max(r.p1.y,Math.min(r.p2.y,y)))
  def translate(dx: Double, dy: Double) = new PointImpl(x+dx,y+dy)
  def distTo(p: Point) = Math.sqrt(Point.square(p.x-x)+Point.square(p.y-y))
  def castTo(p: Point) = LineSegment(this,p)
}