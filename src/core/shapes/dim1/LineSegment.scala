package core.shapes.dim1

import java.awt.Color
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object LineSegment {
  def apply(a: Point, b: Point) = {
    val p1 = if(a.y>b.y){a}else if(b.y>a.y){b}else{if(a.x>b.x){a}else{b}}
    val p2 = if(p1.eq(a)){b}else{a}
    new LineSegmentImpl(p1, p2, Vector2(p2.x-p1.x, p2.y-p1.y))
  }
}

trait LineSegment extends Ray {
  val length: Double
  val p_end: Point
  override def render(c: Color, s: Surface) = {
    Line.renderLine(p,p_end,c,s)
  }
  override def validAtT(t: Double) = t>=0 && t<length
  override def hitType(l: Line) = {
    println("hitType in LineSegment not implemented")
    1
  }
}