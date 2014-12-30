import java.util.Scanner;


public class MAIN {

	public static int height = 10;
	public static int width = 10;
	
	public static int player_A_score = 0;
	public static int player_B_score = 0;
	
	public static void main(String[] args){
		Board board = new Board(width,height);
		play(board);
	}
	
	public static void play(Board board){
		int turn = 1; // 1 = A's turn, 2 = B's turn
		board.print();
		while(!board.isFinished()){
			if(turn==1){
				System.out.println("Player A's turn");
				Scanner scanner = new Scanner(System.in);
				int row = scanner.nextInt();
				int col = scanner.nextInt();
				while(!board.isLegitimateCord(row, col)){
					System.out.println("Not a legitimate coordinate; please reenter: ");
					row = scanner.nextInt();
					col = scanner.nextInt();
				}
				board.board[row][col] = 'o';
				board.print();
				turn = 2;
			}
			else if(turn==2){
				System.out.println("Player B's turn");
				Scanner scanner = new Scanner(System.in);
				int row = scanner.nextInt();
				int col = scanner.nextInt();
				while(!board.isLegitimateCord(row, col)){
					System.out.println("Not a legitimate coordinate; please reenter: ");
					row = scanner.nextInt();
					col = scanner.nextInt();
				}
				board.board[row][col] = 'x';
				board.print();
				turn = 1;
			}
		}
		System.out.println("Game finished");
		board.print();
		if(player_A_score>player_B_score){
			System.out.println("Player A wins");
		}
		else if(player_B_score>player_A_score){
			System.out.println("Player B wins");
		}
		else{
			System.out.println("Draw");
		}
	}
}
