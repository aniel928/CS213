package chess;

public class Bishop extends Piece {
	
	public Bishop(String color) {
		this.color = color;
		this.piece = "Bishop";
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
		return Math.abs(startRow - endRow) == Math.abs(startCol - endCol);
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wB";
		}
		else {
			return "bB";
		}
	}

}
