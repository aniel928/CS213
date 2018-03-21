package chess;

import java.util.ArrayList;
import java.util.List;


/**
 * Queen  piece to be used in Chess game.  Contains attributes and methods that are inherited from the abstract class {@link Piece}.
 * @author alh220
 * @author jmuccino 
 *
 */
public class Queen extends Piece {

	/**
	 * Creates new Queen and sets the color and name.
	 * @param color a string ("White" or "Black") representing which player the piece belongs to.
	 */
	public Queen(Player color) {
		this.color = color;
		this.name = PieceName.QUEEN;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Player getColor() {
		return this.color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PieceName getName() {
		return this.name;
	}
	
	/**
	 * {@inheritDoc}
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
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		return (Math.abs(startRow - endRow) == Math.abs(startCol - endCol)) || ((startRow == endRow) || (startCol == endCol));
	}
	
	/**
	 * {@inheritDoc}
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
					if(board.getPiece(startRow, i).getName() != PieceName.GHOST) {
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
					if(board.getPiece(i, startCol).getName() != PieceName.GHOST) {
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
					if(board.getPiece(i, j).getName() != PieceName.GHOST) {
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
	 * Returns a two-character long representation of the piece.
	 */
	@Override
	public String toString() {
		if(this.color == Player.WHITE) {
			return "wQ";
		}
		else {
			return "bQ";
		}
	}

}
