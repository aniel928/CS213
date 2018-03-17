package chess;
import java.util.List;
import java.util.Scanner;

public class Chess{
	private static boolean gameOver = false;
	protected static String turn = "White";
	private static boolean draw = false;
	private static boolean promote = false;
	private static boolean goAgain = false;
	private static ChessBoard board;
	private static boolean castle = false;
	private static int[] WhiteKing, BlackKing;
	//Checks the format of the input and makes sure the user is giving good commands
	private static boolean checkMoves(String[] moves) {

		//should have at most 3 parameters 
		if(moves.length > 3) {
			return false;
		}
		//if there are three, it's either offering draw or a request to promote
		else if(moves.length == 3) {
			//offer to draw sets variable so on the next turn we know the user offered a draw.
			if(moves[2].equals("draw?")) {
				draw = true;;
			}
			//request to promote flags a variable for when we're ready to promote.  
			else if(moves[2].equals("N") || moves[2].equals("R") || moves[2].equals("B") || moves[2].equals("Q")){
				promote = true;
			}
			//otherwise, invalid input
			else {
				return false;
			}
		}
		//if there is only one parameter it should either be resign, or draw (in response to draw offer of other player)
		else if(moves.length == 1) {
			//if resign then announce winner
			if(moves[0].equals("resign")) {
				gameOver = true;
				if(turn.equals("White")) {
					System.out.println("Black wins");
				}else {
					System.out.println("White wins");
				}
				return true;
			} 
			//if draw, make sure variable is set then announce draw.
			if(draw == true && moves[0].equals("draw")){
				gameOver = true;
				System.out.println("Draw");
				return true;
			}
			//otherwise bad format
			else {
				return false;
			}
		}
		//if it's not a case above then there should only be two paramters, the starting and ending positions
		else if(moves.length != 2) {
			return false;
		}
		
		//Now that we're through all of that, if we haven't returned yet, there's more work to do.  
		//If we're here, then user didn't accept draw. Change draw back to false.
		if(!(moves.length == 3 && moves[2].equals("draw"))) {
			draw = false;
		}
		
		//Finally, validate that the first two paramters of input in format of letter/number letter number i.e. e2 e5
		if(moves[0].length() != 2 || moves[1].length() != 2) {
			return false;
		}
		
		//if we passed all of that, then input is good.
		return true;
	}
	
	private static void checkForPromotion(Piece piece, int row) {
		if((turn.equals("White") && row == 1) || (turn.equals("Black") && row == 6)) {
			promote = true;
		}
	}
	
	//handle promotion of piece.	
	private static Piece promote(String strPiece) {
		Piece piece = null;
		switch(strPiece) {
			case "R":	
				piece = new Rook(turn);
				break;
			case "N":
				piece = new Knight(turn);
				break;
			case "B": 
				piece = new Bishop(turn);
				break;
			case "Q":
				piece = new Queen(turn);
				break;
			default:
				piece = null;
		}
		return piece;
	}
	
	//This just prints out that the move is illegal.	
	private static void illegalMove() {
		System.out.print("Illegal move, try again: ");
		goAgain = true;
	}

	//En passant valid for only one move, so remove any ghost pawn at beginning of each turn.
	private static void removeEnpassant() {
		int i = 0;
		if(turn.equals("White")) {
			while(i < 8) {
				if(board.getPiece(5, i) != null && board.getPiece(5, i).getPiece().equals("ghost")) {
					board.setPiece(5, i, null);
					break;
				}
				i++;
			}
		}
		else {
			while(i < 8) {
				if(board.getPiece(2,  i) != null && board.getPiece(2, i).getPiece().equals("ghost")) {
					board.setPiece(2, i, null);
					break;
				}
				i++;
			}
		
		}
	}

	//if ghost pawn taken, remove pawn
	private static void enforceEnpassant(int endCol) {
		if(turn.equals("White")) {
			board.setPiece(3, endCol, null);
		}
		else {
			board.setPiece(4,  endCol,  null);
		}
	}
	
