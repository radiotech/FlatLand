package wrapper

import java.time._
import java.util.TimerTask
import java.util.Timer;
import scala.collection.immutable.ListMap

object Help {
  var TrackMap: Map[String,List[Long]] = Map()
  
  val helpTimer = new Timer()
  val helpTask = new HelpTask() 
  helpTimer.schedule(helpTask, 500, 1000)
  
  def time[T](name: String = "No-Name", a: Instant = Instant.now())(value: T, c: Instant = Instant.now()): T = {
    val time = Duration.between(a,c).toMillis
    //println(name+": "+(time))
    TrackMap = if(TrackMap.contains(name)){
      TrackMap + (name -> (time::TrackMap(name)))
    } else {
      TrackMap + (name -> List(time))
    }
    //println(TrackMap(name).length)
    value
  }
  
  def print {
    println()
    TrackMap = ListMap(TrackMap.toSeq.sortWith(_._1 < _._1):_*)
    TrackMap.foreach(kv=>{
      println(kv._1+": "+kv._2.length+" runs averaging "+(0.0+kv._2.foldLeft[Long](0)((b,a)=>b+a))/kv._2.length+"ms")
    })
    TrackMap = Map()
  }
}

class HelpTask extends TimerTask {
  def run = {
    //println("poop")
    Help.print
  }
}