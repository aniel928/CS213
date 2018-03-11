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
		if(!moved && startRow == endRow) {
			//right side of board
			if(endCol == 6) {
				if(board.getPiece(board.ROWS - startRow,  7) != null) {
					if(board.getPiece(board.ROWS - startRow,7).piece == "Rook" &&! board.getPiece(board.ROWS - startRow, 7).moved) {
						return true;
					}
				}
			}
			//left side of board.
			if(endCol == 2) {
				if(board.getPiece(board.ROWS - startRow,  1) != null) {
					if(board.getPiece(board.ROWS - startRow,  1).piece == "Rook" &&! board.getPiece(board.ROWS - startRow,  1).moved) {
						return true;
					}
				}
			}
		}
		return ((startRow == endRow || Math.abs(startRow - endRow) == 1) && (startCol == endCol || Math.abs(startCol - endCol) == 1)); 
	}
	
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		//only need to check for castling
		return false;
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
	protected List<Integer[]> validMoves(int startRow, int startCol) {
		// TODO Auto-generated method stub
		return null;
	}


}
