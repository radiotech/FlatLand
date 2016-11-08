package core.main

import java.awt.{Color}
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

object World {
  def apply(c: List[PieceShell]) = new World(c)(QT(c))
  def apply(c: List[PieceShell], qt: QT) = new World(c)(qt)
  val X_MIN = -10
  val Y_MIN = -10
  val X_MAX = 10
  val Y_MAX = 10
}

class World(val content: List[PieceShell])(val contentTree: QT) {
  
  def next(ui: UserInput) = {
    val newContent = content.map{_.next(ui)}
    //val changedContent = content.zip(newContent).filterNot(x => x._1.eq(x._2)).map(_._2)
    
    //println("Update world "+content(0).body.p1.x+" "+newContent(0).body.p1.x)
    World(newContent, QT(newContent))
  }
  
  def render2D(w: Int, h: Int, r: Rect): View = {
    //val dx = (x2-x1)/w
    //val dy = (y2-y1)/h
    //colorAt(new Point(x1+x*dx,y1+y*dy))
    def getPos(x: Int, y: Int) = new PointImpl(r.p1.x+x*r.w/w,r.p1.y+y*r.h/h)
    new View2D(w, h, (x,y)=>colorAt(getPos(x,y)), getPos)
  }
  
  def colorAt(p: Point): Color = { 
    contentTree.find(p) match{
      case Nil => new Color(0,0,0)
      case _ => new Color(255,255,255)
    }
  }
  //def colorAt(x: Double, y: Double): Color = if(x<0 & y<0 & x>(-1) & y>(-1)){Color.GREEN}else{Color.BLACK}
  //def colorAt(p: Point): Color = contentTree.find(p,0)
}