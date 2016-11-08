package core.shapes.dim1

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

class RayImpl(val p: Point, raw_dx: Double, raw_dy: Double) extends Ray {
  lazy val raw_dt = Math.sqrt(raw_dx*raw_dx+raw_dy*raw_dy)
  lazy val dx = if(raw_dt == 0){1}else{raw_dx/raw_dt}
  lazy val dy = if(raw_dt == 0){0}else{raw_dy/raw_dt}
}