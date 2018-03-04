package chess;

public abstract class Piece {
	//make color private, get through getter
	protected String color;
	protected String piece;
	protected boolean moved = false;
	//get color
	protected abstract String getColor();
	//get piece
	protected abstract String getPiece();
	//find out if current move is legal.
	protected abstract boolean isLegalMove(int startRow, int startCol, int endRow, int endCol);
}
