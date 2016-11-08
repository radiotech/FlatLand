package core.pieces

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

object MovingWall {
  def apply(b: Rect, dx: Double, dy: Double) = new MovingWall(b,dx,dy)
}

class MovingWall(val body: Rect, dx: Double, dy: Double) extends Piece {
  def next(ui: UserInput) = new MovingWall(body+Vector2(dx,dy),dx,dy)
}