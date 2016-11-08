package core.main

import java.awt.Color
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

object QTNil extends RectImpl(null,null) with QT {
  val content = Nil
  val tl,tr,bl,br = QTNil
  def find(p: Point, c: List[PieceShell]): List[PieceShell] = c.filter(_.piece.body.contains(p))
  def update(xs: List[PieceShell], a: Point, b: Point) = xs match {case Nil => this case _ => new QTImpl(xs,a,b)()()}
  def renderAll(c: Color, m: Surface) {}
}