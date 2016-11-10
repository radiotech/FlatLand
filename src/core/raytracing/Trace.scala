package core.raytracing

import core.main._, core.pieces._, core.shapes.dim0._, core.shapes.dim1._, core.shapes.dim2._, core.shapes.forces._, core.forces._, core.quadtree._
import wrapper._

object Trace {
  val DELTA = .000001
  def apply(qt: QT, p: Point, dir: Vector2): Trace = {
    if(qt.rect.contains(p)){
      new TraceImpl(qt, LineSegment(p,qt.getLeaf(p).rect.castInside(p, dir),dir))
    } else {
      TraceNil
    }
  }
  def getQTLeaves(p: Point, dir: Vector2, qt: QT): List[QT] = {
    val dirSmall = dir*Trace.DELTA
    def iter(p: Point): List[QT] = {
      if(qt.rect.contains(p)){
        val leaf = qt.getLeaf(p);
        leaf::iter(leaf.rect.castInside(p, dir)+dirSmall)
      } else {
        Nil
      }
    }
    iter(p)
  }
  def getPieceShells(xs: List[QT]) = Set(xs.flatMap{_.content}: _*)
  
  def getPieceWrapperIntersects(p: Point, dir: Vector2, xs: Set[PieceShell]): List[(Double,PieceShell)] = {
    xs.flatMap{
      ps=>{
        val hits = ps.piece.body.getIntersections(p,dir)
        if(hits.isEmpty){
          Nil
        } else if(hits.length == 1){
          if(ps.piece.body.contains(p)){
            List((0.0,ps),(hits.head,ps))
          } else {
            List((hits.head,ps),(hits.head,ps))
          }
        } else if(hits.length == 2){
          List((hits.head,ps),(hits.last,ps))
        } else {
          val hits2 = hits.distinct
          if(hits2.length == 2){
            List((hits2.head,ps),(hits2.last,ps))
          } else {
            throw new RuntimeException("Ray and Rect intersection resulted in three different points, did not think this was possible");
          }
        }
      }
    }.toList
  }
  
  def getIntersectRanges(xs: List[(Double,PieceShell)]): List[(Double,Double,Set[PieceShell])] = {
    def iter(xs: List[(Double,PieceShell)], lastDis: Double, currentPSs: Set[PieceShell]): List[(Double,Double,Set[PieceShell])] = {
      if(xs == Nil){
        Nil
      } else {
        (lastDis,xs.head._1-lastDis,currentPSs) :: iter(xs.tail,xs.head._1,f(currentPSs,xs.head._2))
      }
    }
    def f(xs: Set[PieceShell], x: PieceShell) = {
      if(xs.contains(x)) xs-x else xs+x
    }
    if(xs.isEmpty){Nil}else{
      val orderedXs = xs.sortBy(_._1)
      iter(orderedXs.tail,orderedXs.head._1,f(Set.empty,orderedXs.head._2))
    }
  }
  
  
}

trait Trace {
  val head: LineSegment
  val tail: Trace
  def render(s: Surface): Unit
}