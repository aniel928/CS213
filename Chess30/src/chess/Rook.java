package chess;

public class Rook extends Piece {

	public Rook(String color) {
		this.color = color;
	}
	
	@Override
	protected String getColor() {
		return this.color;
	}

	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol) {
		return ((startRow == endRow) || (startCol == endCol));			
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
