package core.shapes.dim1

import java.awt.Color
import wrapper._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._
import scala.Range

object Line {
  val AT_NONE = 0
  val AT_POINT = 1
  val AT_LINE = 2
  
  def renderLine(p1: Point, p2: Point, c: Color, s: Surface): Unit = renderLine(p1,p2,(0,0),(0,0),c,s)
  def renderLine(pStart: Point, pEnd: Point,mod1: (Int,Int)=(0,0), mod2: (Int,Int)=(0,0), c: Color, s: Surface){
    val raw_p1 = s.getPos(pStart,mod1)
    val raw_p2 = s.getPos(pEnd,mod2)
    val flip = Math.abs(raw_p1._1-raw_p2._1)<Math.abs(raw_p1._2-raw_p2._2)
    val p1 = if(flip){if(raw_p1._2<raw_p2._2){raw_p1}else{raw_p2}}else{if(raw_p1._1<raw_p2._1){raw_p1}else{raw_p2}}
    val p2 = if(p1.eq(raw_p1)){raw_p2}else{raw_p1}
    val dx = p2._1-p1._1
    val dy = p2._2-p1._2
    val m = if(flip){dx.toDouble/dy}else{dy.toDouble/dx}
    
    //Range(0,tw+th).foreach { x => m.setPixel((p1._1+x.toDouble/(tw+th)*dx).toInt,(p1._2+x.toDouble/(tw+th)*dx).toInt,c) }
    if(flip){
      Range(0,dy).foreach(y=>s.setPixel((p1._1+y*m).toInt, p1._2+y, c))
    } else {
      Range(0,dx).foreach(x=>s.setPixel(p1._1+x, (p1._2+x*m).toInt, c))
    }
    s.setPixel(p2._1,p2._2,c)
  }
}

trait Line {
  val p: Point
  val dir: Vector2
  def validAtT(t: Double) = true
  def capT(t: Double) = if(t.isInfinity || t.isNaN){Double.PositiveInfinity}else{if(validAtT(t)){t}else{Double.NaN}}
  def xToT(x: Double) = capT((x-p.x)/dir.x)
  def yToT(y: Double) = capT((y-p.y)/dir.y)
  def tToX(t: Double) = p.x+t*dir.x
  def tToY(t: Double) = p.y+t*dir.y
  def render(c: Color = new Color(100,100,100), s: Surface): Unit
  def hitType(l: Line): Int
}