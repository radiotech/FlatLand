package wrapper

import scala.annotation.tailrec
import javax.swing.{JFrame,JLabel}
import java.awt.{Dimension,BorderLayout,Color}
import java.awt.event._
import scala.collection.mutable.Queue
import java.util.Timer;
import core.forces._
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._
import core.shapes.dim2.Matrix2

object Main {
  
  val WINDOW_WIDTH = 500
  val WINDOW_HEIGHT = 500
  val VIEW_WIDTH = 500
  val VIEW_HEIGHT = 500
  
  //val mapa = Array.fill(VIEW_WIDTH, VIEW_HEIGHT)(new Color(0,0,0))
  //val mapb = Array.fill(VIEW_WIDTH, VIEW_HEIGHT)(new Color(0,0,0))
  
  //val world = new World()()
  //val rendered1 = world.render2D(-10,-10,10,10,100,100)
  
  //val world2 = world.update(true)
  //val rendered2 = world2.render2D(-10,-10,10,10,100,100)
  
  //println("Main "+world.content(0).body.p1.x+" "+world2.content(0).body.p1.x)
  //println("Main "+world2.content(0).bodyLast.p1.x+" "+world2.content(0).body.p1.x)
  
  
  
  //world.content.foreach{_.body.render(new Color(255,255,255), mapa)}
  //world2.content.foreach{_.body.render(new Color(255,255,255), mapb)}
  //val rendered3: View = new View2D(VIEW_WIDTH, VIEW_HEIGHT, mapa(_)(_), world, new RectImpl(-10,-10,10,10),p=>"has ("+rendered3.view2World(p).x+","+rendered3.view2World(p).y+")-"+world.contentTree.find(rendered3.view2World(p)))
  //val rendered4: View = new View2D(VIEW_WIDTH, VIEW_HEIGHT, mapb(_)(_), world, new RectImpl(-10,-10,10,10),p=>"has ("+rendered4.view2World(p).x+","+rendered4.view2World(p).y+")-"+world2.contentTree.find(rendered4.view2World(p)))
  
  //val grid = new DataPanel(WINDOW_WIDTH, WINDOW_HEIGHT, rendered3);
  //val grid2 = new DataPanel(WINDOW_WIDTH, WINDOW_HEIGHT, rendered4);
  
  println("T DOWN is DOWN"+Button.DOWN.is(Button.DOWN))
  println("F DOWN is DOWN_NEW"+Button.DOWN.is(Button.DOWN_NEW))
  println("T DOWN_NEW is DOWN"+Button.DOWN_NEW.is(Button.DOWN))
  println("T UP_NEW is UP"+Button.UP_NEW.is(Button.UP))
  println("F DOWN is UP"+Button.DOWN.is(Button.UP))
  
  
  var mapa = new Surface(VIEW_WIDTH, VIEW_HEIGHT, (p)=>(((p.x+10)/20*500).toInt,((p.y+10)/20*500).toInt))
    
  var world = World(
      List.tabulate(10)(_ match {
        case 0 => PieceShell("Player",Player(Polygon(List(Point(0,-1.5),Point(-1,1.5),Point(0,1),Point(1,1.5))), PlayerMovement(.2,.02)))
        case _ => PieceShell("Wall",Wall(Rect(Point(Math.random()*20-10,Math.random()*20-10),2,2)))
      })
      )
  
  var rendered1 = world.render2D(500,500,Rect(-10,-10,10,10))
  
  world.content.foreach{_.piece.body.render(new Color(255,255,255), mapa)}
  var rendered2: View = new View2D(VIEW_WIDTH, VIEW_HEIGHT, mapa.pixels, (x: Int, y: Int)=>Point.ORIGIN)
 
  var grid = new DataPanel(WINDOW_WIDTH, WINDOW_HEIGHT);
  var grid2 = new DataPanel(WINDOW_WIDTH, WINDOW_HEIGHT);
  
  
  
  
  var inputs = new UserInput()
  var keys = Queue[Queue[(Char,Boolean)]]()
  var mbs = Queue[(Char,Boolean,Point)]()
  var mPos: Point = new PointImpl(0,0)
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
  
  
  
  
  def update(): Unit = {
    inputs = inputs.getNext(if(keys.isEmpty){Queue()}else{keys}.dequeue,if(mbs.isEmpty){null}else{mbs.dequeue},mPos)
    
    if(inputs.mbLeft.isNew){println(inputs.mouse.x+","+inputs.mouse.y)}
    
    //player = player.next(inputs)
    
    world = world.next(inputs)
  }
  
  val pm = Matrix2.ID.rotate2D(Math.PI/2).rotate2D(Math.PI/2).rotate2D(Math.PI/2)
  println("["+pm.x00+", "+pm.x10+"]")
  println("["+pm.x01+", "+pm.x11+"]")
  
  def draw(): Unit = {
    mapa = new Surface(VIEW_WIDTH, VIEW_HEIGHT, (p)=>(((p.x+10)/20*500).toInt,((p.y+10)/20*500).toInt))
    
    
    
    rendered1 = world.render2D(500,500,Rect(-10,-10,10,10))
    world.contentTree.renderAll(new Color(255,255,255), mapa)
    world.content.foreach{_.piece.body.render(new Color(255,255,255), mapa)}
    
    
    
    //val poly = Ray(Point(1,1),1,1)
    //val poly = (posR then posT)(Polygon(List(Point(-1,-1),Point(2,-1),Point(-1,2))))
    
    //val poly = playerMove.rot(Polygon(List(Point(0,-1.5),Point(-1,1.5),Point(0,1),Point(1,1.5))))+playerMove.pos
    //poly.render(new Color(0,255,0),mapa)
    //player.body.render(new Color(0,255,0), mapa)
    
    
    if(inputs.mbLeft.isDownNew){
      //println(poly.contains(inputs.mouse))
    }
    
    def getPos2(x: Int, y: Int) = new PointImpl(-10+x*20.0/500,-10+y*20.0/500)
    /*
    rendered1 = new View2D(500, 500, (x,y)=>if(
        player.body.contains(getPos2(x,y))
        //Ray(getPos2(x,y),1,0).hitType(poly)==1
        ){Color.GREEN}else{Color.WHITE}, getPos2)
    */
    rendered2 = new View2D(VIEW_WIDTH, VIEW_HEIGHT, mapa.pixels, (x: Int, y: Int)=>Point.ORIGIN)
 
    
    grid.draw(rendered1)
    grid2.draw(rendered2)
    /*
    frame.getContentPane().add(grid, BorderLayout.WEST);
    frame.getContentPane().add(grid2, BorderLayout.EAST);
    frame.pack();*/
  }
  
  var frame = new JFrame("Flat World Thing")
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.getContentPane().add(grid, BorderLayout.WEST);
  frame.getContentPane().add(grid2, BorderLayout.EAST);
  frame.pack();
  frame.setVisible(true);
  
  val updateTimer = new Timer()
  val updateTask = new UpdateTask() 
  updateTimer.schedule(updateTask, 40, 40)
  
  val drawTimer = new Timer()
  val drawTask = new DrawTask() 
  drawTimer.schedule(drawTask, 20, 40)
  
  def main(args: Array[String]): Unit = {
    
  }
}