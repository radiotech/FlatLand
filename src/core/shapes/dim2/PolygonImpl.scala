package core.shapes.dim2

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

class PolygonImpl(val points: List[Point], val sides: List[LineSegment]) extends Polygon {
  
  def getIntersections(p: Point, dir: Vector2): List[Double] = {
    boundingBox.getIntersections(p, dir)
  }
  
}