package core.shapes.dim1

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

class LineSegmentImpl(p_start: Point, val p_end: Point, raw_dir: Vector2) extends RayImpl(p_start,raw_dir) with LineSegment {
  lazy val length = p.distTo(p_end)
}