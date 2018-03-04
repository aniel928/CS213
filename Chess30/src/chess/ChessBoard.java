package chess;

public interface ChessBoard {
	final int ROWS = 8;
	final int COLS = 8;
	static Piece[][] positions = new Piece[ROWS][COLS];
	
	static void initializeBoard() {
		positions[0][0] = new Rook("Black");
		positions[0][1] = new Knight("Black");
		positions[0][2] = new Bishop("Black");
		positions[0][3] = new Queen("Black");
		positions[0][4] = new King("Black");
		positions[0][5] = new Bishop("Black");
		positions[0][6] = new Knight("Black");
		positions[0][7] = new Rook("Black");

		positions[1][0] = new Pawn("Black");
		positions[1][1] = new Pawn("Black");
		positions[1][2] = new Pawn("Black");
		positions[1][3] = new Pawn("Black");
		positions[1][4] = new Pawn("Black");
		positions[1][5] = new Pawn("Black");
		positions[1][6] = new Pawn("Black");
		positions[1][7] = new Pawn("Black");

		positions[6][0] = new Pawn("White");
		positions[6][1] = new Pawn("White");
		positions[6][2] = new Pawn("White");
		positions[6][3] = new Pawn("White");
		positions[6][4] = new Pawn("White");
		positions[6][5] = new Pawn("White");
		positions[6][6] = new Pawn("White");
		positions[6][7] = new Pawn("White");
		
		positions[7][0] = new Rook("White");
		positions[7][1] = new Knight("White");
		positions[7][2] = new Bishop("White");
		positions[7][3] = new Queen("White");
		positions[7][4] = new King("White");
		positions[7][5] = new Bishop("White");
		positions[7][6] = new Knight("White");
		positions[7][7] = new Rook("White");
	}
	
	static String getBoard() {
		// TODO Auto-generated method stub
		String board = "";
		for(int i = 0; i< ROWS; i++) {
			for(int j = 0; j< COLS; j++) {
				if(positions[i][j] != null) {
					board += positions[i][j].toString()+ " ";
				}
				else {
					if((i % 2 == 0 && j %2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
						board += "   ";
					}
					else {
						board += "## ";
					}
				}
				if(j == (COLS - 1)) {
					board += (COLS - i) + "\n";
					if(i == (ROWS - 1)) {
						board += " a  b  c  d  e  f  g  h\n";
					}
				}
				
			}
		}
		return board;
	}
	
	static Piece getPiece(int row, int col) {
		return positions[row][col];
	}
	
	static void setPiece(int row, int col, Piece piece) {
		positions[row][col] = piece;
	}
}
