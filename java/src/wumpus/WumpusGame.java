package wumpus;

/**
 * Test change
 */

/**
 * @author reza
 *
 *Main class of program
 */
public class WumpusGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int outputMode = Integer.valueOf(args[0]).intValue();
		Cave myCave = new Cave(outputMode);
		Agent myAgent = new Agent(myCave, 0, 0, 0, outputMode);

	}

}
