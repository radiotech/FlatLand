package core.shapes.forces

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object Transform extends Transform {
  val t = ((0.0,0.0,0.0),(0.0,0.0,0.0),(0.0,0.0,0.0))
  val ID = Transform.translate(0,0)
  def apply(o: ((Double,Double,Double),(Double,Double,Double),(Double,Double,Double))) = new TransformImpl(o)
  
  override def then(o: Transform): Transform = o
  override def then(o: ((Double,Double,Double),(Double,Double,Double),(Double,Double,Double))): Transform = Transform(o)
}

trait Transform {
  val t: ((Double,Double,Double),(Double,Double,Double),(Double,Double,Double))
  
  def apply(p: Point): Point = Point(p.x*t._1._1+p.y*t._1._2+t._1._3,p.x*t._2._1+p.y*t._2._2+t._2._3)
  def apply(s: Polygon): Polygon = Polygon(s.points.map {this(_)})
  
  def translate(x: Double, y: Double) = then(((1.0,0.0,x),(0.0,1.0,y),(0.0,0.0,1.0)))
  def rotate(a: Double) = then(((Math.cos(a),Math.sin(a),0.0),(-Math.sin(a),Math.cos(a),0.0),(0.0,0.0,1.0)))
  def scale(x: Double, y: Double) = then(((x,0.0,0.0),(0.0,y,0.0),(0.0,0.0,1.0)))
  
  def then(o: Transform): Transform = then(o.t)
  def then(o: ((Double,Double,Double),(Double,Double,Double),(Double,Double,Double))): Transform = {
    Transform((
        (   t._1._1*o._1._1 + t._2._1*o._1._2 + t._3._1*o._1._3,
            t._1._2*o._1._1 + t._2._2*o._1._2 + t._3._2*o._1._3,
            t._1._3*o._1._1 + t._2._3*o._1._2 + t._3._3*o._1._3),
        (   t._1._1*o._2._1 + t._2._1*o._2._2 + t._3._1*o._2._3,
            t._1._2*o._2._1 + t._2._2*o._2._2 + t._3._2*o._2._3,
            t._1._3*o._2._1 + t._2._3*o._2._2 + t._3._3*o._2._3),
        (   t._1._1*o._3._1 + t._2._1*o._3._2 + t._3._1*o._3._3,
            t._1._2*o._3._1 + t._2._2*o._3._2 + t._3._2*o._3._3,
            t._1._3*o._3._1 + t._2._3*o._3._2 + t._3._3*o._3._3)))
  }
}