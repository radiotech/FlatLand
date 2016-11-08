package core.shapes.dim2

import core.shapes.dim0._
import core.shapes.dim2._
import core.shapes.dim0.Vector2
import core.shapes.dim0.Point
import core.shapes.dim0.Vector2

object Matrix2 extends Matrix2 {
  val x00 = 1.0
  val x01 = 0.0
  val x10 = 0.0
  val x11 = 1.0
  val ID: Matrix2 = this
  def apply(x00: Double, x01: Double, x10: Double, x11: Double): Matrix2 = new Matrix2Impl(x00,x01,x10,x11)

  override def then(o: Matrix2): Matrix2 = o
  override def then(y00: Double, y01: Double, y10: Double, y11: Double): Matrix2 = Matrix2(y00,y01,y10,y11)
}

trait Matrix2 {
  val x00,x01,x10,x11: Double
  
  def apply(p: Point): Point = Point(p.x*x00+p.y*x01,p.x*x10+p.y*x11)
  def apply(v: Vector2): Vector2 = Vector2(v.x*x00+v.y*x01,v.x*x10+v.y*x11)
  def apply(s: Polygon): Polygon = Polygon(s.points.map {this(_)})
  
  def rotate2D(x: Double) = then(Math.cos(x),Math.sin(x),-Math.sin(x),Math.cos(x))
  def scale2D(x: Double, y: Double) = then(1,0,0,1)
  
  def then(o: Matrix2): Matrix2 = then(o.x00,o.x01,o.x10,o.x11)
  def then(y00: Double, y01: Double, y10: Double, y11: Double): Matrix2 = {
    Matrix2(
        x00*y00 + x10*y01, x01*y00 + x11*y01,
        x00*y10 + x10*y11, x01*y10 + x11*y11)
  }
}