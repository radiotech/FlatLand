package core.shapes.dim1

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

class LineSegmentImpl(p_start: Point, val p_end: Point, raw_dx: Double, raw_dy: Double) extends RayImpl(p_start,raw_dx,raw_dy) with LineSegment {
  lazy val length = p.distTo(p_end)
}