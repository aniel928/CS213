package chess;
//TEST 123
import java.util.Scanner;
test 123123
public class Chess {
	static boolean gameOver = false;
	static String gameBoard;
	static String turn = "White";
	static boolean draw = false;
	
	private static String getBoard() {
		// TODO Auto-generated method stub
		return 	"bR bN bB bQ bK bB bN bR 8\n" + 
				"bp bp bp bp bp bp bp bp 7\n" + 
				"   ##    ##    ##    ## 6\n" + 
				"##    ##    ##    ##    5\n" + 
				"   ##    ##    ##    ## 4\n" + 
				"##    ##    ##    ##    3 \n" + 
				"wp wp wp wp wp wp wp wp 2\n" + 
				"wR wN wB wQ wK wB wN wR 1\n" + 
				" a  b  c  d  e  f  g  h\n";
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while(!gameOver) {
			
			System.out.println(getBoard());
		
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
			else {
				
				//do some real work
				
			}
			
			
			
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
