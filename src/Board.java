import java.util.ArrayList;


public class Board {

	public int width;
	public int height;
	public char[][] board;
	public ArrayList<Point> prohibited_points;
	
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		initializeBoard();
		prohibited_points = new ArrayList<Point>();
	}
	
	public boolean isLegitimateCord(int row, int col){
		if(row<0||row>=height){
			return false;
		}
		if(col<0||col>=width){
			return false;
		}
		if(board[row][col]!=' '){
			return false;
		}
		// add the prohibited area (the area that is already taken)
		return true;
	}
	
	public boolean isFinished(){
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				if(board[row][col]==' '){
					return false;
				}
			}
		}
		return true;
	}
	
	public void initializeBoard(){
		board = new char[height][width];
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				board[row][col] = ' ';
			}
		}
	}
	
	public void print(){
		for(int row=0;row<height;row++){
			for(int col=0;col<width;col++){
				System.out.print(board[row][col]);
				if(col!=width){
					System.out.print(" | ");
				}
			}
			System.out.println();
		}
	}
}
