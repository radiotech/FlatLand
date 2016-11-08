package core.pieces

object PieceInfo {
  def apply(n: String = "No_Name") = new PieceInfo(n)
}

class PieceInfo(val name: String)