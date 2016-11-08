package core.pieces

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

trait Piece {
  val body: Shape
  def next(ui: UserInput): Piece
}