package chess;

public class Pawn extends Piece {
	
	
	public Pawn(String color) {
		this.color = color;
		this.piece = "Pawn";
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
		
		//if White pawn
		if(this.color.equals("White")) {
			//cover first move - white
			if(startCol == endCol && startRow == 2 && endRow == 4) {
				return true;
			}
			if(startCol == endCol && (startRow == (endRow - 1))){
				if(ChessBoard.getPiece(ChessBoard.ROWS - endRow, endCol) != null && ChessBoard.getPiece(ChessBoard.ROWS - endRow,  endCol).color.equals("Black")) {
					return false;
				}
				return true;
			}
			System.out.println("sc"+startCol + "ec"+endCol +"sr"+ startRow + "er"+endRow);
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow - 1))) {
				System.out.println("one");
				if(ChessBoard.getPiece(ChessBoard.ROWS - endRow, endCol) == null) {
					System.out.println("NULL");
					return false;
				}if(ChessBoard.getPiece(ChessBoard.ROWS - endRow, endCol).color.equals("White")) {
					System.out.println("White");
					return false;
				}
				System.out.println("three");
				return true;
			}
		}
		//black pawn
		else {
			//cover first move - black
			if(startCol == endCol && startRow == 7 && endRow == 5) {
				return true;
			}
			if(startCol == endCol && (startRow == (endRow + 1))){
				if(ChessBoard.getPiece(ChessBoard.ROWS - endRow, endCol) != null && ChessBoard.getPiece(ChessBoard.ROWS - endRow, endCol).color.equals("White")) {
					return false;
				}
				return true;
			}
			System.out.println("sc"+startCol + "ec"+endCol +"sr"+ startRow + "er"+endRow);
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow + 1))) {
				System.out.println("four");
				if(ChessBoard.getPiece(ChessBoard.ROWS - endRow, endCol) == null || ChessBoard.getPiece(ChessBoard.ROWS - endRow, endCol).color.equals("Black")) {
					System.out.println("five");
					return false;
				}
				System.out.println("six");
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
