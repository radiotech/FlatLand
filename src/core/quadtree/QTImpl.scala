package core.quadtree

import java.awt.Color
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.forces._

class QTImpl(val content: List[PieceShell], val rect: Rect, val parent: QT) extends QT {
  lazy val tl: QT = if(isLeaf){QTNil}else{QT(content.filter(_.piece.body.nwOf(rect.center)),Rect(rect.p1,rect.center),this)}
  lazy val tr: QT = if(isLeaf){QTNil}else{QT(content.filter(_.piece.body.neOf(rect.center)),Rect(rect.p1.setX(rect.center.x),rect.center.setX(rect.p2.x)),this)}
  lazy val bl: QT = if(isLeaf){QTNil}else{QT(content.filter(_.piece.body.swOf(rect.center)),Rect(rect.p1.setY(rect.center.y),rect.center.setY(rect.p2.y)),this)}
  lazy val br: QT = if(isLeaf){QTNil}else{QT(content.filter(_.piece.body.seOf(rect.center)),Rect(rect.center,rect.p2),this)}
  
  lazy val root = if(parent!=QTNil) parent.root else this
  
  def find(p: Point, c: List[PieceShell]): List[PieceShell] = sub(p).find(p,content)
  def getLeaf(p: Point): QT = if(isLeaf){this}else{sub(p).getLeaf(p)}
  def renderAll(c: Color, m: Surface){
    rect.render(c,m)
    tl.renderAll(c,m)
    tr.renderAll(c,m)
    bl.renderAll(c,m)
    br.renderAll(c,m)
  }
}