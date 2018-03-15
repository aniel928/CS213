package chess;
import java.util.Scanner;

public class Chess{
	private static boolean gameOver = false;
	protected static String turn = "White";
	private static boolean draw = false;
	private static boolean promote = false;
	private static boolean goAgain = false;
	private static ChessBoard board;
	private static boolean castle = false;

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
	
	private static void checkForPromotion(Piece piece, int row) {
		if((turn.equals("White") && row == 1) || (turn.equals("Black") && row == 6)) {
			promote = true;
		}
	}
	
	//handle promotion of piece.	
	private static Piece promote(String strPiece, int row) {
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
				System.out.println("Not valid promotion");
		}
	//	System.out.println("Promote pawn to "+piece+" at array["+row+"]["+col+"].");
		promote = false;
		return piece;
	}
	
	//This just prints out that the move is illegal.	
	private static void illegalMove() {
		System.out.println("\n\n Illegal move\n");
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
	
	//the actual game
	private static void playGame(Scanner scanner) {
		while(!gameOver) {
			//if pawn double moved last time, remove enpassant.
			removeEnpassant();
			
			//print the current board, unless there was an invalid move.
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
				System.out.println("You fucked up board positions");
				illegalMove();
				continue;
			}
			
			//Store this piece in a variable
			Piece piece = board.getPiece(startRow,  startCol);
			
			//if promotion got flagged and this piece is not a pawn, illegal move
			if(promote && !piece.getPiece().equals("Pawn")){
				System.out.println("Promotion not on pawn");
				promote = false;
				illegalMove();
				continue;
			}
			if(promote && ((piece.getColor().equals("White") && endRow != 0) || (piece.getColor().equals("Black") && endRow != 7))) {
				System.out.println("Promotion not valid except last row");
				promote = false;
				illegalMove();
				continue;				
			}
			
			//check if there is currently a piece on e2 that belongs to current color.
			if(piece != null) {
				if(piece.getColor().equals(turn)) {
					System.out.println("Valid");
				}
				else {
					System.out.println("That's not your piece!");
					illegalMove();
					continue;
				}
			}else {
				System.out.println("There is no piece!");
				illegalMove();
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
				System.out.println("Coast not clear!");
				illegalMove();
				continue;
			}
			
			//check if another piece occupies destination (opposite color)
			if(board.getPiece(endRow,  endCol) != null) {
				if(!board.getPiece(endRow, endCol).getColor().equals(turn)) {
					System.out.println("Valid");
				}
				else {
					System.out.println("You can't kill yourself!");
					continue;
				}
			}
			
			if(piece.getPiece().equals("King") && Math.abs(startCol - endCol) == 2) {
				castle = true;
			}
			
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
				piece = promote(promoteTo, startCol);
				if(piece == null) {
					illegalMove();
					continue;
				}
			}
			
			//store in case issue with check and need to revert
			Piece oldPiece = board.getPiece(endRow, endCol);
			
			if(castle) {
				if(startCol - endCol == 2) {
					board.setPiece(endRow,  endCol, piece);
					Piece rook = board.getPiece(endRow, 0);
					board.setPiece(endRow, 3, rook);
					board.setPiece(startRow, startCol, null);
					board.setPiece(endRow, 0, null);
				}
				else if(startCol - endCol == -2) {
					board.setPiece(endRow,  endCol, piece);
					Piece rook = board.getPiece(endRow, 0);
					board.setPiece(endRow, 5, rook);
					board.setPiece(startRow, startCol, null);
					board.setPiece(endRow, 7, null);
				}
				castle = false;
			}
			else {
				
				//move current to new and remove current position
				board.setPiece(endRow, endCol, piece);
				piece.moved = true;
				board.setPiece(startRow, startCol, null);
				
				if(oldPiece != null && oldPiece.getPiece().equals("ghost")) {
					enforceEnpassant(endCol);
				}
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
		board = new ChessBoard();
		//initialize chessboard - put all pieces in starting positions
		board.initializeBoard();
		
		//play the game!
		playGame(scanner);
		
		//game is over, so close up shop.
		scanner.close();
		
	}

}
