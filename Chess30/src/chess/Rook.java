package chess;

public class Rook extends Piece {
	
	public Rook(String color) {
		this.color = color;
		this.piece = "Rook";
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
		return ((startRow == endRow) || (startCol == endCol));			
	}
	
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol) {
		System.out.println("SR" + startRow + "SC" + startCol + "ER" +  endRow + "EC"+ endCol);
		if(startRow == endRow) {
			if(startCol > endCol) {
				int temp = --startCol;
				startCol = ++endCol;
				endCol = temp;
			}
			else {
				startCol++;
				endCol--;
			}
			int i = startCol;
			while(i <= endCol) {
				if(ChessBoard.getPiece(startRow, i) != null) {
					return false;
				}
				i++;
			}
		}
		else if(startCol == endCol){
			System.out.println("else");
			if(startRow > endRow) {
				int temp = --startRow;
				startRow = ++endRow;
				endRow = temp;
				System.out.println("sr"+startRow+"er" +endRow);
			}
			else {
				startRow++;
				endRow--;
			}
			int i = startRow;
			while(i <= endRow) {
				System.out.println(i);
				if(ChessBoard.getPiece(i, startCol) != null) {
					return false;
				}
				i++;
			}
				
		}
		return true;
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wR";
		}
		else {
			return "bR";
		}
	}


}
