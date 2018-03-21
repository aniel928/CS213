package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Pawn  piece to be used in Chess game.  Contains attributes and methods that are inherited from the abstract class {@link Piece}.
 * @author alh220
 * @author jmuccino
 *
 */
public class Pawn extends Piece {
	
	/**
	 * Creates new Pawn and sets the color and name.
	 * @param color a string ("White" or "Black") representing which player the piece belongs to.
	 */
	public Pawn(String color) {
		this.color = color;
		this.name = "Pawn";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getColor() {
		return this.color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getName() {
		return this.name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<int[]> allLegalMoves(int row, int col, ChessBoard board){
		List<int[]> moves = new ArrayList<int[]>();
		if(this.color == "White") {
			//first move
			if(row == 6) {
				if(board.getPiece(row - 2, col) == null) {
					int[] arr = {row - 2, col};
					moves.add(arr);
				}
			}
			if(board.getPiece(row - 1, col) == null) {
				int[] arr = {row - 1, col};
				moves.add(arr);
			}
			if(col > 0) {
				if(board.getPiece(row - 1, col - 1) != null && !board.getPiece(row - 1, col - 1).getName().equals("ghost")) {
					int[] arr = {row - 1, col - 1};
					moves.add(arr);
				}
			}
			if(col < 7) {
				if(board.getPiece(row - 1, col + 1) != null && !board.getPiece(row - 1, col + 1).getName().equals("ghost")) {
					int[] arr = {row - 1, col + 1};
					moves.add(arr);
				}
			}
		}else {
			//first move
			if(row == 1) {
				if(board.getPiece(row + 2, col) == null) {
					int[] arr = {row + 2, col};
					moves.add(arr);
				}
				
			}
			if(board.getPiece(row + 1, col) == null) {
				int[] arr = {row + 1, col};
				moves.add(arr);
			}
			if(col > 0) {
				if(board.getPiece(row + 1, col - 1) != null && !board.getPiece(row + 1, col - 1).getName().equals("ghost")) {	
					int[] arr = {row + 1, col - 1};
					moves.add(arr);
				}
			}
			if(col < 7) {
				if(board.getPiece(row + 1, col + 1) != null && !board.getPiece(row + 1, col + 1).getName().equals("ghost")) {
					int[] arr = {row + 1, col + 1};
					moves.add(arr);
				}
			}
		}
		return moves;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {	
		//if White pawn
		if(this.color.equals("White")) {
			//cover first move - white
			if(startCol == endCol && startRow == 6 && endRow == 4) {
				if(board.getPiece(endRow, endCol) != null) {
					return false;
				}
				return true;
			}
			if(startCol == endCol && (startRow == (endRow + 1))){
				if(board.getPiece(endRow, endCol) != null && board.getPiece(endRow,  endCol).color.equals("Black")) {
					return false;
				}
				return true;
			}
			
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow + 1))) {
				if(board.getPiece(endRow, endCol) == null) {
					return false;
				}if(board.getPiece(endRow, endCol).color.equals("White")) {
					return false;
				}
				return true;
			}
		}
		//black pawn
		else {
			//cover first move - black
			if(startCol == endCol && startRow == 1 && endRow == 3) {
				if(board.getPiece(endRow, endCol) != null) {
					return false;
				}
				return true;
			}
			if(startCol == endCol && (startRow == (endRow - 1))){
				if(board.getPiece( endRow, endCol) != null && board.getPiece(endRow, endCol).color.equals("White")) {
					return false;
				}
				return true;
			}
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow - 1))) {
				if(board.getPiece(endRow, endCol) == null || board.getPiece(endRow, endCol).color.equals("Black")) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		if(this.color.equals("White")){
			if(startRow == 6 && endRow == 4) {
				if(board.getPiece(5, startCol) != null) {
					return false;
				}
				Piece ghostPawn = new GhostPawn(this.color);
				board.setPiece(5, startCol, ghostPawn);
			}
		}
		else{
			if(startRow == 1 && endRow == 3) {
				if(board.getPiece(2, startCol) != null) {
					return false;
				}
				Piece ghostPawn = new GhostPawn(this.color);
				board.setPiece(2, startCol, ghostPawn);
			}
		}
		return true;
	}
	
	/**
	 * Returns a two-character long representation of the piece.
	 */
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wp";
		}
		else {
			return "bp";
		}
	}

}
