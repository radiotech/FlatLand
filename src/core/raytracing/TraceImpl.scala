package core.raytracing

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.forces._

class TraceImpl(val head: LineSegment) extends Trace {
  lazy val tail = Trace(head.p_end, head.dir)
}