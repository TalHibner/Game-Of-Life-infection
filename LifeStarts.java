package panoplyTest;
/*
Any live cell with fewer than two live neighbors dies, as if by needs caused by underpopulation.
Any live cell with more than three live neighbors dies, as if by overcrowding.
Any live cell with two or three live neighbors lives, unchanged, to the next generation.
Any dead cell with exactly three live neighbors cells will come to life.
*/
public class LifeStarts {
	public static void main(String[] args)
			 throws java.lang.InterruptedException{
		//program [width] [height] [infect-after] [max-generations] [seed]
		//I assume the input is valid - if not i could use Apache Commons CLI library
		//but I believe that it isn't part of the task
		int generation = 0;
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		int infectAfter = Integer.parseInt(args[2]);
		int maxGenerations = Integer.parseInt(args[3]);
		String[] seedInput = args[4].split(" "); ;
		int[] seed = new int[seedInput.length];
		for (int i = 0; i < seedInput.length; i++) {
		    seed[i] = Integer.parseInt(seedInput[i]);
		}
		boolean infected = false;
		Life earth = new Life(width, height, seed);
		generation++;
		earth.drawWorld();
		
		while(generation <= maxGenerations){
			if(generation > infectAfter) {
				infected = true;
			}
			Thread.sleep(200);
			earth.nextGeneration(infected);
			earth.drawWorld();
			generation++;
		}
	}
}

//Tests
/*
 * 
 * 3 3 3 6 "1 0 0 1 0 0 1 0 1"
 * 
 * 
 * 4 4 20 30 "1 0 0 1 0 0 1 0 1 0 0 0 1 0 0 1"
 * 
 * 
 * 10 10 6 100 "0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0"
 * 
 * 
 */
