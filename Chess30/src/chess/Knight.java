package chess;

public class Knight extends Piece {

	public Knight(String color) {
		this.color = color;
	}
	
	@Override
	protected String getColor() {
		return this.color;
	}

	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol) {
		// TODO Auto-generated method stub
		return false;
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
