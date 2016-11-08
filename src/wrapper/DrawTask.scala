package wrapper

import java.util.TimerTask

class DrawTask extends TimerTask {
  def run = {
    Main.draw
  }
}