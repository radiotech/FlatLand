package core.raytracing

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.forces._, core.quadtree._
import wrapper._

object Trace {
  val DELTA = .000001
  def apply(qt: QT, p: Point, dir: Vector2): Trace = {
    if(qt.rect.contains(p)){
      new TraceImpl(qt, LineSegment(p,qt.getLeaf(p).rect.castInside(p, dir),dir))
    } else {
      TraceNil
    }
  }
}

trait Trace {
  val head: LineSegment
  val tail: Trace
  def render(s: Surface): Unit
}