package chess;

public class Pawn extends Piece {
	private boolean moved = false;
	
	public Pawn(String color) {
		this.color = color;
	}
	
	@Override
	protected String getColor() {
		return this.color;
	}

	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol) {
		
		//if White pawn
		if(this.color.equals("White")) {
			//cover first move - white
			if(startRow == 2 && endRow == 4) {
				return true;
			}
			if(startCol == endCol && (startRow == (endRow - 1))){
				return true;
			}
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow -1))) {
				return true;
			}
		}
		//black pawn
		else {
			//cover first move - black
			if(startRow == 7 && endRow == 5) {
				return true;
			}
			if(startCol == endCol && (startRow == (endRow + 1))){
				return true;
			}
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow + 1))) {
				return true;
			}
		}
		
		
		
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
