package chess;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

	public Queen(String color) {
		this.color = color;
		this.piece = "Queen";
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
		return (Math.abs(startRow - endRow) == Math.abs(startCol - endCol)) || ((startRow == endRow) || (startCol == endCol));
	}
	
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wQ";
		}
		else {
			return "bQ";
		}
	}

	@Override
	protected List<Integer[]> validMoves(int startRow, int startCol) {
		List<Integer[]> valid = new ArrayList<>();
		
		//add everything in same column
		for(int i = 0; i < 8; i++) {
			if(i != startRow) {
				Integer[] arr = {startCol, i};
				valid.add(arr);
			}
		}
		
		//add everything in same column
		for(int i = 0; i < 8; i++) {
			if(i != startCol) {
				Integer[] arr = {i, startCol};
				valid.add(arr);
			}
		}
		
		//need to add diaganols still
		
		return valid;
	}


}
