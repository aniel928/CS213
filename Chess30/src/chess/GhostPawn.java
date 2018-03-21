package chess;

import java.util.List;

/**
 * <p>GhostPawn is a special piece to be used in Chess game.  Contains attributes and methods that are inherited 
 * from the abstract class {@link Piece}.</p>
 * <p>GhostPawn is used for En Passant, it only lasts for 1 turn and is not a visible piece on the board.
 * If the GhostPawn is taken by a Pawn of the opposite color, then the Pawn who left it there is also taken.
 * 
 * @author alh220
 * @author jmuccino
 */
public class GhostPawn extends Piece {

	/**
	 * Creates new GhostPawn and sets the color and name.
	 * @param color a string ("White" or "Black") representing which player the piece belongs to.
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
	 * {@inheritDoc} <p>In this particular case because this is a special piece that cannot move, a null value is returned.</p>
	 */
	protected List<int[]> allLegalMoves(int row, int col, ChessBoard board){
		return null;
	}

	/**
	 * {@inheritDoc} <p>In this particular case because this is a special piece that cannot move, a false value is returned.</p>
	 */
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		return false;
	}

	/**
	 * {@inheritDoc}  <p>In this particular case because this is a special piece that cannot move, a false value is returned.</p>
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
