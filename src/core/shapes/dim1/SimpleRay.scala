package core.shapes.dim1

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

class SimpleRay(val p: Point, raw_dx: Double, raw_dy: Double) extends Ray {
  lazy val raw_dt = Math.abs(raw_dx+raw_dy)
  lazy val dx = if(raw_dt == 0){1}else{Math.round(raw_dx/raw_dt).toDouble}
  lazy val dy = if(raw_dt == 0){0}else{Math.round(raw_dy/raw_dt).toDouble}
  override def hitType(l: Line) = {
    //println("hitType in SimpleRay not implemented")
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