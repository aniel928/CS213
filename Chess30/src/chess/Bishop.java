package chess;

public class Bishop extends Piece {
	
	public Bishop(String color) {
		this.color = color;
	}

	@Override
	protected String getColor() {
		return this.color;
	}

	@Override
	protected boolean isLegalMove() {
		// TODO Auto-generated method stub
		return false;
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