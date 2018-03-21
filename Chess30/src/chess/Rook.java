package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Rook piece to be used in Chess game.  Contains attributes and methods that are inherited from the abstract class {@link Piece}.
 * @author alh220
 * @author jmuccino
 *
 */
public class Rook extends Piece {
	
	/**
	 * Creates new Rook and sets the color and name.
	 * @param color a string ("White" or "Black") representing which player the piece belongs to.
	 */
	public Rook(Player color) {
		this.color = color;
		this.name = PieceName.ROOK;
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
		return moves;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		return ((startRow == endRow) || (startCol == endCol));			
	}
	
	/**
	 * {@inheritDoc}
	 */
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
		return true;
	}
	
	/**
	 * Returns a two-character long representation of the piece.
	 */
	@Override
	public String toString() {
		if(this.color == Player.WHITE) {
			return "wR";
		}
		else {
			return "bR";
		}
	}

}
