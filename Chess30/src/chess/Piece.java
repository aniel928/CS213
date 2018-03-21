package chess;

import java.util.List;
/**
 * Piece is an abstract class that all chess pieces inherit from. All chess pieces have a color, 
 * a name, a moved-status, a list of legal moves (along with a method to see if a particular move 
 * is legal), and a method to check to make sure no other pieces are in their way. 
 *   
 * @author alh220
 * @author jmuccino
 *
 */
public abstract class Piece {
	
	/**
	 * An enum storing which player the piece belongs to. (e.g. "White" or "Black")
	 */
	protected Player color;
	
	/**
	 * An enum storing the name of the piece so we can easily figure out what 
	 * it is in an array of abstract Pieces.
	 */
	protected PieceName name;
	
	/**
	 * A that stores whether or not the piece has moved.  
	 */
	protected boolean moved = false;

	/**
	 * Getter method to return which team the piece belongs to.
	 * @return {@link #color}
	 */
	protected abstract Player getColor();
	
	/**
	 * Getter method to return the name of the piece.
	 * @return {@link #name}
	 */
	protected abstract PieceName getName();
	
	/**
	 * Takes in starting position of move, as well as the board the move pertains to. Returns a 
	 * list of all valid moves, primarily to check for checkmate or stalemate.    
	 * @param row an integer between 0 and 7 that corresponds to a board row. 
	 * @param col an integer between 0 and 7 that corresponds to a board column.
	 * @param board the {@link ChessBoard} object to be referenced.
	 * @return ArrayList of integer pairs denoting legal ending positions on the board 
	 * in row, col} format.
 	 *
	 */
	protected abstract List<int[]> allLegalMoves(int row, int col, ChessBoard board);
	
	/** 
	 * Takes in starting and ending positions, as well as the board to determine if a particular 
	 * move is legal for this piece.
	 * @param startRow an integer between 0 and 7 that corresponds to the row of the starting 
	 * position on the board.
	 * @param startCol an integer between 0 and 7 that corresponds to the column of the starting 
	 * position on the board.
	 * @param endRow an integer between 0 and 7 that corresponds to the row of the ending 
	 * position on the board.
	 * @param endCol an integer between 0 and 7 that corresponds to the column of the ending 
	 * position on the board.
	 * @param board the {@link ChessBoard} object to be referenced.
	 * @return boolean determining whether the move passed in is legal.
	 */
	protected abstract boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board);
	
	/**
	 * Takes in starting and ending positions, as well as the board to determine if a particular
	 * piece is free to move or is being blocked by another piece on the board. 
	 * @param startRow an integer between 0 and 7 that corresponds to the row of the starting 
	 * position on the board.
	 * @param startCol an integer between 0 and 7 that corresponds to the column of the starting 
	 * position on the board.
	 * @param endRow an integer between 0 and 7 that corresponds to the row of the ending position 
	 * on the board.
	 * @param endCol an integer between 0 and 7 that corresponds to the column of the ending position 
	 * on the board.
	 * @param board the {@link ChessBoard} object to be referenced.
	 * @return boolean determining whether this move is able to take place.
	 */
	protected abstract boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board);
}
