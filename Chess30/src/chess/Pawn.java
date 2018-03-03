package chess;

public class Pawn extends Piece {

	public Pawn(String color) {
		this.color = color;
	}
	
	@Override
	protected String getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isLegalMove() {
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
