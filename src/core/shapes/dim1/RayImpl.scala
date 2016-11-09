package core.shapes.dim1

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

class RayImpl(val p: Point, val rawDir: Vector2) extends Ray {
  lazy val raw_dt = rawDir.mag
  lazy val dir = rawDir.unit
}