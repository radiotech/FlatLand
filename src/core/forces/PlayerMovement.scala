package core.forces

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._
import core.shapes.dim2.Matrix2
import core.shapes.dim0.Vector2

object PlayerMovement {
  def apply(velMax: Double, accMax: Double) = {
    val d = accMax/velMax
    val s = (4.88*Math.pow(d,-1.72)).toInt+5
    new PlayerMovement(d,accMax,s,Matrix2.ID,Vector2.ZERO,Vector2.ZERO,0)
  }
}

class PlayerMovement(
    val drag: Double,
    val accMax: Double,
    val smooth: Int,
    val rot: Matrix2 = Matrix2.ID,
    val pos: Vector2 = Vector2.ZERO,
    val vel: Vector2 = Vector2.ZERO,
    val posGoal: Double) {
  
  lazy val terminalVel = accMax/drag
  
  def apply(p: Polygon) = rot(p)+pos
  
  def move(distance: Double) = new PlayerMovement(drag,accMax,smooth,rot,pos,vel,posGoal+distance)
  def turn(rotation: Double) = new PlayerMovement(drag,accMax,smooth,rot.rotate2D(rotation),pos,vel,posGoal)
  
  //def updateRelative(relativeGoal: Vector2) = update(relativeGoal.mag,0) //find angle to relative point
  //def updateAbsolute(absoluteGoal: Vector2) = updateRelative(Vector2.ZERO) //convert to relative then 
  
  
  def next = new PlayerMovement(drag,accMax,smooth,rot,pos+vel,
      vel+rot(Vector2(0,-Math.max(-accMax,Math.min(accMax,(posGoal-(if(vel.mag<0) -1 else 1)*vel.mag*vel.mag/(2*accMax))/smooth))))-vel*drag,0)
  
}