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
	
	protected List<int[]> allLegalMoves(int row, int col, ChessBoard board){
		return null;
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
	public String toString() {
		return "gp";
	}

}
