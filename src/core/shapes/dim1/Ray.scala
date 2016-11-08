package core.shapes.dim1

import java.awt.Color
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object Ray {
  def apply(p: Point, dx: Double, dy: Double): Ray = {
    if((dx == 0 && dy != 0) || (dx != 0 && dy == 0)){
      new SimpleRay(p,dx,dy)
    } else {
      new RayImpl(p,dx,dy)
    }
  }
}

trait Ray extends Line {
  def render(c: Color, s: Surface) = {println("Render ray not implemented")}
  override def validAtT(t: Double) = t>=0
  def hitType(l: Line) = {
    println("hitType in Ray not implemented")
    1
  }
}