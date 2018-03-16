package chess;

import java.util.List;

public abstract class Piece {
	//make color private, get through getter
	protected String color;
	protected String piece;
	protected boolean moved = false;
	//get color
	protected abstract String getColor();
	//get piece
	protected abstract String getPiece();
	//compile list of valid moves
	protected abstract List<int[]> allLegalMoves(int row, int col, ChessBoard board);
	//find out if current move is legal.
	protected abstract boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board);
	//check if anyone is in my way
	protected abstract boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board);
	//generate valid moves
	protected abstract boolean checkForCheckMate(int startRow, int startCol, int[] kingSpot);
}
