package core.shapes.dim2

import java.awt.Color
import wrapper._
import core._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._
import core.shapes.dim0.Vector2

object Polygon {
  def apply(ps: List[Point]) = {
    new PolygonImpl(ps, ps.zipWithIndex.map(p=>p._1.castTo(ps((p._2+1)%ps.length))))
  }
}

trait Polygon extends Shape {
  lazy val p1 = Point(points.minBy(_.x).x,points.minBy(_.y).y)
  lazy val p2 = Point(points.maxBy(_.x).x,points.maxBy(_.y).y)
  override def contains(p: Point) = {
    val ray = p.castEast
    (sides.count {ray.hitType(_)==Line.AT_POINT})%2==1
  }
  def +(v: Vector2) = {
    Polygon(points.map{p=>Point(p.x+v.x,p.y+v.y)})
  }
}