	//takes input of board position (i.e. a1 or e5) and returns where that is in the array
	private static int[] getArrayVals(String move) {
		int row;
		try{
			//row 8 is at top of board, so we need to reverse the input row #
			row = board.ROWS-Integer.parseInt(String.valueOf(move.charAt(1)));
		}
		catch(Exception e){
			//if not a valid row number (1-8), return -1's so we flag as error.
			row = -1;
		}
		
		int col;
		//a is left-most and g-is right most, so assign indexes as in array.
		switch(move.charAt(0)) {
			case 'a':
				col = 0;
				break;
			case 'b':
				col = 1;
				break;
			case 'c':
				col = 2;
				break;
			case 'd':
				col = 3;
				break;
			case 'e':
				col = 4;
				break;
			case 'f':
				col = 5;
				break;
			case 'g':
				col = 6;
				break;
			case 'h':
				col = 7;
				break;
			default:
				//if not a valid col letter (a-h), return -1's so we flag as error.
				col = -1;
		}
		
		//return positions (or -1's for error)
		int[] array = {row, col};
		return array;
		
	}
	
	//see if White is in check
	private static boolean whiteCheck(ChessBoard board) {
		for(int i = 0; i < board.ROWS; i++) {
			for(int j = 0; j < board.COLS; j++) {
				Piece piece = board.getPiece(i, j);
				if(piece != null && piece.getColor().equals("Black")) {
					if(piece.isLegalMove(i, j, WhiteKing[0], WhiteKing[1], board) && piece.coastClear(i, j, WhiteKing[0], WhiteKing[1], board)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//see if Black is in check
	private static boolean blackCheck(ChessBoard board) {
		for(int i = 0; i < board.ROWS; i++) {
			for(int j = 0; j < board.COLS; j++) {
				Piece piece = board.getPiece(i, j);
				if(piece != null && piece.getColor().equals("White")) {
					if(piece.isLegalMove(i, j, BlackKing[0], BlackKing[1], board) && piece.coastClear(i, j, BlackKing[0], BlackKing[1], board)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//check for Stalemate
	private static boolean checkforStaleMate() {
		//for each row and each col,
				for(int i = 0; i < board.ROWS; i++) {
					for(int j = 0; j < board.COLS; j++) {
						//grab the piece
						Piece tempPiece = board.getPiece(i, j);
						
						//if it's a piece of the opposite color, get all of it's moves
						if(tempPiece != null  && !tempPiece.getColor().equals(turn)) {
							List <int[]> moves = tempPiece.allLegalMoves(i, j, board);
							//for each move, create a temporary board and move the piece
							if(moves != null) {
								for(int[] move : moves) {
									ChessBoard tempBoard = board.makeCopy();
									if((tempBoard.getPiece(move[0], move[1]) == null || tempBoard.getPiece(move[0], move[1]).getColor().equals(turn)) && tempPiece.coastClear(i,  j,  move[0],  move[1],  tempBoard)) {
										tempBoard.setPiece(move[0],  move[1],  tempPiece);
										tempBoard.setPiece(i,  j,  null);
										if(tempPiece.getPiece().equals("King")) {
											if(turn.equals("White")) {
												BlackKing = move;
											}else {
												WhiteKing = move;
											}
										}
										//now check to see if check happened
										boolean check = false;
										if(turn.equals("White")) {
											check = blackCheck(tempBoard);
											if(tempPiece.getPiece().equals("King")) {
												BlackKing[0] = i;
												BlackKing[1] = j;
											}
										}
										else{
											check = whiteCheck(tempBoard);
											if(tempPiece.getPiece().equals("King")) {
												WhiteKing[0] = i;
												WhiteKing[1] = j;
											}
										}
										//if not currently in check, then there is a valid move. 
										if(!check) {
											return false;
										}
									}
								}
							}
						}
					}
				}
				//if get through entire double loop and never returned, stalemate
				return true;
	}
	
	
	//check for Checkmate 
	private static boolean checkForCheckMate() {
		//for each row and each col,
		for(int i = 0; i < board.ROWS; i++) {
			for(int j = 0; j < board.COLS; j++) {
				//grab the piece
				Piece tempPiece = board.getPiece(i, j);
				//if it's a piece of the opposite color, get all of it's moves
				if(tempPiece != null  && !tempPiece.getColor().equals(turn)) {
					List <int[]> moves = tempPiece.allLegalMoves(i, j, board);
					//for each move, create a temporary board and move the piece
					if(moves != null) {
						for(int[] move : moves) {
							ChessBoard tempBoard = board.makeCopy();
							if((tempBoard.getPiece(move[0], move[1]) == null || tempBoard.getPiece(move[0], move[1]).getColor().equals(turn)) && tempPiece.coastClear(i,  j,  move[0],  move[1],  tempBoard)) {
								tempBoard.setPiece(move[0],  move[1],  tempPiece);
								tempBoard.setPiece(i,  j,  null);
								if(tempPiece.getPiece().equals("King")) {
									if(turn.equals("White")) {
										BlackKing = move;
									}else {
										WhiteKing = move;
									}
								}
								//now check to see if there's still check
								boolean check = true;
								if(turn.equals("White")) {
									check = blackCheck(tempBoard);
									if(tempPiece.getPiece().equals("King")) {
										BlackKing[0] = i;
										BlackKing[1] = j;
									}
								}
								else{
									check = whiteCheck(tempBoard);
									if(tempPiece.getPiece().equals("King")) {
										WhiteKing[0] = i;
										WhiteKing[1] = j;
									}
								}
								//if no longer in check, not checkmate
								if(!check) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		//if get through entire double loop and never returned, checkmate
		return true;
	}
	
	//the actual game
	private static void playGame(Scanner scanner) {
		while(!gameOver) {
			//if pawn double moved last time, remove enpassant.
			removeEnpassant();
			
			if(goAgain) {
				goAgain = false;
			}
			else{
				//Prompt user for move and parse input
				System.out.print(turn + "'s move: ");
			}			
			
			String move = scanner.nextLine();
			String moves[] = move.split(" ");
			boolean validInput = checkMoves(moves);
			
			//check for errors in parsing.
			if(!validInput) {
				illegalMove();
				continue;
			}
			
			//check if someone resigned or agreed to draw
			if(gameOver) {
				continue;
			}
			
			//get positions of starting spot
			int[] pos = getArrayVals(moves[0]);
			int startRow = pos[0];
			int startCol = pos[1];
			
			//get position of ending spot
			pos = getArrayVals(moves[1]);
			int endRow = pos[0];
			int endCol = pos[1];
			
			//check to see if something went wrong when getting positions
			if(startRow == -1 || startCol == -1 || endRow == -1 || endCol == -1) {
				illegalMove();
				continue;
			}
			
			//Store this piece in a variable
			Piece piece = board.getPiece(startRow,  startCol);
			
			//if promotion got flagged and this piece is not a pawn, illegal move
			if(promote && !piece.getPiece().equals("Pawn")){
				promote = false;
				illegalMove();
				continue;
			}
			if(promote && ((piece.getColor().equals("White") && endRow != 0) || (piece.getColor().equals("Black") && endRow != 7))) {
				promote = false;
				illegalMove();
				continue;				
			}
			
			//check if there is currently a piece on starting spot that belongs to current color.
			if(piece != null) {
				if(!piece.getColor().equals(turn)) {
					illegalMove();
					continue;
				}
			}else {
				illegalMove();
				continue;
			}
			
			//if yes, check for valid moves (function in piece)
			if(!(piece.isLegalMove(startRow, startCol, endRow, endCol, board))) {
				illegalMove();
				continue;
			}
			//if valid ,check for pieces in the way (except for knight)
			if(!(piece.coastClear(startRow, startCol, endRow, endCol, board))){
				illegalMove();
				continue;
			}
			
			//check if another piece occupies destination (opposite color)
			if(board.getPiece(endRow,  endCol) != null && board.getPiece(endRow, endCol).getColor().equals(turn)) {
				illegalMove();
				continue;
			}
			
			//store in case issue with check and need to revert
			Piece oldPiece = board.getPiece(endRow, endCol);	
			
			//check for promotion
			if(piece.getPiece().equals("Pawn")) {
				checkForPromotion(piece, startRow);
			}			
			
			if(promote) {
				String promoteTo = "";
				if(moves.length < 3) {
					promoteTo = "Q";
				}
				else {
					promoteTo = moves[2];
				}
				piece = promote(promoteTo);
				if(piece == null) {
					illegalMove();
					continue;
				}
			}
			
			//check for Castle
			if(piece.getPiece().equals("King") && Math.abs(startCol - endCol) == 2) {
				//if Black and not in check, then you're good.
				if(turn.equals("Black") && !blackCheck(board)) {
					castle = true;
				}
				//if Black and in check, not so good.
				else if(turn.equals("Black")) {
					illegalMove();
					continue;
				}
				//if White and not in check, good
				else if(turn.equals("White") && !whiteCheck(board)) {
					castle = true;
				}
				//if White and in check, not so good.
				else if(turn.equals("White")) {
					illegalMove();
					continue;
				}
			}
			
			//handle castle
			if(castle) {
				if(startCol - endCol == 2) {
					board.setPiece(endRow,  endCol, piece);
					oldPiece = board.getPiece(endRow, 0);
					board.setPiece(endRow, 3, oldPiece);
					board.setPiece(startRow, startCol, null);
					board.setPiece(endRow, 0, null);
					piece.moved = true;
					oldPiece.moved = true;
				}
				else if(startCol - endCol == -2) {
					board.setPiece(endRow,  endCol, piece);
					oldPiece = board.getPiece(endRow, 7);
					board.setPiece(endRow, 5, oldPiece);
					board.setPiece(startRow, startCol, null);
					board.setPiece(endRow, 7, null);
					piece.moved = true;
					oldPiece.moved = true;
				}
			}
			//no castle, so do regular move instead.
			else {
				//move current to new and remove current position
				board.setPiece(endRow, endCol, piece);
				piece.moved = true;
				board.setPiece(startRow, startCol, null);
				
				//if en passant, then enforce.
				if(piece.getPiece().equals("Pawn") && oldPiece != null && oldPiece.getPiece().equals("ghost")) {
					enforceEnpassant(endCol);
				}
			}

			if(piece.getPiece().equals("King")) {
				if(turn.equals("White")) {
					WhiteKing[0] = endRow;
					WhiteKing[1] = endCol;
				}
				else {
					BlackKing[0] = endRow;
					BlackKing[1] = endCol;
				}
			}
			
			
			//Check both Kings in Check/CheckMate
			boolean whiteCheck = whiteCheck(board);
			boolean blackCheck = blackCheck(board);

			//if current color in check, return error and revert move
			if((turn.equals("White") && whiteCheck) || (turn.equals("Black") && blackCheck)) {
				illegalMove();
				if(castle) {
					if(startCol - endCol == 2) {
						board.setPiece(startRow, startCol, piece);
						board.setPiece(startRow,  0, oldPiece);
						board.setPiece(endRow, endCol, null);
						board.setPiece(endRow, 3, null);
					}
					else if(startCol - endCol == -2) {
						board.setPiece(startRow, startCol, piece);
						board.setPiece(startRow,  7, oldPiece);
						board.setPiece(endRow, endCol, null);
						board.setPiece(endRow, 5, null);
					}
					if(turn.equals("White")) {
						WhiteKing[0] = 7;
						WhiteKing[1] = 4;
					}
					if(turn.equals("Black")) {
						BlackKing[0] = 0;
						BlackKing[1] = 4;
					}
				}else if(promote){
					piece = new Pawn(turn);
					board.setPiece(startRow, startCol, piece);
					board.setPiece(endRow, endCol, oldPiece);
				}else {
					board.setPiece(startRow, startCol, piece);
					board.setPiece(endRow, endCol, oldPiece);
				}
				continue;
			}
			//if other color in check, check for checkmate, then return either "Check" or "Checkmate"
			else if((turn.equals("White") && blackCheck) || (turn.equals("Black") && whiteCheck)) {
				//check for checkmate
				boolean checkMate = checkForCheckMate();
				if(checkMate) {
					System.out.println(board.getBoard());

					System.out.println("Checkmate\n"+turn+" wins");
					gameOver = true;
				}else {
					System.out.println(board.getBoard());
					System.out.println("Check");
				}
				
			}
			//Stalemate here
			else if(checkforStaleMate()) {
				System.out.println(board.getBoard());
				System.out.println("Stalemate");
				System.out.println("Draw");
				gameOver = true;
			}
			else {
				System.out.println(board.getBoard());
			}
			castle = false;
			promote = false;
			
			
			//Change turns			
			if(turn.equals("White")) {
				turn = "Black";
			}
			else {
				turn = "White";
			}
			
			

		}
	}
	
	public static void main(String[] args){
		//set up scanner
		Scanner scanner = new Scanner(System.in);
		board = new ChessBoard();
		//initialize chessboard - put all pieces in starting positions
		board.initializeBoard();
		WhiteKing = new int[2];
		WhiteKing[0] = 7;
		WhiteKing[1] = 4;
		BlackKing = new int[2];
		BlackKing[0] = 0;
		BlackKing[1] = 4;
		
		System.out.println(board.getBoard());
		
		//play the game!
		playGame(scanner);
		
		//game is over, so close up shop.
		scanner.close();
		
	}

}
