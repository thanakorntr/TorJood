package SecondVersion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import SecondVersion.Cell.Seed;

public class Board {

	private final int ROW = 10;
	private final int COL = 10;
	
	public int currentRow = -1;
	public int currentCol = -1;
	
	public Cell[][] board = new Cell[ROW][COL];
	
	public enum Player {PLAYER_1, PLAYER_2}  // PLAYER_1 = 'O', PLAYER_2 = 'X'
	public enum State {PLAYING, FINISHED}
	
	private int player1Score = 0;
	private int player2Score = 0;
	
	public State state;
	public Player turn;
	
	public Scanner scanner = new Scanner(System.in);
	
	public Board(){
		initializeBoard();
		state = State.PLAYING;
		turn = Player.PLAYER_1;
	}
	
	public void putSeed(int row, int col){
		if(turn == Player.PLAYER_1){
			board[row][col].seed = Seed.O;
		}
		else{
			board[row][col].seed = Seed.X;
		}
	}
	
	public void evaluateBoard(){
		HashSet<HashSet<Cell>> loops = getAllClosedLooops();
		if(!loops.isEmpty()){
			update(loops);
//			updateInsideLoop(loops);
			if(isFinished()){
				state = State.FINISHED;
			}			
		}
	}
	
	private void update(HashSet<HashSet<Cell>> loops){
				
		int newPlayer1Score = 0;
		int newPlayer2Score = 0;
		
		for(HashSet<Cell> loop : loops){
			if(loop.isEmpty() || loop.size() < 4){
				continue;
			}
			Iterator iterator = loop.iterator();
			Player boundaryCells = ((Cell)iterator.next()).seed == Seed.O ? Player.PLAYER_1 : Player.PLAYER_2;
			HashSet<Cell> allCellsInClosedLoop = getAllCellsInClosedCloop(loop);
			for(Cell cell : allCellsInClosedLoop){
				int row = cell.row;
				int col = cell.col;
				
				if(boundaryCells == Player.PLAYER_1){
					if(board[row][col].seed == Seed.X){
						board[row][col].seed = Seed.X_INSIDE;
						newPlayer1Score++;
					}
				}
				else if(boundaryCells == Player.PLAYER_2){
					if(board[row][col].seed == Seed.O){
						board[row][col].seed = Seed.O_INSIDE;
						newPlayer2Score++;
					}
				}
				
				if(board[row][col].seed == Seed.EMPTY){
					board[row][col].seed = Seed.EMPTY_INSIDE;
				}
			}
		}
		
		player1Score = newPlayer1Score;
		player2Score = newPlayer2Score;
	}
	
	public HashSet<HashSet<Cell>> getAllClosedLooops(){
		// TODO: ensure that all loops have at least 4 cells
		HashSet<HashSet<Cell>> allClosedLoops = new HashSet<HashSet<Cell>>();
		
		return allClosedLoops;
	}
	
	private HashSet<Cell> getAllCellsInClosedCloop(HashSet<Cell> loop){
		if(loop.isEmpty() || loop.size() < 4){
			return null;
		}
		
		int left = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		int bottom = Integer.MAX_VALUE;
		int top = Integer.MIN_VALUE;
		
		for(Cell cell : loop){
			int cellRow = cell.row;
			int cellCol = cell.col;
			if(cellCol < left){
				left = cellCol;
			}
			if(cellCol > right){
				right = cellCol;
			}
			if(cellRow < bottom){
				bottom = cellRow;
			}
			if(cellRow > top){
				top = cellRow;
			}
		}
		
		HashSet<Cell> insideCells = new HashSet<Cell>();
		for(int row = 0; row < ROW; row++){
			for(int col = 0; col < COL; col++){
				if(col > left && col < right && row > bottom && row < top){
					insideCells.add(board[row][col]);
				}
			}
		}
		return insideCells;
	}
	
	private boolean isFinished(){
		for(int row = 0; row < ROW; row++){
			for(int col = 0; col < COL; col++){
				if(board[row][col].seed == Seed.EMPTY){
					return false;
				}
			}
		}
		return true;
	}
	
	public void runGame(){
		
		while(state == State.PLAYING){
			printBoard();
			if(turn == Player.PLAYER_1){
				System.out.println("Player 1's turn \"O\"");
			}
			else if(turn == Player.PLAYER_2){
				System.out.println("Player 2's turn \"X\"");
			}
			
			System.out.println("X [1-10], Y [1-10]: ");
			currentCol = scanner.nextInt();
			currentRow = scanner.nextInt();
			
			if(currentRow < 1 || currentRow > 10 || currentCol < 1 || currentCol > 10 ){
				System.out.println("Invalid input row or column");
				continue;
			}
			if(board[currentRow-1][currentCol-1].seed != Seed.EMPTY){
				System.out.println("Occupied position");
				continue;
			}
			
			putSeed(currentRow-1, currentCol-1);
			evaluateBoard();
			switchTurn();
		}
		
		summarizeGame();
	}
	
	
	public void switchTurn(){
		if(turn == Player.PLAYER_1){
			turn = Player.PLAYER_2;
		}
		else{
			turn = Player.PLAYER_1;
		}
	}
	
	// Initialize all the cells in the board
	private void initializeBoard(){
		for(int row = 0; row < ROW; row++){
			for(int col = 0; col < COL; col++){
				board[row][col] = new Cell(row,col);
			}
		}
	}
	
	public void printBoard(){
		for(int row = ROW-1; row >= 0; row--){
			for(int col = 0; col < COL; col++){
				board[row][col].printCell();
				if(col != COL - 1){
					System.out.print("|");
				}
			}
			System.out.println();
			if(row != 0){
				System.out.println("---------------------------------------");
			}
		}
	}
	
	public void summarizeGame(){
		if(player1Score > player2Score){
			System.out.println("O wins!");
		}
		else if(player1Score < player2Score){
			System.out.println("X wins!");
		}
		else{
			System.out.println("Draw game!");
		}
	}
	
	
//	private void updateInsideLoop(HashSet<HashSet<Cell>> loops){
//	for(HashSet<Cell> loop : loops){
//		HashSet<Cell> allCellsInClosedLoop = getAllCellsInClosedCloop(loop);
//		for(Cell cell : allCellsInClosedLoop){
//			int row = cell.row;
//			int col = cell.col;
//			if(board[row][col].seed == Seed.EMPTY){
//				board[row][col].seed = Seed.EMPTY_INSIDE;
//			}
//		}
//	}
//  }
	
//	public boolean isInClosedLoop(HashSet<Cell> loop, int row, int col){
//		int left = Integer.MAX_VALUE;
//		int right = Integer.MIN_VALUE;
//		int bottom = Integer.MAX_VALUE;
//		int top = Integer.MIN_VALUE;
//		
//		for(Cell cell : loop){
//			int cellRow = cell.row;
//			int cellCol = cell.col;
//			if(cellCol < left){
//				left = cellCol;
//			}
//			if(cellCol > right){
//				right = cellCol;
//			}
//			if(cellRow < bottom){
//				bottom = cellRow;
//			}
//			if(cellRow > top){
//				top = cellRow;
//			}
//		}
//		
//		return (col > left && col < right && row > bottom && row < top) ? true : false;
//	}
}
