package core.main

import wrapper._
import java.awt.Color
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

object QT{
  def apply() = QTNil
  def apply(c: List[PieceShell]) = new QTImpl(c,new PointImpl(World.X_MIN,World.Y_MIN),new PointImpl(World.X_MAX,World.Y_MAX))()()
  def apply(c: List[PieceShell], a: Point, b: Point) = new QTImpl(c,a,b)()()
  val MAX_ELEMENTS_PER_LEAF = 5
  val MIN_EDGE_SIZE = 1.0
}

trait QT extends Rect {
  lazy val isLeaf = if(smallEdge < QT.MIN_EDGE_SIZE*2) true else content.length<=QT.MAX_ELEMENTS_PER_LEAF
  val content: List[PieceShell]
  val tl,tr,bl,br: QT
  def sub(p: Point): QT = (p.x>center.x,p.y>center.y) match {case (false,false)=>tl case (true,false)=>tr case (false,true)=>bl case (true,true)=>br}
  def find(p: Point, c: List[PieceShell] = Nil): List[PieceShell]
  def update(xs: List[PieceShell], a: Point = p1, b: Point = p2): QT
  def renderAll(c: Color = new Color(100,100,100), m: Surface = wrapper.Main.mapa): Unit
}