package core.shapes.dim1

import java.awt.Color
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object Ray {
  def apply(p: Point, dir: Vector2): Ray = if(dir.isSimple) new SimpleRay(p,dir) else new RayImpl(p,dir)
  def apply(p: Point): Ray = new SimpleRay(p,Vector2.ZERO_DIR)
}

trait Ray extends Line {
  def render(c: Color, s: Surface) = {println("Render ray not implemented")}
  override def validAtT(t: Double) = t>=0
  def hitType(l: Line) = {
    println("hitType in Ray not implemented")
    1
  }
}