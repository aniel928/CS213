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
		boolean properOrder = true;
		
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
				if(board.getPiece(i, startCol) != null) {
					return false;
				}
				i++;
			}
				
		}
		else //move is up/left, make it the same as down/right (eliminate endpoints)
			if(startRow > endRow && startCol > endCol) {
				int temp = --startRow;
				startRow = ++endRow;
				endRow = temp;
				temp = --startCol;      
				startCol = ++endCol;    
				endCol = temp;          
			}
			//move is down/right, eliminate endpoints
			else if(startRow < endRow && startCol < endCol) {
				startRow++;
				endRow--;
				startCol++;
				endCol--;
			}
			//move is up/right, make sme as down/left (elminate endpoints)
			else if(startRow > endRow && startCol < endCol) {
				properOrder = false;
				int temp = --startRow;	
				startRow = ++endRow;
				endRow = temp;
				temp = ++startCol;				
				startCol = --endCol;
				endCol = temp;
				System.out.println("sr"+startRow+"er" +endRow);
			}
			//move is down/left, eliminate endpoints
			else if(startRow < endRow && startCol > endCol) {
				properOrder = false;
				startRow++;
				endRow--;
				startCol--;
				endCol++;
			}else {
				System.out.println("this should never happen");
			}
			int i = startRow;
			int j = startCol;
			
				while(i <= endRow) {
					if(board.getPiece(i, j) != null) {
						return false;
					}
					i++;
					if(properOrder) {
						j++;
					}else {
						j--;
					}
				}
		
		return true;
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
