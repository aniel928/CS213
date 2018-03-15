package chess;
//TEST 123
import java.util.Scanner;

public class Chess{
	private static boolean gameOver = false;
	protected static String turn = "White";
	private static boolean draw = false;
	private static boolean promote = false;
	private static boolean goAgain = false;
	
	//Checks the format of the input and makes sure the user is giving good commands
	private static boolean checkMoves(String[] moves) {

		//should have at most 3 paraemters
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
			else if(moves[2].equals("knight") || moves[2].equals("rook") || moves[2].equals("bishop") || moves[2].equals("queen")){
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
				System.out.println("\n\nDraw");
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
	
	//handle promotion of piece.
	private static void promote(ChessBoard board, String piece, int col, int row) {
//		if(!(row == 0 && board.getPiece(row,col).piece.equals("Pawn"))) {
//			System.out.println("fuck");
//			//throw invalid move
//		}
		System.out.println("Promote pawn to "+piece+" at array["+row+"]["+col+"].");
	}
	
	//This just prints out that the move is invalid.
	private static void invalidMove() {
		goAgain = true;
		System.out.println("\n\nInvalid move, please try again\n");
	}
	
	private static void illegalMove() {
		System.out.println("\n\n Illegal move\n");
		goAgain = true;
	}
	
	private static void removeEnpassant(ChessBoard board) {
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
	
	private static void enforceEnpassant(int endCol, ChessBoard board) {
		if(turn.equals("White")) {
			board.setPiece(3, endCol, null);
		}
		else {
			board.setPiece(4,  endCol,  null);
		}
	}

	
	//takes input of board position (i.e. a1 or e5) and returns where that is in the array
	private static int[] getArrayVals(String move, ChessBoard board) {
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
	
	private static void playGame(Scanner scanner, ChessBoard board) {
		while(!gameOver) {
			
			removeEnpassant(board);
			
			//get the current board.
			if(goAgain) {
				goAgain = false;
			}
			else{
				System.out.println(board.getBoard());
			}
			
			//Prompt user for move and parse input
			System.out.print(turn + "'s move: ");
			String move = scanner.nextLine();
			String moves[] = move.split(" ");
			boolean validInput = checkMoves(moves);
			
			//check for errors in parsing.
			if(!validInput) {
				System.out.println("You fucked up your paramters");
				invalidMove();
				continue;
			}
			
			//check if someone resigned or agreed to draw
			if(gameOver) {
				continue;
			}
			
			//get positions of starting spot
			int[] pos = getArrayVals(moves[0], board);
			int startRow = pos[0];
			int startCol = pos[1];
			
			//get position of ending spot
			pos = getArrayVals(moves[1], board);
			int endRow = pos[0];
			int endCol = pos[1];
			
			//check to see if something went wrong when getting positions
			if(startRow == -1 || startCol == -1 || endRow == -1 || endCol == -1) {
				System.out.println("You fucked up board positions");
				invalidMove();
				continue;
			}
			
			//Store this piece in a variable
			Piece piece = board.getPiece(startRow,  startCol);
			//check if there is currently a piece on e2 that belongs to current color.
			if(piece != null) {
				
				if(piece.getColor().equals(turn)) {
					System.out.println("Valid");
				}
				else {
					System.out.println("That's not your piece!");
					invalidMove();
					continue;
				}
			}else {
				System.out.println("There is no piece!");
				invalidMove();
				continue;
			}
			
			//if yes, check for valid moves (function in piece)
			if(!(piece.isLegalMove(board.ROWS-startRow, startCol, board.ROWS-endRow, endCol, board))) {
				System.out.println("SHITTY MOVE");
				illegalMove();
				continue;
			}
			//if valid ,check for pieces in the way (except for knight)
			if(!(piece.coastClear(startRow, startCol, endRow, endCol, board))){
				System.out.println("Coast not clear");
				illegalMove();
				continue;
			}
			
			//check if another piece occupies destination (opposite color)
			if(board.getPiece(endRow,  endCol) != null) {
				if(!board.getPiece(endRow, endCol).getColor().equals(turn)) {
					System.out.println("Valid");
				}
				else {
					System.out.println("Does not have piece of correct color!");
					continue;
				}
			}
			
			if(promote) {
				promote(board, moves[2], startRow, startCol);
			}
			
			//store in case issue with check and need to revert
			Piece oldPiece = board.getPiece(endRow, endCol);
			
			//move current to new and remove current position
			board.setPiece(endRow, endCol, piece);
			board.setPiece(startRow, startCol, null);
			
			if(oldPiece != null && oldPiece.getPiece().equals("ghost")) {
				enforceEnpassant(endCol, board);
			}
			//Check both Kings in Check/CheckMate
				//if current color in check, return error and revert cahnge
				//if current color in check, return "Check"
			
			
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
		ChessBoard board = new ChessBoard();
		//initialize chessboard - put all pieces in starting positions
		board.initializeBoard();
		
		//play the game!
		playGame(scanner, board);
		
		//game is over, so close up shop.
		scanner.close();
		
	}

}
