package chess;

public class Pawn extends Piece {

	public Pawn(String color) {
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
			return "wp";
		}
		else {
			return "bp";
		}
	}


}
