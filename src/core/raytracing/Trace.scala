package core.raytracing

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.forces._

object Trace {
  def apply(p: Point, dir: Vector2) = {
    new TraceImpl(LineSegment(p,p,dir))
  }
}

trait Trace {
  val head: LineSegment
  val tail: Trace
}