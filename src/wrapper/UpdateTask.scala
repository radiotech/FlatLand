package wrapper

import java.util.TimerTask

class UpdateTask extends TimerTask {
  def run = {
    Main.update
  }
}