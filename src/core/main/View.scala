package core.main

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

trait View {
  val w: Int
  val h: Int
  val getPos: (Int,Int)=>Point
  val pixels: Array[Int]
}