package core.quadtree

import wrapper._
import java.awt.Color
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

object QT{
  def apply() = QTNil
  def apply(c: List[PieceShell]) = new QTImpl(c,Rect(World.X_MIN,World.Y_MIN,World.X_MAX,World.Y_MAX),QTNil)
  def apply(c: List[PieceShell], r: Rect) = new QTImpl(c,r,QTNil)
  def apply(c: List[PieceShell], r: Rect, p: QT) = new QTImpl(c,r,p)
  
  val MAX_ELEMENTS_PER_LEAF = 5
  val MIN_EDGE_SIZE = 1.0
}

trait QT {
  val rect: Rect
  val content: List[PieceShell]
  val root: QT
  
  lazy val isLeaf = if(rect.smallEdge < QT.MIN_EDGE_SIZE*2) true else content.length<=QT.MAX_ELEMENTS_PER_LEAF
  val tl,tr,bl,br: QT
  def sub(p: Point, isIn: Boolean = false): QT = (p.x>rect.center.x,p.y>rect.center.y) match {case (false,false)=>tl case (true,false)=>tr case (false,true)=>bl case (true,true)=>br}
  def find(p: Point, c: List[PieceShell] = Nil): List[PieceShell]
  def getLeaf(p: Point): QT
  def renderAll(c: Color = new Color(100,100,100), m: Surface = wrapper.Main.mapa): Unit
  def renderRoot() {
    rect.render(Color.RED, Main.mapa)
    root.rect.render(Color.RED, Main.mapa)
  }
}