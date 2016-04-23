package wumpus;

/**
 * 
 */

/**
 * @author reza
 *This class simulates the environment for the Agent.
 */
public class Cave {
	private int outputMode = 0; //0=Statistics 1=Verbose
	
	private int agentX = 0;
	private int agentY = 0;
	
	private int t = 0;

	private int agentDirection = 0; 

	/*
	 * To Right = 0
	 * To Up    = 1
	 * To Left  = 2
	 * To Down  = 3
	 */

	private boolean agentHasArrow = true;
	private boolean agentGrabbedGold = false;

	private boolean wumpusAlive = true;
	private int wumpusX = 0;
	private int wumpusY = 0;

	private boolean[][] pits = new boolean[4][4];

	private int goldX = 0;
	private int goldY = 0;

	/**
	 * @param oMode
	 * Main constructor of the environment
	 */
	public Cave(int oMode) {
		super();
		pits[2][0] = true;
		pits[2][2] = true;
		pits[3][3] = true;
		
		outputMode = oMode;
		
		wumpusX = 0;
		wumpusY = 2;
		
		goldX = 1;
		goldY = 2;

		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * 
	 * It initializes agent's position 
	 */
	public Perception putAgentAt( int x, int y){
		agentX = x;
		agentY = y;
		drawScreen('N');
		return new Perception(getStench(agentX, agentY), getBreezeState(agentX, agentY), getGlitter(agentX, agentY), false, !wumpusAlive);
	}


	/**
	 * 
	 * @param action
	 * @return
	 * 
	 * This function gets the action and gives back the corresponding perception
	 */
	public Perception getAction(char action){
		Perception output = stateChange(action);
		drawScreen(action);
		return output;
	}
	
	/**
	 * 
	 * This function the state changes of agent in the environment
	 */
	
	public Perception stateChange(char action){
		++t;
		if ( action == 'L' ){
				++agentDirection;
				if( agentDirection > 3 )agentDirection = 0;
				return new Perception(getStench(agentX, agentY), getBreezeState(agentX, agentY), getGlitter(agentX, agentY), false, !wumpusAlive);			
			}
		if ( action == 'R' ){
			--agentDirection;
			if( agentDirection < 0 )agentDirection = 3;
			return new Perception(getStench(agentX, agentY), getBreezeState(agentX, agentY), getGlitter(agentX, agentY), false, !wumpusAlive);
		}
		if ( action == 'M' ){
			switch (agentDirection) {
			case 0:
				if ( agentX == 3 )
					return new Perception(getStench(agentX, agentY), getBreezeState(agentX, agentY), getGlitter(agentX, agentY), true, !wumpusAlive);
				else
					agentX++;
				break;
			case 1:
				if ( agentY == 3 )
					return new Perception(getStench(agentX, agentY), getBreezeState(agentX, agentY), getGlitter(agentX, agentY), true, !wumpusAlive);
				else
					agentY++;
				break;
			case 2:
				if ( agentX == 0 )
					return new Perception(getStench(agentX, agentY), getBreezeState(agentX, agentY), getGlitter(agentX, agentY), true, !wumpusAlive);
				else
					agentX--;
				break;
			case 3:
				if ( agentY == 0 )
					return new Perception(getStench(agentX, agentY), getBreezeState(agentX, agentY), getGlitter(agentX, agentY), true, !wumpusAlive);
				else
					agentY--;
				break;
			default:
				System.out.println("Invalid moving direction. Environment confused!!!!");
				break;
			};
		}
		if ( action == 'S' ){
			if ( !agentHasArrow ){
				System.out.println("Invalid shooting action. Agent doesn't have the arrow any more!!!!");
			} else {
				System.out.println("Agent is shooting. Agent doesn't have the arrow any more!!!!");
				agentHasArrow = false;
				switch (agentDirection) {
				case 0:
					if ( ( agentX < wumpusX ) && ( agentY == wumpusY ) )
						wumpusAlive = false;
				case 1:
					if ( ( agentY < wumpusY ) && ( agentX == wumpusX ) )
						wumpusAlive = false;
				case 2:
					if ( ( agentX > wumpusX ) && ( agentY == wumpusY ) )
						wumpusAlive = false;
				case 3:
					if ( ( agentY > wumpusY ) && ( agentX == wumpusX ) )
						wumpusAlive = false;
				default:
					System.out.println("Invalid moving direction. Environment confused!!!!");
					break;
				}

			}
		}
		if ( pits[agentX][agentY] ){
			System.out.println("Agent has been fallen in a pit. Game over!!!!");
			return null;
		}

		if ( ( agentX==wumpusX ) && ( agentY==wumpusY ) ){
			System.out.println("Agent has been eaten with wumpus. Game over!!!!");
			return null;
		}
		return new Perception(getStench(agentX, agentY), getBreezeState(agentX, agentY), getGlitter(agentX, agentY), false, !wumpusAlive);

	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * 
	 * This function considers the Breeze perception according to the agent's position
	 */

	private boolean getBreezeState(int x, int y){
		if( ( x + 1 ) < 4 ){
			if( pits[ x + 1 ][y] ) return true;
		}
		if( ( x - 1 ) >= 0 ){
			if( pits[ x - 1 ][y] ) return true;
		}		
		if( ( y + 1 ) < 4 ){
			if( pits[x][ y + 1 ] ) return true;
		}
		if( ( y - 1 ) >= 0 ){
			if( pits[x][ y - 1 ] ) return true;
		}		
		return false;
	}

	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * 
	 * This function considers the Stench perception according to the agent's position
	 */
	private boolean getStench(int x, int y){
		if( (( x + 1 ) < 4 ) && (( x + 1)==wumpusX) && (y==wumpusY) )return true;
		if( (( x - 1 ) >= 0 ) && (( x - 1)==wumpusX) && (y==wumpusY) )return true;
		if( (( y + 1 ) < 4 ) && (( y + 1)==wumpusY) && (x==wumpusX) )return true;
		if( (( y - 1 ) >= 0 ) && (( y - 1)==wumpusY) && (x==wumpusX) )return true;
		return false;
	}

	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * 
	 * This function considers the Glitter perception according to the agent's position
	 */
	private boolean getGlitter(int x, int y){
		return( ( x==goldX ) && ( y==goldY ) );
	}

	/**
	 * 
	 * @param recentAction
	 * 
	 * This function draws the output according to the execution mode
	 */
	
	private void drawScreen(char recentAction){
		if( t > 500 ){
			System.out.println("Answer unreachable!!! Crashed");
			System.exit(0);
		}
		if( outputMode == 1 ){
			System.out.println("Time = " + t);
			for( int j = 3 ; j > -1 ; --j ){
				for( int i = 0 ; i < 4 ; ++i ){
					System.out.print("|");
					if( ( i == agentX ) && ( j == agentY ) ){
						if( agentDirection == 0 )System.out.print(">");
						if( agentDirection == 1 )System.out.print("^");
						if( agentDirection == 2 )System.out.print("<");
						if( agentDirection == 3 )System.out.print("v");				
					} else if( ( i == wumpusX ) && ( j == wumpusY ) )System.out.print("W");
					else if( pits[i][j] )System.out.print("*");
					else System.out.print(" ");
				}
				System.out.println("|");
			}
		} else if ( recentAction == 'M' ){
			System.out.print(t+"\t");
		}else System.out.println(t+"\t");

	}

}
