package wrapper

import javax.swing._
import java.awt.{Color,Canvas,Dimension,Graphics}
import java.awt.event._
import java.awt.image.{BufferedImage,DataBufferInt}
import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._

class DataPanel(val w: Int, val h: Int) extends Canvas {
  setFocusable(true)
  requestFocus
  requestFocusInWindow
  setPreferredSize(new Dimension(w, h))
  
  var getPos = (X :Int, Y :Int)=>Point.ORIGIN: Point
  
  this.addMouseListener(new MouseAdapter{
    /*override def mouseClicked(e: MouseEvent) {
      if(SwingUtilities.isRightMouseButton(e)){
        println(view.clickResults(getPos(e)))
      } else if(SwingUtilities.isLeftMouseButton(e)){
        val t0 = System.nanoTime()
        for(_ <- 0 to 100 ){
           Main.update()
        }
        val t1 = System.nanoTime()
        println("Elapsed time: " + (t1 - t0)/10000000.0 + "units")
        
      }
    }*/
    override def mousePressed(e: MouseEvent) {Main.registerInput(e,getPos)}
    override def mouseReleased(e: MouseEvent) {Main.registerInput(e,getPos)}
    override def mouseEntered(e: MouseEvent) {Main.registerInput(e,getPos)}
  })
  this.addMouseMotionListener(new MouseMotionAdapter{
    override def mouseMoved(e: MouseEvent) {Main.registerInput(e,getPos)}
    override def mouseDragged(e: MouseEvent) {Main.registerInput(e,getPos)}
  })
  this.addKeyListener(new KeyAdapter{
    override def keyPressed(e: KeyEvent) {println("p"); Main.registerInput(e,getPos)}
    override def keyReleased(e: KeyEvent) {println("e");Main.registerInput(e,getPos)}
  })
  
  
  var img = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
  var pixels = Array[Int]()
  
  def draw(v: View) {
    getPos = v.getPos
    if(v.pixels.length != pixels.length){
      img = new BufferedImage(v.w,v.h,BufferedImage.TYPE_INT_RGB);
      pixels = img.getRaster().getDataBuffer().asInstanceOf[DataBufferInt].getData()
    }
    
    val bs = getBufferStrategy
    if(bs == null){
      createBufferStrategy(3)
      requestFocus
      return
    }
    val g = bs.getDrawGraphics
    
    Array.copy(v.pixels, 0, pixels, 0, pixels.length)
    
    g.drawImage(img, 0, 0, w, h, null)
    
    g.dispose
    bs.show
  }
  
  
  
  
  def render() {
    
    
    /*
    val dx = g.getClipBounds.width.toFloat  / view.w
    val dy = g.getClipBounds.height.toFloat / view.h
    
    val t0 = System.nanoTime()
    
    view.pixels.zipWithIndex.foreach(x => x._1.zipWithIndex.foreach(y => {
      val x1 = (x._2 * dx)
      val y1 = (y._2 * dy)
      g.setColor(y._1)
      g.fillRect(x1.toInt, y1.toInt, (x1+dx).toInt, (y1+dy).toInt)
    }) )
    /*
    for {
      x <- 0 until view.w
      y <- 0 until view.h
      x1 = (x * dx)
      y1 = (y * dy)
    } {
      view.pixels(x)(y) match {
        case c: Color => g.setColor(c)
        case _ => g.setColor(Color.WHITE)
      }
      g.fillRect(x1.toInt, y1.toInt, (x1+dx).toInt, (x1+dy).toInt)
    }
    */
    
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/10000000.0 + "units")
    */
  }
}