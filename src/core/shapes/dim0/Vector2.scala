package core.shapes.dim0

object Vector2 {
  val ZERO: Vector2 = new Vector2Impl(0,0)
  val ZERO_DIR: Vector2 = new Vector2Impl(1,0)
  def apply(x: Double, y: Double): Vector2 = new Vector2Impl(x,y)
}

trait Vector2 {
  val x: Double
  val y: Double
  lazy val mag = Math.sqrt(x*x+y*y)
  lazy val unit = if(hasDir) Vector2(x/mag,y/mag) else Vector2.ZERO_DIR
  lazy val isSimple = x==0||y==0
  lazy val hasDir = x!=0||y!=0
  lazy val slope = y/x
  def addToX(n: Double) = Vector2(x+n,y) 
  def addToY(n: Double) = Vector2(x,y+n)
  def +(v: Vector2) = Vector2(x+v.x,y+v.y)
  def -(v: Vector2) = Vector2(x-v.x,y-v.y)
  def *(n: Double) = Vector2(x*n,y*n)
}