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
  def getIntersections(p: Point, dir: Vector2): List[Double] = {
    val t1 = (p2.x-p.x)/dir.x
    val t2 = (p2.y-p.y)/dir.y
    val t3 = (p1.x-p.x)/dir.x
    val t4 = (p1.y-p.y)/dir.y
    lazy val y1 = p.y+dir.y*t1
    lazy val x2 = p.x+dir.x*t2
    lazy val y3 = p.y+dir.y*t3
    lazy val x4 = p.x+dir.x*t4
    (if(t1>=0 && y1>=p1.y && y1<=p2.y){List(t1)}else{Nil}) :::
    (if(t2>=0 && x2>=p1.x && x2<=p2.x){List(t2)}else{Nil}) ::: 
    (if(t3>=0 && y3>=p1.y && y3<=p2.y){List(t3)}else{Nil}) :::
    (if(t4>=0 && x4>=p1.x && x4<=p2.x){List(t4)}else{Nil})
  }
  def +(v: Vector2) = Rect(Point(p1.x+v.x,p1.y+v.y), Point(p1.x+v.x,p1.y+v.y))
  def borders(p: Point) = ((p.x==p1.x||p.x==p2.x)&&(p.y>=p1.y&&p.y<=p2.y))||((p.y==p1.y||p.y==p2.y)&&(p.x>=p1.x&&p.x<=p2.x))
  def castInside(p: Point, dir: Vector2) = {
    val side = if(dir.x > 0){
      if(dir.y > 0){if(dir.slope<(p2.y-p.y)/(p2.x-p.x)) 0 else 1}else{if(dir.slope>(p1.y-p.y)/(p2.x-p.x)) 0 else 3}
    } else {
      if(dir.y > 0){if(dir.slope>(p2.y-p.y)/(p1.x-p.x)) 2 else 1}else{if(dir.slope<(p1.y-p.y)/(p1.x-p.x)) 2 else 3}
    }
    
    if(side == 0) Point(p2.x,p.y+dir.y*((p2.x-p.x)/dir.x))
    else if (side == 1) Point(p.x+dir.x*((p2.y-p.y)/dir.y),p2.y)
    else if (side == 2) Point(p1.x,p.y+dir.y*((p1.x-p.x)/dir.x))
    else Point(p.x+dir.x*((p1.y-p.y)/dir.y),p1.y)
  }
}