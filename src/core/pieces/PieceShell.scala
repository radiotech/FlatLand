package core.pieces

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._
import userinput.UserInput

object PieceShell {
  def apply(s: PieceInfo, p: Piece) = new PieceShell(s, p)()
  def apply(s: PieceInfo, p: Piece, l: Piece) = new PieceShell(s, p)(l)
  def apply(p: Piece) = new PieceShell(PieceInfo(), p)()
  def apply(n: String, p: Piece) = new PieceShell(PieceInfo(n), p)()
}

class PieceShell(val shell: PieceInfo, val piece: Piece)(val pieceLast: Piece = piece){
  def next(ui: UserInput) = piece.next(ui) match {case u => if(u.eq(piece) & pieceLast.eq(piece)) this else PieceShell(shell,u,piece)}
}