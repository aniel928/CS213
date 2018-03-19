package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Code for the Queen piece.
 * @author 
 *
 */
public class Queen extends Piece {

	/**
	 * This is the constructor for the Queen piece.
	 * @param color
	 */
	public Queen(String color) {
		this.color = color;
		this.piece = "Queen";
	}
	
	/**
	 * Gets the color.
	 */
	@Override
	protected String getColor() {
		return this.color;
	}

	/**
	 * Gets the piece.
	 */
	@Override
	protected String getPiece() {
		return this.piece;
	}
	
	/**
	 * A list of all legal moves for the Queen.
	 */
	@Override
	protected List<int[]> allLegalMoves(int row, int col, ChessBoard board){
		List<int[]> moves = new ArrayList<int[]>();

		//all of this column
		for(int i = 0; i < 8; i++) {
			if(row != i) {
				int arr[] = {i, col};
				moves.add(arr);
			}
		}
		
		//all of this row
		for(int i = 0; i < 8; i++) {
			if(col != i) {
				int arr[] = {row, i};
				moves.add(arr);
			}
		}
		
		//up and left
		int i = Math.min(row, col);
		while(i > 0) {
			int[] arr = {row - 1, col - 1};
			moves.add(arr);
			i--;
		}
		
		//up and right
		i = row;
		int j = col;
		while(i > 0 && j < 7) {
			int[] arr = {row - 1, col + 1};
			moves.add(arr);
			i--;
			j++;
		}
		
		//down and left
		i = row;
		j = col;
		while(i < 7 && j > 0) {
			int[] arr = {row + 1, col -1};
			moves.add(arr);
			i++;
			j--;
		}
		
		//down and right
		i = Math.max(row, col);
		while(i < 7) {
			int[] arr = {row + 1, col + 1};
			moves.add(arr);
			i++;
		}
		
		return moves;
	}
	
	/**
	 * Checks to see if a move is legal.
	 */
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		return (Math.abs(startRow - endRow) == Math.abs(startCol - endCol)) || ((startRow == endRow) || (startCol == endCol));
	}
	
	/**
	 * Checks to see if the path is clear.
	 */
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
					if(!board.getPiece(startRow, i).getPiece().equals("ghost")) {
						return false;
					}
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
					if(!board.getPiece(i, startCol).getPiece().equals("ghost")) {
						return false;
					}
				}
				i++;
			}
				
		}
		 //move is up/left, make it the same as down/right (eliminate endpoints)
		else {
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
			}
			//move is down/left, eliminate endpoints
			else if(startRow < endRow && startCol > endCol) {
				properOrder = false;
				startRow++;
				endRow--;
				startCol--;
				endCol++;
			}
			int i = startRow;
			int j = startCol;
			
			while(i <= endRow) {
				if(board.getPiece(i, j) != null) {
					if(!board.getPiece(i, j).getPiece().equals("ghost")) {
						return false;
					}
				}
				i++;
				if(properOrder) {
					j++;
				}else {
					j--;
				}
			}
		}	
		return true;
	}
	
	/**
	 * Returns the color of the Queen.
	 */
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wQ";
		}
		else {
			return "bQ";
		}
	}

}
