package wrapper

import java.awt.Color
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object Surface {
  val BLACK = Color.BLACK.getRGB
}

class Surface(val w: Int, val h: Int, getPos: Point=>(Int,Int)) {
  var pixels = Array.fill(w*h)(Surface.BLACK)
  def setPixel(x: Int, y: Int, c: Color){
    pixels(y*w+x) = c.getRGB
  }
  def getPos(p: Point, mod: (Int,Int)): (Int,Int) = {
    capCoordWithDirToBox((0,0),(w-1,h-1),getPos(p))
  }
  
  def capCoordWithDirToBox(a: (Int,Int), b: (Int,Int), c: (Int,Int)): (Int,Int) = {
    return (Math.max(a._1, Math.min(b._1, c._1)),Math.max(a._2, Math.min(b._2, c._2)))
    if(c._1 < a._1){
      val d = a;
      if(d._2 >= a._2 && d._2 <= b._2){
        return d
      }
    }
    if(c._1 > b._1){
      
    }
    if(c._2 < a._2){
      
    }
    if(c._2 > b._2){
      
    }
    return null
  }
}