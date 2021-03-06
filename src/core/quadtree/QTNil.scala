package core.quadtree

import java.awt.Color
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.forces._

object QTNil extends QT {
  val parent = this
  val root = this
  val rect = null
  val content = Nil
  val tl,tr,bl,br = this
  def find(p: Point, c: List[PieceShell]): List[PieceShell] = c.filter(_.piece.body.contains(p))
  def getLeaf(p: Point): QT = {println("ERROR.. getLeaf called on QTNil.. Leaves should never pass getLeaf to QTNil..!"); this}
  def renderAll(c: Color, m: Surface) {}
}