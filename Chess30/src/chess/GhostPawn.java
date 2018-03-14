package chess;

import java.util.List;

public class GhostPawn extends Piece {

	@Override
	protected String getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPiece() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		if(startCol == endCol && startRow == 2 && endRow == 4) {
			if(board.getPiece(endRow, endCol) != null) {
				return false;
			}
			return false;
		}
		
		if(startCol == endCol && startRow == 7 && endRow == 5) {
			if(board.getPiece(endRow, endCol) != null) {
				return false;
			}
			return false;
		}
		return false;
	}

	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected List<Integer[]> validMoves(int startRow, int startCol) {
		// TODO Auto-generated method stub
		return null;
	}

}
