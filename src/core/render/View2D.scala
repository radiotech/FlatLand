package render

import java.awt.{Color}
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

object View2D {
  def apply(w: Int, h: Int, r: Rect, gen: Point=>Color) = {
    def getPos(x: Int,y: Int) = Point(r.p1.x+x*r.w/w,r.p1.y+y*r.h/h)
    new ViewImpl(w,h,Array.tabulate(w*h)(i=>gen(getPos(i%w,i/w)).getRGB()),getPos _)
  }
}