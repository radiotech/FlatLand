package core.main

import java.awt.event._
import javax.swing._
import scala.collection.mutable.Queue
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._

object Button {
  sealed trait State {
    def is(x: State) = eq(x)|(if(eq(DOWN_NEW)) x.eq(DOWN) else if(eq(UP_NEW)) x.eq(UP) else false)
    def then(x: State): State = if(strip == x) x else if(isDown) UP_NEW else DOWN_NEW
    def then(x: Boolean): State = then((if(x == true) DOWN else UP))
    lazy val isDown = is(DOWN)
    lazy val isDownNew = is(DOWN_NEW)
    lazy val isUp = is(UP)
    lazy val isUpNew = is(UP_NEW)
    lazy val isNew = is(DOWN_NEW)|is(UP_NEW)
    lazy val strip = if(isDown) DOWN else UP
  }
  case object DOWN extends State
  case object UP extends State
  case object DOWN_NEW extends State
  case object UP_NEW extends State
}

class UserInput(
      val key: Map[Char,Button.State] = Map(' ' -> Button.UP).withDefaultValue(Button.UP),
      val mb: Map[Char,Button.State] = Map('r' -> Button.UP, 'l' -> Button.UP, 'm' -> Button.UP),
      val mouse: Point = new PointImpl(0,0)
  ){
  lazy val mouseX = mouse.x
  lazy val mouseY = mouse.y
  lazy val mbRight = mb('r')
  lazy val mbLeft = mb('l')
  lazy val mbMiddle = mb('m')
  def getNext(keys: Queue[(Char,Boolean)], mbs: (Char,Boolean,Point), mPos: Point) = {
    var newKey: Map[Char,Button.State] = key.map{case (a,b)=>(a,b.strip)}
    keys.foreach(x => newKey = newKey + (x._1 -> key(x._1).then(x._2))) 
    new UserInput(
      newKey.withDefaultValue(Button.UP),
      mb.map{case (a,b)=>(a,b.strip)} + (if(mbs==null) 'n'->Button.UP else mbs._1->mb(mbs._1).then(mbs._2)),
      if(mbs == null){mPos}else{mbs._3})
  }
}