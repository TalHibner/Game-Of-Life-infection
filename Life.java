package panoplyTest;

public class Life {
	private int[][] world;
	private int height;
	private int width;
	private int[] seed;
	
	Life(int width, int height, int[] seed){
		this.width = width;
		this.height = height;
		this.seed = seed;
		createNewWorld(width, height, seed);
	}

	// Contains the logic for the starting scenario.
	// Which cells are alive or dead in generation 0.
	private void createNewWorld(int width, int height, int[] seed){
		int seedIndex = 0;
		int[][] newWorld = new int[height][width];
		for(int row = 0; row < newWorld.length; row++ ){
			for(int col = 0; col < newWorld[row].length; col++ ){
				newWorld[row][col] = seed[seedIndex];
				seedIndex++;
			}
		}
		world = newWorld;
	}

	// Draws the world in the terminal.
	public void drawWorld(){
/*		System.out.print("\033[H\033[2J"); // Simulates a clear screen on linux machines
*/		for(int row = 0; row < world.length; row++ ){
			for(int col = 0; col < world[row].length; col++ ){
				 System.out.print(world[row][col]);
				 System.out.print(' ');
			}
		}
		System.out.println();
	}

	// Create the next generation
	public void nextGeneration(boolean infected){
		int[][] newWorld = new int[height][width];
		for(int row = 0; row < newWorld.length; row++ ){
			for(int col = 0; col < newWorld[row].length; col++ ){
				newWorld[row][col] = isAlive(row, col, infected);
			}
		}
		world = newWorld;
	}

	// Calculate if an individual cell should be alive in the next generation.
	// Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
	private int isAlive(int row, int col, boolean infected){
		int liveNeighbors = 0;
		int liveNeighborsHV = 0;
		int cellCurrentlyAlive = world[row][col];
		
	    // Look NW
	    if ((row - 1 >= 0) && (col - 1 >= 0)) {
	    	liveNeighbors = world[row - 1][col - 1] == 1? liveNeighbors + 1 : liveNeighbors;
	    }
	    // Look W
	    if ((row >= 0) && (col - 1 >= 0)) {
	    	liveNeighbors = world[row][col - 1] ==1 ? liveNeighbors + 1 : liveNeighbors;
	    	liveNeighborsHV = world[row][col - 1] ==1 ? liveNeighborsHV + 1 : liveNeighborsHV;
	    }
	    // Look SW
	    if ((row + 1 < world.length) && (col - 1 >= 0)) {
	    	liveNeighbors = world[row + 1][col - 1] ==1 ? liveNeighbors + 1 : liveNeighbors;
	    }
	    // Look S
	    if ((row + 1 < world.length) && (col < world[0].length)) {
	    	liveNeighbors = world[row + 1][col] ==1 ? liveNeighbors + 1 : liveNeighbors;
	    	liveNeighborsHV = world[row + 1][col] ==1 ? liveNeighborsHV + 1 : liveNeighborsHV;
	    }
	    // Look SE
	    if ((row + 1 < world.length) && (col + 1 < world[0].length)) {
	    	liveNeighbors = world[row + 1][col + 1] ==1 ? liveNeighbors + 1 : liveNeighbors;
	    }
	    // Look E
	    if ((row < world.length) && (col + 1 < world[0].length)) {
	    	liveNeighbors = world[row][col + 1] ==1 ? liveNeighbors + 1 : liveNeighbors;
	    	liveNeighborsHV = world[row][col + 1] ==1 ? liveNeighborsHV + 1 : liveNeighborsHV;
	    }
	    // Look NE
	    if ((row - 1 >= 0) && (col + 1 < world[0].length)) {
	    	liveNeighbors = world[row - 1][col + 1] ==1 ? liveNeighbors + 1 : liveNeighbors;
	    }
	    // Look N
	    if ((row - 1 >= 0) && (col < world[0].length)) {
	    	liveNeighbors = world[row - 1][col] ==1 ? liveNeighbors + 1 : liveNeighbors;
	    	liveNeighborsHV = world[row - 1][col] ==1 ? liveNeighborsHV + 1 : liveNeighborsHV;
	    }		

		// Since all cells are counted including the cell we are calculating.
		// We must subtract 1 from the liveCount if the cell we are calculating for is alive.
		if(cellCurrentlyAlive == 1){
			liveNeighbors--;
		}
	
		// The game of life rules in code form.
		return checkRules(liveNeighbors, liveNeighborsHV, cellCurrentlyAlive, infected);
	}
	
	private int checkRules(int liveNeighbors, int liveNeighborsHV, int cellCurrentlyAlive, boolean infected){
		if(!infected) {
			// No change
	        if (liveNeighbors == 2) {		
	        	return cellCurrentlyAlive;
	        }
	        // Cell stays alive, or a new cell is born
	        else if (liveNeighbors == 3) {
	            return 1;
	        }
	        // If under-populated or over-populated, cell dies
	        //if ((liveNeighbors < 2) || (liveNeighbors > 3))
			return 0;
		}
		else {// The game of life: infection rules in code form.
			//Any dead cell with a single live neighbor lives on to the next generation.
			if(cellCurrentlyAlive==0 && liveNeighbors == 1){
				return 1;
			}
			//Any live cell with no horizontal or vertical live neighbors dies.
			else if(cellCurrentlyAlive==1 && liveNeighborsHV > 0){
				return 1;
			} 
			else {
				return 0;
			}
		}
	}
}
