package core.raytracing

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.forces._
import wrapper._

object TraceNil extends Trace {
  val head = null
  val tail = this
  def render(s: Surface) {}
}