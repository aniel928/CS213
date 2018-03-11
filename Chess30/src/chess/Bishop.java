package chess;

import java.util.List;

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
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		return Math.abs(startRow - endRow) == Math.abs(startCol - endCol);
	}
	
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
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

	@Override
	protected List<Integer[]> validMoves(int startRow, int startCol) {
		// TODO Auto-generated method stub
		return null;
	}

}
