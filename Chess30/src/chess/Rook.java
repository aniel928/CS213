package chess;

import java.util.ArrayList;
import java.util.List;

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
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		return ((startRow == endRow) || (startCol == endCol));			
	}
	
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
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
				if(board.getPiece(startRow, i) != null) {
					return false;
				}
				i++;
			}
		}
		else if(startCol == endCol){
			if(startRow > endRow) {
				int temp = --startRow;
				startRow = ++endRow;
				endRow = temp;
			}
			else {
				startRow++;
				endRow--;
			}
			int i = startRow;
			while(i <= endRow) {
				if(board.getPiece(i, startCol) != null) {
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
		
		return valid;
	}


}
