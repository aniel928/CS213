package chess;

import java.util.ArrayList;
import java.util.List;


/**
 * King piece to be used in Chess game.  Contains attributes and methods that are inherited from the abstract class {@link Piece}.
 * @author alh220
 * @author jmuccino
 *
 */
public class King extends Piece {
	
	/**
	 * Creates new King and sets the color and name.
	 * @param color a string ("White" or "Black") representing which player the piece belongs to.
	 */
	public King(Player color) {
		this.color = color;
		this.name = PieceName.KING;
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
		if(!this.moved) {
			if(this.color == Player.WHITE) {
				int[] arr = {7, 2}; 
				moves.add(arr);
				int[] arr2 = {7, 6};
				moves.add(arr2);
			}
			else {
				int[] arr = {0, 2}; 
				moves.add(arr);
				int[] arr2 = {0, 6};
				moves.add(arr2);
			}
		}
		if(row < 7) {
			int[] arr = {row + 1, col};
			moves.add(arr);
			if(col > 0) {
				int[] arr2 = {row + 1, col - 1};
				moves.add(arr2);
			}
			if(col < 7) {
				int arr2[] = {row + 1, col + 1};
				moves.add(arr2);
			}
		}
		if(col > 0) {
			int[] arr = {row, col - 1};
			moves.add(arr);
		}
		if(col < 7) {
			int[] arr = {row, col + 1};
			moves.add(arr);
		}
		if(row > 0) {
			int[] arr = {row - 1, col};
			moves.add(arr);
			if(col > 0) {
				int[] arr2 = {row - 1, col - 1};
				moves.add(arr2);
			}
			if(col < 7) {
				int[] arr2 = {row - 1, col + 1};
				moves.add(arr2);
			}
		}
		return moves;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		//if piece hasn't moved and king is trying to move two spaces to the left or right, then look for castle
		if(!this.moved && startRow == endRow) {
			//right side of board
			if(endCol == 6) {
				if(board.getPiece(startRow,  7) != null) {
					if(board.getPiece(startRow,7).getName() == PieceName.ROOK && !board.getPiece(startRow, 7).moved) {
						return true;
					}
				}
			}
			//left side of board.
			if(endCol == 2) {
				if(board.getPiece(startRow,  0) != null) {
					if(board.getPiece(startRow,  0).getName() == PieceName.ROOK && !board.getPiece(startRow,  0).moved) {
						return true;
					}
				}
			}
		}
		return ((startRow == endRow || Math.abs(startRow - endRow) == 1) && (startCol == endCol || Math.abs(startCol - endCol) == 1)); 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		if(startCol - endCol == 2) {
			//Queen Side Castling
			if(board.getPiece(startRow, startCol-1) != null || board.getPiece(startRow, startCol-2) != null || board.getPiece(startRow, startCol-3) != null) {
				return false;
			}
		}else if(endCol - startCol == 2) {
			//Regular Castling
			if(board.getPiece(startRow, startCol+1) != null || board.getPiece(startRow, startCol+2) != null) {
				return false;
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
			return "wK";
		}
		else {
			return "bK";
		}
	}

	


}
