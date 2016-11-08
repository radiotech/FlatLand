package core.shapes.dim2

import java.awt.{Color}
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object Rect {
  val ENTIRE_WORLD = Rect(World.X_MIN,World.Y_MIN,World.X_MAX,World.Y_MAX)
  def apply(a: Point, b: Point) = new RectImpl(a, b);
  def apply(p: Point, w: Int, h: Double) = new RectImpl(p, new PointImpl(p.x+w,p.y+h))
  def apply(x1: Double, y1: Double, x2: Double, y2: Double) = new RectImpl(new PointImpl(x1,y1), new PointImpl(x2,y2))
}

trait Rect extends Shape {
  lazy val sides = List(LineSegment(p1,p3),LineSegment(p3,p2),LineSegment(p2,p4),LineSegment(p4,p1))
  lazy val points = List(p1,p3,p2,p1)
  def +(v: Vector2) = Rect(Point(p1.x+v.x,p1.y+v.y), Point(p1.x+v.x,p1.y+v.y))
}