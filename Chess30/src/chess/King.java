package chess;

import java.util.List;

public class King extends Piece {
	
	public King(String color) {
		this.color = color;
		this.piece = "King";
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
		//if piece hasn't moved and king is trying to move two spaces to the left or right, then look for castle
		if(!this.moved && startRow == endRow) {
			//right side of board
			if(endCol == 6) {
				if(board.getPiece(board.ROWS - startRow,  7) != null) {
					if(board.getPiece(board.ROWS - startRow,7).getPiece().equals("Rook") && !board.getPiece(board.ROWS - startRow, 7).moved) {
						return true;
					}
				}
			}
			//left side of board.
			if(endCol == 2) {
				if(board.getPiece(board.ROWS - startRow,  0) != null) {
					if(board.getPiece(board.ROWS - startRow,  0).getPiece().equals("Rook") && !board.getPiece(board.ROWS - startRow,  0).moved) {
						return true;
					}
				}
			}
		}
		return ((startRow == endRow || Math.abs(startRow - endRow) == 1) && (startCol == endCol || Math.abs(startCol - endCol) == 1)); 
	}
	
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		if(startCol - endCol == 2) {
			//Queen Side Castling
			if(board.getPiece(startRow, startCol-1) != null || board.getPiece(startRow, startCol-2) != null || board.getPiece(startRow, startCol-3) != null) {
				return false;
			}
		}else if(endCol - startCol == 2) {
			//Regular Castling
			if(board.getPiece(startRow, startCol+1) != null || board.getPiece(startRow, startCol+2) != null) {
				return false;
			}
		}
		return true;	
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wK";
		}
		else {
			return "bK";
		}
	}

	@Override
	protected boolean checkForCheckMate(int startRow, int startCol, int[] kingSpot){
		// TODO Auto-generated method stub
		return false;
	}


}
