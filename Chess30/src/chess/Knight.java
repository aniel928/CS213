package chess;

public class Knight extends Piece {

	public Knight(String color) {
		this.color = color;
		this.piece = "Knight";
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
		return (Math.abs(startRow - endRow) == 1 && Math.abs(startCol - endCol) == 2) || (Math.abs(startRow - endRow) == 2 && Math.abs(startCol - endCol) == 1);
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wN";
		}
		else {
			return "bN";
		}
	}


}
