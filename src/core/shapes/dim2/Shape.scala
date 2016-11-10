package core.shapes.dim2

import java.awt.{Color}
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

trait Shape {
  val p1: Point
  val p2: Point
  lazy val boundingBox = Rect(p1,p2)
  
  lazy val p3 = new PointImpl(p2.x,p1.y)
  lazy val p4 = new PointImpl(p1.x,p2.y)
  lazy val center = p1.avg(p2)
  lazy val w = p2.x-p1.x
  lazy val h = p2.y-p1.y
  lazy val smallEdge = Math.min(w,h).toDouble
  lazy val bigEdge = Math.max(w,h)
  lazy val isFat = p2.x-p1.x > p2.y-p1.y
  
  def nwOf(p: Point) = p1.x<=p.x&p1.y<=p.y
  def neOf(p: Point) = p2.x>=p.x&p1.y<=p.y
  def swOf(p: Point) = p1.x<=p.x&p2.y>=p.y
  def seOf(p: Point) = p2.x>=p.x&p2.y>=p.y
  
  //optional overrides...
  def pad(x: Double) = new RectImpl(new PointImpl(if(w<x*2){center.x}else{p1.x+x},if(h<x*2){center.y}else{p1.y+x}),new PointImpl(if(w<x*2){center.x}else{p2.x-x},if(h<x*2){center.y}else{p2.y-x}))
  def render(c: Color, s: Surface) {
    sides.foreach { _.render(c,s) }
  }
  
  //should override...
  val sides: List[LineSegment]
  val points: List[Point]
  def contains(p: Point) = p.x>=p1.x&p.y>=p1.y&p.x<=p2.x&p.y<=p2.y
  
  //must override...
  def getIntersections(p: Point, dir: Vector2): List[Double]
  def +(v: Vector2): Shape
}