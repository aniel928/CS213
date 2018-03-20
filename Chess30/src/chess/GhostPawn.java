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
		this.name = "ghost";
		this.color = color;
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
	protected List<int[]> allLegalMoves(int row, int col, ChessBoard board){
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Returns a two-character long representation of the piece.
	 */
	@Override
	public String toString() {
		return "gp";
	}

}
