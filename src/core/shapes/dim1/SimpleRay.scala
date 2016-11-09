package core.shapes.dim1

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

class SimpleRay(val p: Point, val rawDir: Vector2) extends Ray {
  lazy val raw_dt = rawDir.mag
  lazy val dir = rawDir.unit
  override def hitType(l: Line) = {
    l match {
      case sr: SimpleRay => {1}
      case r: Ray /*and line segment which is a ray*/ => {
        val hitT = r.yToT(p.y)
        if(hitT.isInfinite || hitT.isNaN){
          if(r.p.y == p.y){Line.AT_LINE}else{Line.AT_NONE}
        } else {
          if(r.validAtT(hitT) && r.tToX(hitT) > p.x){Line.AT_POINT}else{Line.AT_NONE}
          
        }
      }
      case _ => {println("Unimplemented in SimpleRay hitType"); 1}
    }
  }
}