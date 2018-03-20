package chess;

import java.util.List;
/**
 * Piece is an abstract class that all chess pieces inherit from. All chess pieces have a color, a name, a moved-status, a list of legal moves (along with a method to see if a particular moved is legal), and a method to check to make sure no other pieces are in their way. 
 *   
 * @author alh220
 * @author jmuccino
 *
 */
public abstract class Piece {
	
	/**
	 * A {@link String} storing which team the piece belongs to. (e.g. "White" or "Black")
	 */
	protected String color;
	
	/**
	 * A {@link String} storing the name of the piece so we can easily figure out what it is in an array of abstract Pieces. (e.g. "Pawn", "Bishop", etc.)
	 */
	protected String name;
	
	/**
	 * A {@link Boolean} that stores whether or not the piece has moved.  
	 */
	protected boolean moved = false;

	/**
	 * Getter method to return which team the piece belongs to.
	 * @return {@link Piece.color}
	 */
	protected abstract String getColor();
	
	/**
	 * Getter method to return the name of the piece.
	 * @return {@link Piece.name}
	 */
	protected abstract String getName();
	
	/**
	 * Takes in starting position of move, as well as the board the move pertains to. Returns a list of all valid moves, primarily to check for checkmate or stalemate.    
	 * @param row an integer between 0 and 7 that corresponds to a board row. 
	 * @param col an integer between 0 and 7 that corresponds to a board column.
	 * @param board the {@link Chessboard} object to be referenced.
	 * @return {@link ArrayList} of integer pairs denoting legal ending positions on the board in {row, col} format.
	 * @see {@link Chess.checkForMate} - this method is called from with in the checkForMate() method.
	 * 		{@link Chessboard} - this method takes in a type of Chessboard.
	 */
	protected abstract List<int[]> allLegalMoves(int row, int col, ChessBoard board);
	
	/** 
	 * Takes in starting and ending positions, as well as the board to determine if a particular move is legal for this piece.
	 * @param startRow an integer between 0 and 7 that corresponds to the row of the starting position on the board.
	 * @param startCol an integer between 0 and 7 that corresponds to the column of the starting position on the board.
	 * @param endRow an integer between 0 and 7 that corresponds to the row of the ending position on the board.
	 * @param endCol an integer between 0 and 7 that corresponds to the column of the ending position on the board.
	 * @param board the {@link Chessboard} object to be referenced.
	 * @return {@link boolean} determining whether the move passed in is legal.
	 */
	protected abstract boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board);
	
	/**
	 *Takes in starting and ending positions, as well as the board to determine if a particular move is free to move or is being blocked by another piece on the board. 
	 * @param startRow an integer between 0 and 7 that corresponds to the row of the starting position on the board.
	 * @param startCol an integer between 0 and 7 that corresponds to the column of the starting position on the board.
	 * @param endRow an integer between 0 and 7 that corresponds to the row of the ending position on the board.
	 * @param endCol an integer between 0 and 7 that corresponds to the column of the ending position on the board.
	 * @param board the {@link Chessboard} object to be referenced.
	 * @return {@link boolean} determining whether this move is able to take place.
	 */
	protected abstract boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board);
}
