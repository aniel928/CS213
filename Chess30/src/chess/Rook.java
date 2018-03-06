package chess;

public class Rook extends Piece {
	
	public Rook(String color) {
		this.color = color;
		this.piece = "Rook";
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
		return ((startRow == endRow) || (startCol == endCol));			
	}
	
	@Override
	protected boolean coastClear(int startRpw, int startCol, int endRow, int endCol) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wR";
		}
		else {
			return "bR";
		}
	}


}
