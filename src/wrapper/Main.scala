package wrapper

import javax.swing.{JFrame,JLabel}
import java.awt.{Dimension,BorderLayout,Color}
import java.awt.event._
import scala.collection.mutable.Queue
import java.util.Timer;
import core.forces._
import core.main._, core.raytracing._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.raytracing._
import core.shapes.dim2.Matrix2
import userinput.UserInput
import render.View

object Main {
  val PANNEL_WIDTH = 500
  val PANNEL_HEIGHT = 500
  val VIEW_WIDTH = 100
  val VIEW_HEIGHT = 100
  val FRAME_TIME = 1000/25
  
  
  var world = World(
      List.tabulate(10)(_ match {
        case 0 => PieceShell("Player",Player(Polygon(List(Point(0,-1.5),Point(-1,1.5),Point(0,1),Point(1,1.5))), PlayerMovement(.2,.02)))
        case _ => PieceShell("Wall",Wall(Rect(Point(Math.random()*20-10,Math.random()*20-10),2,2)))
      })
      )
  
  var mapa = Surface(VIEW_WIDTH, VIEW_HEIGHT)
  
  var grid = new DataPanel(PANNEL_WIDTH, PANNEL_HEIGHT);
  var grid2 = new DataPanel(PANNEL_WIDTH, PANNEL_HEIGHT);
  
  var inputs = new UserInput()
  
  //TODO: Package these into UserInput if possible
  var keys = Queue[Queue[(Char,Boolean)]]()
  var mbs = Queue[(Char,Boolean,Point)]()
  var mPos: Point = new PointImpl(0,0)
  
  var frame = new JFrame("FlatLand Thing.. :p")
  
  def main(args: Array[String]): Unit = {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.getContentPane().add(grid, BorderLayout.WEST);
    frame.getContentPane().add(grid2, BorderLayout.EAST);
    frame.pack();
    frame.setVisible(true);
    
    val updateTimer = new Timer()
    val updateTask = new UpdateTask() 
    updateTimer.schedule(updateTask, FRAME_TIME, FRAME_TIME)
    
    val drawTimer = new Timer()
    val drawTask = new DrawTask() 
    drawTimer.schedule(drawTask, FRAME_TIME/2, FRAME_TIME)
  }
  
  def update(): Unit = {
    Help.time("Update"){
      inputs = inputs.getNext(if(keys.isEmpty){Queue()}else{keys}.dequeue,if(mbs.isEmpty){null}else{mbs.dequeue},mPos)
      
      //if(inputs.mbLeft.isNew){println(inputs.mouse.x+","+inputs.mouse.y)}
      
      world = world.next(inputs)
      
      Thread.sleep(1)
    }
  }
  
  def draw(): Unit = {
    Help.time("Draw"){
      grid.draw(world.render2D(VIEW_WIDTH,VIEW_HEIGHT))
      
      mapa.background()
      
      world.contentTree.renderAll(new Color(255,255,255), mapa)
      world.content.foreach{_.piece.body.render(new Color(255,255,255), mapa)}
      world.content.foreach{_.piece.body.boundingBox.render(new Color(255,255,255), mapa)}
      
      
      val playerPos = Point.ORIGIN+world.content(0).piece.asInstanceOf[Player].pMove.pos
      val playerRot = world.content(0).piece.asInstanceOf[Player].pMove.rot(Vector2.ZERO_DIR)
      
      val trace = Trace(world.contentTree, playerPos, playerRot)
      trace.render(mapa)
      
      val nodes = Trace.getQTLeaves(playerPos, playerRot, world.contentTree)
      val near = Trace.getPieceShells(nodes)
      val points = Trace.getPieceWrapperIntersects(playerPos, playerRot, near)
      val ranges = Trace.getIntersectRanges(points)
      nodes.foreach{_.rect.render(Color.CYAN,mapa)}
      near.foreach {_.piece.body.render(Color.CYAN, mapa)}
      
      println()
      ranges.foreach {x=>println(x._3.size)}
      println()
      //Point(Math.random()*5-2.5,Math.random()*5-2.5).render(Color.MAGENTA,mapa)
      
      grid2.draw(View(mapa))
    }
  }
  
  def registerInput(e: Any, getPos: (Int,Int)=>Point){
    e match {
      case k: KeyEvent =>
        var state = inputs.key(k.getKeyChar).isDown
        keys.foreach(x=>x.foreach(x=>if(x._1==k.getKeyChar){state=x._2}))
        if(state != (k.getID==KeyEvent.KEY_PRESSED)){
          if(!keys.isEmpty && keys.last.forall((x: (Char,Boolean)) => x._1!=k.getKeyChar)){
            keys.last.enqueue((k.getKeyChar,k.getID==KeyEvent.KEY_PRESSED))
          } else {
            keys.enqueue(Queue((k.getKeyChar,k.getID==KeyEvent.KEY_PRESSED)))
          }
        }
      case m: MouseEvent =>
        mPos = getPos(m.getX,m.getY)
        if(m.getButton != MouseEvent.NOBUTTON){
          mbs.enqueue((m.getButton match{case MouseEvent.BUTTON1 => 'l' case MouseEvent.BUTTON2 => 'm' case MouseEvent.BUTTON3 => 'r'},m.getID==MouseEvent.MOUSE_PRESSED,mPos))
        }
    }
  }
}