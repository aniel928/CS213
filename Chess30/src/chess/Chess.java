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
				if(moves[2].equals("draw")) {
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
					if(turn.equals("White")) {
						System.out.println("Black wins");
					}
					else {
						System.out.println("White wins");
					}
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
			
			draw = false;
			
			//do some real work
				
			//validate input in format of letternumber letter number i.e. e2 e5
			
			//check if there is currently a piece on e2 that belongs to current color.
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
