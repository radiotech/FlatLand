package core.main

import java.awt.Color
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

class QTImpl(val content: List[PieceShell], val rect: Rect) extends QT {
  lazy val tl: QT = if(isLeaf){QTNil}else{content.filter(_.piece.body.nwOf(rect.center)) match {case Nil=>QTNil case x=>QT(x,Rect(rect.p1,rect.center))}}
  lazy val tr: QT = if(isLeaf){QTNil}else{content.filter(_.piece.body.neOf(rect.center)) match {case Nil=>QTNil case x=>QT(x,Rect(rect.p1.setX(rect.center.x),rect.center.setX(rect.p2.x)))}}
  lazy val bl: QT = if(isLeaf){QTNil}else{content.filter(_.piece.body.swOf(rect.center)) match {case Nil=>QTNil case x=>QT(x,Rect(rect.p1.setY(rect.center.y),rect.center.setY(rect.p2.y)))}}
  lazy val br: QT = if(isLeaf){QTNil}else{content.filter(_.piece.body.seOf(rect.center)) match {case Nil=>QTNil case x=>QT(x,Rect(rect.center,rect.p2))}}

  def find(p: Point, c: List[PieceShell]): List[PieceShell] = sub(p).find(p,content)
  def renderAll(c: Color, m: Surface){
    rect.render(c,m)
    tl.renderAll(c,m)
    tr.renderAll(c,m)
    bl.renderAll(c,m)
    br.renderAll(c,m)
  }
}