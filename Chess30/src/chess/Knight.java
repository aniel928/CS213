package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Knight piece to be used in Chess game.  Contains attributes and methods that are inherited from the abstract class {@link Piece}.
 * @author alh220
 * @author jmuccino
 *
 */
public class Knight extends Piece {

	/**
	 * Creates new Knight and sets the color and name.
	 * @param color a string ("White" or "Black") representing which player the piece belongs to.
	 */
	public Knight(Player color) {
		this.color = color;
		this.name = PieceName.KNIGHT;
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
		//top
		if(row > 1) {
			if(col > 0) {
				int[] arr = {row - 2, col - 1};
				moves.add(arr);
			}
			if(col < 7) {
				int[] arr = {row - 2, col + 1};
				moves.add(arr);
			}
		}
		//left
		if(col > 1) {
			if(row > 0) {
				int[] arr = {row - 1, col - 2};
				moves.add(arr);
			}
			if(row < 7) {
				int[] arr = {row + 1, col - 2};
				moves.add(arr);
			}
		}
		//right
		if(col < 6) {
			if(row > 0) {
				int[] arr = {row - 1, col + 2};
				moves.add(arr);
			}
			if(row < 7) {
				int[] arr = {row + 1, col + 2};
				moves.add(arr);		
			}
		}
		//bottom
		if(row < 6) {
			if(col > 0) {
				int[] arr = {row + 2, col - 1};
				moves.add(arr);
			}
			if(col < 7) {
				int[] arr = {row + 2, col + 1};
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
		return (Math.abs(startRow - endRow) == 1 && Math.abs(startCol - endCol) == 2) || (Math.abs(startRow - endRow) == 2 && Math.abs(startCol - endCol) == 1);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Returns a two-character long representation of the piece.
	 */
	@Override
	public String toString() {
		if(this.color == Player.WHITE) {
			return "wN";
		}
		else {
			return "bN";
		}
	}

}
