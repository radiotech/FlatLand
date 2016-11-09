package core.pieces

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.forces._
import userinput.UserInput

object Player {
  def apply(b: Polygon, m: PlayerMovement) = new Player(b,m);
}

class Player(poly: Polygon, pMove: PlayerMovement) extends Piece {
  val body: Polygon = pMove(poly)
  def next(ui: UserInput) = {
    val pMoveW = if(ui.key('w').isDown) pMove.move(100) else pMove
    val pMoveWA = if(ui.key('a').isDown) pMoveW.turn(0.05) else pMoveW
    val pMoveWAS = if(ui.key('s').isDown) pMoveWA.move(-100) else pMoveWA
    val pMoveWASD = if(ui.key('d').isDown) pMoveWAS.turn(-0.05) else pMoveWAS
    
    Player(poly, pMoveWASD.next)
  }
}