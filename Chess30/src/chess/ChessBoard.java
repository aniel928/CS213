package chess;
/**
 * Representation of chessboard.  Contains all logic for getting/setting positions, making copies of the board, and returning current board layout.
 * 
 * @author alh220
 * @author jmuccino
 */
public class ChessBoard {
	final int ROWS = 8;
	final int COLS = 8;
	Piece[][] positions = new Piece[ROWS][COLS];
	
	/**
	 * Initializes the chessboard to the standard starting position
	 */
	protected void initializeBoard() {
		positions[0][0] = new Rook(Piece.Player.BLACK);
		positions[0][1] = new Knight(Piece.Player.BLACK);
		positions[0][2] = new Bishop(Piece.Player.BLACK);
		positions[0][3] = new Queen(Piece.Player.BLACK);
		positions[0][4] = new King(Piece.Player.BLACK);
		positions[0][5] = new Bishop(Piece.Player.BLACK);
		positions[0][6] = new Knight(Piece.Player.BLACK);
		positions[0][7] = new Rook(Piece.Player.BLACK);

		positions[1][0] = new Pawn(Piece.Player.BLACK);
		positions[1][1] = new Pawn(Piece.Player.BLACK);
		positions[1][2] = new Pawn(Piece.Player.BLACK);
		positions[1][3] = new Pawn(Piece.Player.BLACK);
		positions[1][4] = new Pawn(Piece.Player.BLACK);
		positions[1][5] = new Pawn(Piece.Player.BLACK);
		positions[1][6] = new Pawn(Piece.Player.BLACK);
		positions[1][7] = new Pawn(Piece.Player.BLACK);

		positions[6][0] = new Pawn(Piece.Player.WHITE);
		positions[6][1] = new Pawn(Piece.Player.WHITE);
		positions[6][2] = new Pawn(Piece.Player.WHITE);
		positions[6][3] = new Pawn(Piece.Player.WHITE);
		positions[6][4] = new Pawn(Piece.Player.WHITE);
		positions[6][5] = new Pawn(Piece.Player.WHITE);
		positions[6][6] = new Pawn(Piece.Player.WHITE);
		positions[6][7] = new Pawn(Piece.Player.WHITE);
		
		positions[7][0] = new Rook(Piece.Player.WHITE);
		positions[7][1] = new Knight(Piece.Player.WHITE);
		positions[7][2] = new Bishop(Piece.Player.WHITE);
		positions[7][3] = new Queen(Piece.Player.WHITE);
		positions[7][4] = new King(Piece.Player.WHITE);
		positions[7][5] = new Bishop(Piece.Player.WHITE);
		positions[7][6] = new Knight(Piece.Player.WHITE);
		positions[7][7] = new Rook(Piece.Player.WHITE);
	}
	
	/** 
	 * Draws out the board based on where pieces currently are.  If no piece is on the square, prints "##" 
	 * for brown or empty spaces "  " for white.  Also prints out rank and file.
	 * @return a string representation of the chessboard
	 */
	protected String getBoard() {
		//print empty line above board
		String board = "\n";
		//print one row at a time
		for(int i = 0; i< ROWS; i++) {
			for(int j = 0; j< COLS; j++) {
				//if there is a piece that is not a GhostPawn, get it's toString representation.
				if(positions[i][j] != null && positions[i][j].getName() != Piece.PieceName.GHOST) {
					board += positions[i][j].toString()+ " ";
				}
				//otherwise figure out if it's brown or white and print a brown or white square
				else {
					if((i % 2 == 0 && j %2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
						board += "   ";
					}
					else {
						board += "## ";
					}
				}
				//when we're at the very last column of the row, print the ranks and do new line.
				if(j == (COLS - 1)) {
					board += (COLS - i) + "\n";
					//when we're at the very last column of the very last row, print the files.
					if(i == (ROWS - 1)) {
						board += " a  b  c  d  e  f  g  h\n";
					}
				}
				
			}
		}
		return board;
	}
	
	/**
	 * Returns which piece is on the square.
	 * @param row an integer representing an index in the array for rank.
	 * @param col an integer representing an index in the array for file.
	 * @return a {@link Piece} object that is on the square
	 */
	protected Piece getPiece(int row, int col) {
		return positions[row][col];
	}
	
	/** 
	 * Sets the piece given on the square indicated
	 * @param row an integer representing an index in the array for rank
	 * @param col an integer representing an index in the array for file
	 * @param piece a {@link Piece} object to put onto the square
	 */
	protected void setPiece(int row, int col, Piece piece) {
		positions[row][col] = piece;
	}
	
	/** 
	 * Makes a copy of this board and returns that copy.
	 * @return a new instance of ChessBoard.
	 */
	protected ChessBoard makeCopy() {
		ChessBoard newBoard = new ChessBoard();
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				newBoard.positions[i][j] = this.positions[i][j];
			}
		}
		return newBoard;
	}
}
