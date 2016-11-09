package core.raytracing

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.forces._, core.quadtree._
import wrapper._
import java.awt.Color

class TraceImpl(qt: QT, val head: LineSegment) extends Trace {
  lazy val tail = Trace(qt, head.p_end+head.dir*Trace.DELTA, head.dir)
  def render(s: Surface) {
    head.render(Color.YELLOW, s)
    tail.render(s)
  }
}