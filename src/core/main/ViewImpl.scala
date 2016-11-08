package core.main

import java.awt.{Color}
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

class ViewImpl(val w: Int, val h: Int, val pixels: Array[Int], val getPos: (Int,Int)=>Point) extends View