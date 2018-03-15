package chess;

import java.util.List;

public class GhostPawn extends Piece {

	public GhostPawn(String color) {
		this.piece = "ghost";
		this.color = color;
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
		return false;
	}

	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean checkForCheckMate(int startRow, int startCol, int[] kingSpot){
		// TODO Auto-generated method stub
		return false;
	}
	
	public String toString() {
		return "gp ";
	}

}
