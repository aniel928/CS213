package chess;
//TEST 123
import java.util.Scanner;

public class Chess implements ChessBoard{
	private static boolean gameOver = false;
	private static String gameBoard;
	private static String turn = "White";
	private static boolean draw = false;
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		ChessBoard.initializeBoard();
		while(!gameOver) {
			System.out.println(ChessBoard.getBoard());

			System.out.print(turn + "'s move: ");
			
			String move = scanner.nextLine();
			
			String moves[] = move.split(" ");
			
			System.out.println("");
			
			if(moves.length > 3) {
				System.out.println("\nInvalid move please try again\n");
				continue;
			}
			else if(moves.length == 3) {
				if(moves[2].equals("draw?")) {
					draw = true; //now figure out what to do with this
				}
				else if(moves[2].equals("knight") || moves[2].equals("rook") || moves[2].equals("bishop") || moves[2].equals("queen")){
					System.out.println("Promotion");
					//flag for promotion
				}
				else {
					System.out.println("\nInvalid move please try again\n");
					continue;
				}
			}
			else if(moves.length == 1) {
				if(move.equals("resign") || (draw == true && move.equals("draw"))) {
					gameOver = true;
					System.out.println("Draw");
					continue;
				}
				else {
					System.out.println("\nInvalid move please try again\n");
					continue;
				}
			}
			else if(moves.length != 2) {
				System.out.println("\nInvalid move, please try again\n");
				continue;
			}
			
			
			if(!(moves.length == 3 && moves[2].equals("draw"))) {
				draw = false;
			}

			//validate input in format of letter/number letter number i.e. e2 e5
			if(moves[0].length() != 2 || moves[1].length() != 2) {
				System.out.println("\nInvalid move, please try again\n");
				continue;
			}
			
			
			int startRow;
			try{
				startRow = 8-Integer.parseInt(String.valueOf(moves[0].charAt(1)));
			}
			catch(Exception e){
				System.out.println("\nInvalid move, please try again\n");
				continue;
			}
			int startCol;
			
			switch(moves[0].charAt(0)) {
				case 'a':
					startCol = 0;
					break;
				case 'b':
					startCol = 1;
					break;
				case 'c':
					startCol = 2;
					break;
				case 'd':
					startCol = 3;
					break;
				case 'e':
					startCol = 4;
					break;
				case 'f':
					startCol = 5;
					break;
				case 'g':
					startCol = 6;
					break;
				case 'h':
					startCol = 7;
					break;
				default:
					System.out.println("\nInvalid move, please try again\n");
					continue;
			}
			
			
			int finishRow;
			try{
				finishRow = 8-Integer.parseInt(String.valueOf(moves[1].charAt(1)));
			}
			catch(Exception e){
				System.out.println("\nInvalid move, please try again\n");
				continue;
			}
			int finishCol;
			
			switch(moves[1].charAt(0)) {
				case 'a':
					finishCol = 0;
					break;
				case 'b':
					finishCol = 1;
					break;
				case 'c':
					finishCol = 2;
					break;
				case 'd':
					finishCol = 3;
					break;
				case 'e':
					finishCol = 4;
					break;
				case 'f':
					finishCol = 5;
					break;
				case 'g':
					finishCol = 6;
					break;
				case 'h':
					finishCol = 7;
					break;
				default:
					System.out.println("\nInvalid move, please try again\n");
					continue;
			}
			
							
			//check if there is currently a piece on e2 that belongs to current color.
			if(positions[startRow][startCol] != null) {
				
				if(positions[startRow][startCol].getColor().equals(turn)) {
					System.out.println("Valid");
				}
				else {
					System.out.println("Does not have piece of correct color!");
				}
			}
			//if yes, check for valid moves (function in piece)
			
			//if valid ,check for pieces in the way (except for knight)
			
			//check if another piece occupies destination (opposite color)
			
			//move current to new and remove current position
			
		
			
			
			
			
			//do some work here to actually move
			
			if(turn.equals("White")) {
				turn = "Black";
			}
			else {
				turn = "White";
			}
			
			
		}
		
		scanner.close();
		
		
		
	}

}
