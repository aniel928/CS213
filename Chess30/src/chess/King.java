package chess;

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
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol) {
		//if piece hasn't moved and king is trying to move two spaces to the left or right, then look for castle
		if(!moved && startRow == endRow) {
			//right side of board
			if(endCol == 6) {
				if(ChessBoard.getPiece(ChessBoard.ROWS - startRow,  7) != null) {
					if(ChessBoard.getPiece(ChessBoard.ROWS - startRow,7).piece == "Rook" &&! ChessBoard.getPiece(ChessBoard.ROWS - startRow, 7).moved) {
						return true;
					}
				}
			}
			//left side of board.
			if(endCol == 2) {
				if(ChessBoard.getPiece(ChessBoard.ROWS - startRow,  1) != null) {
					if(ChessBoard.getPiece(ChessBoard.ROWS - startRow,  1).piece == "Rook" &&! ChessBoard.getPiece(ChessBoard.ROWS - startRow,  1).moved) {
						return true;
					}
				}
			}
		}
		return ((startRow == endRow || Math.abs(startRow - endRow) == 1) && (startCol == endCol || Math.abs(startCol - endCol) == 1)); 
	}
	
	@Override
	protected boolean coastClear(int startRpw, int startCol, int endRow, int endCol) {
		// TODO Auto-generated method stub
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


}
