package chess;

public abstract class Piece {
	//make color private, get through getter
	protected String color;
	//get color
	protected abstract String getColor();
	//find out if current move is legal.
	protected abstract boolean isLegalMove(int startRow, int startCol, int endRow, int endCol);
}
