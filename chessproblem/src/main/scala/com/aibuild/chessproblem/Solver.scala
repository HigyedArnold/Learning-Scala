package com.aibuild.chessproblem
import com.aibuild.chessproblem.core.ChessPieceType.{ChessPieceType, _}
import com.aibuild.chessproblem.core.Util
import com.aibuild.chessproblem.entities.{Bishop, ChessPiece, King, Knight, Position, Queen, Rook}
import com.aibuild.chessproblem.timer.Timer
import com.aibuild.chessproblem.timer.Timer.TimerType

object Solver extends App {

  solve()

  def solve(): Unit = {
    val m      = 7
    val n      = 7
    val pieces = Vector(Queen, Queen, Bishop, Bishop, King, King, Knight)
    //    val m =3
    //    val n = 3
    //    val pieces = Vector(Rook, King, King)

    // Better performance if we start the search with the pieces sorted

    val chessTable = Util.generateChessTable(m, n)

    //    Debug purposes
    //    println(pieces)
    //    println(chessTable)
    //    println(chessTable.size)
    val uniqueCoef = Util.uniqueCoef(pieces)
    //    println(coef)

    val started = Timer.start

    // recursive function
    // BZL:
    //
    // -> Stop succes: no more pieces
    // -> Stop failure: no more positions to put piece on
    //
    // -> valid piece
    //    -> rec take (mark)
    //    -> rec not take
    // -> invalid piece
    //    -> rec

    val solutions = rec(chessTable, 0, pieces, Vector.empty[ChessPiece])

    Timer.print(Timer.stop(started), TimerType.SEC)
    println("Raw solutions: " + solutions)
    println("Unique solutions: " + solutions / uniqueCoef)
  }

  def rec(chessTable: Vector[Position], chessIndex: Int, pieces: Vector[ChessPieceType], onPieces: Vector[ChessPiece]): Int = {
    // -> Stop succes: no more pieces
    if (pieces.isEmpty) {
      // print the pieces position
      //      onPieces.map(println(_))
      //      println("\n")
      1
    }
    // -> Stop failure: no more positions to put piece on
    else if (chessIndex >= chessTable.size) {
      //println("Chess table index over-incremented. Dead branch.")
      0
    }
    else if (chessTable.isEmpty) {
      //println("No more positions left. Dead branch.")
      0
    }
    else {

      val piece = pieces.head match {
        case Queen  => new Queen(chessTable(chessIndex))
        case Rook   => new Rook(chessTable(chessIndex))
        case Bishop => new Bishop(chessTable(chessIndex))
        case King   => new King(chessTable(chessIndex))
        case Knight => new Knight(chessTable(chessIndex))
      }

      if (!piece.validate(onPieces)) {
        // -> valid piece
        //    -> rec take (mark)
        val markedChessTable = piece.mark(chessTable)
        rec(markedChessTable, 0, pieces.tail, piece +: onPieces) +
          //    -> rec not take
          rec(chessTable, chessIndex + 1, pieces, onPieces)
      }
      else {
        // -> invalid piece
        //    -> rec
        rec(chessTable, chessIndex + 1, pieces, onPieces)
      }
    }
  }

}
