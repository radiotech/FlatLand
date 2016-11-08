package core.main

import java.awt.{Color}
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

class View2D(val w: Int, val h: Int, val pixels: Array[Int], val getPos: (Int,Int)=>Point) extends View {
  def this(w: Int, h: Int, gen: (Int,Int)=>Color, getPos: (Int,Int)=>Point) = this(w,h,Array.tabulate(w*h)(i=>gen(i%w,i/w).getRGB()),getPos)
}