package chess;

import java.util.List;

/**
 * This is the GhostPawn class. It is used for En Passant.
 * The GhostPawn only lasts for 1 turn and is not a visible piece on the board.
 * If the GhostPawn is taken, then the Pawn in front of it is also taken.
 * @author 
 *
 */
public class GhostPawn extends Piece {

	/**
	 * This is the constructor for the GhostPawn.
	 * @param color
	 */
	public GhostPawn(String color) {
		this.piece = "ghost";
		this.color = color;
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
	 * A list of all legal moves for the GhostPawn.
	 */
	protected List<int[]> allLegalMoves(int row, int col, ChessBoard board){
		return null;
	}

	/**
	 * Checks to see if a move is legal.
	 */
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		return false;
	}

	/**
	 * Checks to see if a path is clear.
	 */
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Returns the GhostPawn piece.
	 */
	@Override
	public String toString() {
		return "gp";
	}

}
