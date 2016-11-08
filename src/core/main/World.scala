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
  val WIDTH = X_MAX-X_MIN
  val HEIGHT = Y_MAX-Y_MIN
  val WORLD_RECT = Rect(X_MIN,Y_MIN,X_MAX,Y_MAX)
}

class World(val content: List[PieceShell])(val contentTree: QT) {
  
  def next(ui: UserInput) = {
    val newContent = content.map{_.next(ui)}
    //val changedContent = content.zip(newContent).filterNot(x => x._1.eq(x._2)).map(_._2)
    
    //println("Update world "+content(0).body.p1.x+" "+newContent(0).body.p1.x)
    World(newContent, QT(newContent))
  }
  
  def render2D(w: Int, h: Int, r: Rect = World.WORLD_RECT): View = {
    View2D(w, h, r, (p)=>colorAt(p))
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