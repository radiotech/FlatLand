package wrapper

import java.awt.Color
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object Surface {
  def apply(w: Int, h: Int, getPos: Point=>(Int,Int)) = new Surface(w,h,getPos)
  def apply(w: Int, h: Int) = new Surface(w,h,(p)=>(((p.x+10)/20*w).toInt,((p.y+10)/20*h).toInt))
  val BLACK = Color.BLACK.getRGB
}

class Surface(val w: Int, val h: Int, val getPos: Point=>(Int,Int)) {
  var pixels = Array.fill(w*h)(Surface.BLACK)
  def setPixel(x: Int, y: Int, c: Color){
    pixels(y*w+x) = c.getRGB
  }
  def getPos(p: Point, mod: (Int,Int)): (Int,Int) = {
    capCoordToBox((0,0),(w-1,h-1),getPos(p))
  }
  def background(c: Color = Color.BLACK) {
    val cInt = c.getRGB
    pixels = Array.fill(w*h)(cInt)
  }
  def capCoordToBox(a: (Int,Int), b: (Int,Int), c: (Int,Int)) = (Math.max(a._1, Math.min(b._1, c._1)),Math.max(a._2, Math.min(b._2, c._2)))
}