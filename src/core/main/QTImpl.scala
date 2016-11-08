package core.main

import java.awt.Color
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

class QTImpl(
    val content: List[PieceShell],
    a: Point,
    b: Point
)(
    c: Point = a.avg(b),
    isL: Boolean = if(Math.min(b.x-a.x,b.y-a.y) < QT.MIN_EDGE_SIZE*2) true else content.length<=QT.MAX_ELEMENTS_PER_LEAF
)(
    val tl: QT = if(isL){QTNil}else{content.filter(_.piece.body.nwOf(c)) match {case Nil=>QTNil case x=>QT(x,a,c)}},
    val tr: QT = if(isL){QTNil}else{content.filter(_.piece.body.neOf(c)) match {case Nil=>QTNil case x=>QT(x,a.setX(c.x),c.setX(b.x))}},
    val bl: QT = if(isL){QTNil}else{content.filter(_.piece.body.swOf(c)) match {case Nil=>QTNil case x=>QT(x,a.setY(c.y),c.setY(b.y))}},
    val br: QT = if(isL){QTNil}else{content.filter(_.piece.body.seOf(c)) match {case Nil=>QTNil case x=>QT(x,c,b)}}
) extends RectImpl(a,b) with QT {
  def find(p: Point, c: List[PieceShell]): List[PieceShell] = sub(p).find(p,content)
  def renderAll(c: Color, m: Surface){
    render(c,m)
    tl.renderAll(c,m)
    tr.renderAll(c,m)
    bl.renderAll(c,m)
    br.renderAll(c,m)
  }
  @deprecated("Creating a new tree is faster than updating an old tree","11-08-2016")
  def update(xs: List[PieceShell], a: Point, b: Point) = {
    if(isLeaf){
      if(xs.isEmpty) QTNil else if(xs.corresponds(content){_.eq(_)}) this else QT(xs,p1,p2)
    } else {
      val ttl = tl.update(xs.filter(_.piece.body.nwOf(center)),p1,center)
      val ttr = tr.update(xs.filter(_.piece.body.neOf(center)),p1.setX(center.x),center.setX(p2.x))
      val tbl = bl.update(xs.filter(_.piece.body.swOf(center)),p1.setY(center.y),center.setY(p2.y))
      val tbr = br.update(xs.filter(_.piece.body.seOf(center)),center,p2)
      if(ttl.eq(tl)&ttr.eq(tr)&tbl.eq(bl)&tbr.eq(br)) if(tl.eq(QTNil)) QTNil else this else new QTImpl(xs,p1,p2)(null,false)(ttl,ttr,tbl,tbr)
    }
  }
}