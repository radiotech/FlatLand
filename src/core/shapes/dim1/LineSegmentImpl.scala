package core.shapes.dim1

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

class LineSegmentImpl(p_start: Point, val p_end: Point, override val rawDir: Vector2) extends RayImpl(p_start,rawDir) with LineSegment {
  override lazy val dir = rawDir.unit
  lazy val length = p.distTo(p_end)
}