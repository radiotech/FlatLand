package core.main

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._
import wrapper._

object View {
  def apply(s: Surface) = {
    new ViewImpl(s.w,s.h,s.pixels,(x,y)=>Point(World.X_MIN+x*World.WIDTH/s.w,World.Y_MIN+y*World.HEIGHT/s.h))
  }
}

trait View {
  val w: Int
  val h: Int
  val getPos: (Int,Int)=>Point
  val pixels: Array[Int]
}