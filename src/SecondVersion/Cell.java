package SecondVersion;

public class Cell {

	public Seed seed;
	public int row;
	public int col;
	
	public enum Seed {O, X, EMPTY, O_INSIDE, X_INSIDE, EMPTY_INSIDE}
	
	public Cell(int row, int col){
		seed = Seed.EMPTY;
		this.row = row;
		this.col = col;
	}
	
	public void printCell(){
		switch (seed){
			case O:
				System.out.print(" O ");
				break;
			case X:
				System.out.print(" X ");
				break;
			case EMPTY:
				System.out.print("   ");
				break;
		}
	}
}
