package chess;

public class Queen extends Piece {

	public Queen(String color) {
		this.color = color;
		this.piece = "Queen";
	}
	
	@Override
	protected String getColor() {
		return this.color;
	}

	@Override
	protected String getPiece() {
		return this.piece;
	}
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol) {
		return (Math.abs(startRow - endRow) == Math.abs(startCol - endCol)) || ((startRow == endRow) || (startCol == endCol));
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wQ";
		}
		else {
			return "bQ";
		}
	}


}
