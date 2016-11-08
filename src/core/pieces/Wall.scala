package core.pieces

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object Wall {
  def apply(b: Rect) = new Wall(b)
}

class Wall(val body: Rect) extends Piece {
  def next(ui: UserInput) = this
}