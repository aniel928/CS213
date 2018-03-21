package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Bishop piece to be used in Chess game.  Contains attributes and methods that are inherited from the abstract class {@link Piece}. 
 * @author JMuccino
 * @author alh220
 *
 */
public class Bishop extends Piece {
	/**
	 * Creates new Bishop and sets the color and name.
	 * @param color a string ("White" or "Black") representing which player the piece belongs to.
	 */
	public Bishop(Player color) {
		this.color = color;
		this.name = PieceName.BISHOP;
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
		return Math.abs(startRow - endRow) == Math.abs(startCol - endCol);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		boolean properOrder = true;
		
		//move is up/left, make it the same as down/right (eliminate endpoints)
		if(startRow > endRow && startCol > endCol) {
			int temp = --startRow;   //6
			startRow = ++endRow;     //6
			endRow = temp;			//6
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
					if(board.getPiece(i, j).getName() != PieceName.GHOST ) {
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
		
		return true;
	}
	
	/**
	 * Returns a two-character long representation of the piece.
	 */
	@Override
	public String toString() {
		if(this.color == Player.WHITE) {
			return "wB";
		}
		else {
			return "bB";
		}
	}

}
