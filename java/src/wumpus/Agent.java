package wumpus;

/**
 * 
 */

/**
 * @author reza
 *
 */
public class Agent {
	
	int outputMode = 0; //0=Statistics 1=Verbose

	private Cave cave = null;
	private int xPos = 0;
	private int yPos = 0;

	private int score = 0;

	private int direction = 0; 

	/*
	 * To Right = 0
	 * To Up    = 1
	 * To Left  = 2
	 * To Down  = 3
	 */


	KnowledgeBase kb;


	/**
	 * @param cave
	 * @param xPos
	 * @param yPos
	 * 
	 * Class constructor
	 */
	public Agent(Cave cave, int xPos, int yPos) {
		super();
		this.cave = cave;
		this.xPos = xPos;
		this.yPos = yPos;
		executeActions();
		kb = new KnowledgeBase(cave.getSize());


	}


	/**
	 * @param cave
	 * @param xPos
	 * @param yPos
	 * @param direction
	 * @param oMode
	 * 
	 * Class Constructor
	 * 
	 */
	public Agent(Cave cave, int xPos, int yPos, int direction, int oMode) {
		super();
		this.cave = cave;
		this.xPos = xPos;
		this.yPos = yPos;
		this.direction = direction;
		outputMode = oMode;
		executeActions();
		kb = new KnowledgeBase(cave.getSize());
	}


	/**
	 * This function the main loop of agent
	 */
	private void executeActions(){
		int resolutionSteps = 0;
		Perception currentPerception = cave.putAgentAt(xPos, yPos);
		do{
			if( currentPerception.isGlitter() ){
				System.out.println("Success!!!!! I grabbed the gold.");
				score += 1000;
				System.out.println("My Score is " + score);
				return;
			}
			if( currentPerception.isBreeze() )resolutionSteps += kb.addFact(new Proposition('B', true, xPos, yPos));
			else resolutionSteps += kb.addFact(new Proposition('B', false, xPos, yPos));
			if( currentPerception.isStench() ) resolutionSteps += kb.addFact(new Proposition('S', true, xPos, yPos));
			else resolutionSteps += kb.addFact(new Proposition('S', false, xPos, yPos));
			resolutionSteps += kb.addFact(new Proposition('V', true, xPos, yPos));
			if( currentPerception.isScream() )kb.wumpusHasBeenKilled();
			currentPerception = chooseAction();
			if(outputMode == 0) System.out.println(kb.numberOfFacts() + "\t" + kb.numberOfClauses() + "\t" + resolutionSteps);
			resolutionSteps = 0;

		}while(true);

	}

	/**
	 * 
	 * @return
	 * 
	 * This function finds the most appropriate action and gives it to the environment
	 */
	private Perception chooseAction(){
		int d = getHDirect();
		int l = getHLeft();
		int r = getHRight();
		int b = getHBack();
		if ( ( d >= l ) && ( d >= r ) && ( d >= b ) ) {
			score--;
			rewisePosition();
			return cave.getAction('M');
		}
		if ( ( l >= r ) && ( l >= b ) ) {
			score -=2;
			cave.getAction('L');
			direction++;
			if( direction > 3 )direction = 0;
			rewisePosition();
			return cave.getAction('M');
		}
		if ( r >= b ) {
			score -=2;
			cave.getAction('R');
			direction--;
			if( direction < 0 )direction = 3;
			rewisePosition();
			return cave.getAction('M');
		} else {
			score -=3;
			cave.getAction('L');
			cave.getAction('L');
			direction += 2;
			if( direction > 3 )direction -= 2;
			rewisePosition();
			return cave.getAction('M');
		}


	}

	/**
	 * 
	 * @return
	 * 
	 * Heauristic function
	 */
	private int getHDirect(){
		switch(direction){
		case 0:
			if(xPos<3){
				if ( kb.queryFact(new Proposition('P', true, ( xPos + 1 ), yPos)) )return -1001;
				if ( kb.queryFact(new Proposition('W', true, ( xPos + 1 ), yPos)) )return -1001;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos + 1 ), yPos)) ) && ( !kb.queryFact(new Proposition('V', true, ( xPos + 1 ), yPos))) ) )return 999;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos + 1 ), yPos)) ) && ( kb.queryFact(new Proposition('V', true, ( xPos + 1 ), yPos))) ) )return -1;
				if ( kb.queryFact (new Proposition('O', true, ( xPos + 1 ), yPos)) )   return -201;
			} else return -4000;
			break;
		case 1:
			if(yPos<3){
				if ( kb.queryFact(new Proposition('P', true, xPos, ( yPos + 1 ))) )return -1001;
				if ( kb.queryFact(new Proposition('W', true, xPos, ( yPos + 1 ))) )return -1001;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos + 1 ))) ) && ( !kb.queryFact(new Proposition('V', true, xPos, ( yPos + 1 )))) ) )return 999;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos + 1 ))) ) && ( kb.queryFact(new Proposition('V', true, xPos, ( yPos + 1 )))) ) )return -1;
				if ( kb.queryFact (new Proposition('O', true, xPos, ( yPos + 1 ))) )   return -201;
			} else return -4000;
			break;	
		case 2:
			if(xPos>0){
				if ( kb.queryFact(new Proposition('P', true, ( xPos - 1 ), yPos)) )return -1001;
				if ( kb.queryFact(new Proposition('W', true, ( xPos - 1 ), yPos)) )return -1001;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos - 1 ), yPos)) ) && ( !kb.queryFact(new Proposition('V', true, ( xPos - 1 ), yPos))) ) )return 999;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos - 1 ), yPos)) ) && ( kb.queryFact(new Proposition('V', true, ( xPos - 1 ), yPos))) ) )return -1;
				if ( kb.queryFact (new Proposition('O', true, ( xPos - 1 ), yPos)) )   return -201;
			} else return -4000;
			break;	
		case 3:
			if(yPos>0){
				if ( kb.queryFact(new Proposition('P', true, xPos, ( yPos - 1 ))) )return -1001;
				if ( kb.queryFact(new Proposition('W', true, xPos, ( yPos - 1 ))) )return -1001;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos - 1 ))) ) && ( !kb.queryFact(new Proposition('V', true, xPos, ( yPos - 1 )))) ) )return 999;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos - 1 ))) ) && ( kb.queryFact(new Proposition('V', true, xPos, ( yPos - 1 )))) ) )return -1;
				if ( kb.queryFact (new Proposition('O', true, xPos, ( yPos - 1 ))) )   return -201;
			} else return -4000;
			break;	
		default:
			return -4000;

		}
		return -4000;
	}


	/**
	 * 
	 * @return
	 * 
	 * Heauristic function
	 */
	private int getHLeft(){
		switch(direction){
		case 3:
			if(xPos<3){
				if ( kb.queryFact(new Proposition('P', true, ( xPos + 1 ), yPos)) )return -1002;
				if ( kb.queryFact(new Proposition('W', true, ( xPos + 1 ), yPos)) )return -1002;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos + 1 ), yPos)) ) && ( !kb.queryFact(new Proposition('V', true, ( xPos + 1 ), yPos))) ) )return 998;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos + 1 ), yPos)) ) && ( kb.queryFact(new Proposition('V', true, ( xPos + 1 ), yPos))) ) )return -2;
				if ( kb.queryFact (new Proposition('O', true, ( xPos + 1 ), yPos)) )   return -202;
			} else return -4001;
			break;
		case 0:
			if(yPos<3){
				if ( kb.queryFact(new Proposition('P', true, xPos, ( yPos + 1 ))) )return -1002;
				if ( kb.queryFact(new Proposition('W', true, xPos, ( yPos + 1 ))) )return -1002;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos + 1 ))) ) && ( !kb.queryFact(new Proposition('V', true, xPos, ( yPos + 1 )))) ) )return 998;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos + 1 ))) ) && ( kb.queryFact(new Proposition('V', true, xPos, ( yPos + 1 )))) ) )return -2;
				if ( kb.queryFact (new Proposition('O', true, xPos, ( yPos + 1 ))) )   return -202;
			} else return -4001;
			break;	
		case 1:
			if(xPos>0){
				if ( kb.queryFact(new Proposition('P', true, ( xPos - 1 ), yPos)) )return -1002;
				if ( kb.queryFact(new Proposition('W', true, ( xPos - 1 ), yPos)) )return -1002;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos - 1 ), yPos)) ) && ( !kb.queryFact(new Proposition('V', true, ( xPos - 1 ), yPos))) ) )return 998;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos - 1 ), yPos)) ) && ( kb.queryFact(new Proposition('V', true, ( xPos - 1 ), yPos))) ) )return -2;
				if ( kb.queryFact (new Proposition('O', true, ( xPos - 1 ), yPos)) )   return -202;
			} else return -4001;
			break;	
		case 2:
			if(yPos>0){
				if ( kb.queryFact(new Proposition('P', true, xPos, ( yPos - 1 ))) )return -1002;
				if ( kb.queryFact(new Proposition('W', true, xPos, ( yPos - 1 ))) )return -1002;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos - 1 ))) ) && ( !kb.queryFact(new Proposition('V', true, xPos, ( yPos - 1 )))) ) )return 998;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos - 1 ))) ) && ( kb.queryFact(new Proposition('V', true, xPos, ( yPos - 1 )))) ) )return -2;
				if ( kb.queryFact (new Proposition('O', true, xPos, ( yPos - 1 ))) )   return -202;
			} else return -4001;
			break;	
		default:
			return -4001;

		}
		return -4001;
	}
	
	/**
	 * 
	 * @return
	 * 
	 * 
	 * Heauristic function
	 */
	private int getHRight(){
		switch(direction){
		case 1:
			if(xPos<3){
				if ( kb.queryFact(new Proposition('P', true, ( xPos + 1 ), yPos)) )return -1002;
				if ( kb.queryFact(new Proposition('W', true, ( xPos + 1 ), yPos)) )return -1002;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos + 1 ), yPos)) ) && ( !kb.queryFact(new Proposition('V', true, ( xPos + 1 ), yPos))) ) )return 998;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos + 1 ), yPos)) ) && ( kb.queryFact(new Proposition('V', true, ( xPos + 1 ), yPos))) ) )return -2;
				if ( kb.queryFact (new Proposition('O', true, ( xPos + 1 ), yPos)) )   return -202;
			} else return -4001;
			break;
		case 2:
			if(yPos<3){
				if ( kb.queryFact(new Proposition('P', true, xPos, ( yPos + 1 ))) )return -1002;
				if ( kb.queryFact(new Proposition('W', true, xPos, ( yPos + 1 ))) )return -1002;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos + 1 ))) ) && ( !kb.queryFact(new Proposition('V', true, xPos, ( yPos + 1 )))) ) )return 998;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos + 1 ))) ) && ( kb.queryFact(new Proposition('V', true, xPos, ( yPos + 1 )))) ) )return -2;
				if ( kb.queryFact (new Proposition('O', true, xPos, ( yPos + 1 ))) )   return -202;
			} else return -4001;
			break;	
		case 3:
			if(xPos>0){
				if ( kb.queryFact(new Proposition('P', true, ( xPos - 1 ), yPos)) )return -1002;
				if ( kb.queryFact(new Proposition('W', true, ( xPos - 1 ), yPos)) )return -1002;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos - 1 ), yPos)) ) && ( !kb.queryFact(new Proposition('V', true, ( xPos - 1 ), yPos))) ) )return 998;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos - 1 ), yPos)) ) && ( kb.queryFact(new Proposition('V', true, ( xPos - 1 ), yPos))) ) )return -2;
				if ( kb.queryFact (new Proposition('O', true, ( xPos - 1 ), yPos)) )   return -202;
			} else return -4001;
			break;	
		case 0:
			if(yPos>0){
				if ( kb.queryFact(new Proposition('P', true, xPos, ( yPos - 1 ))) )return -1002;
				if ( kb.queryFact(new Proposition('W', true, xPos, ( yPos - 1 ))) )return -1002;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos - 1 ))) ) && ( !kb.queryFact(new Proposition('V', true, xPos, ( yPos - 1 )))) ) )return 998;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos - 1 ))) ) && ( kb.queryFact(new Proposition('V', true, xPos, ( yPos - 1 )))) ) )return -2;
				if ( kb.queryFact (new Proposition('O', true, xPos, ( yPos - 1 ))) )   return -202;
			} else return -4001;
			break;	
		default:
			return -4001;

		}
		return -4001;
	}
	
	/**
	 * 
	 *  
	 * @return
	 * 
	 * Heauristic function
	 */
	private int getHBack(){
		switch(direction){
		case 2:
			if(xPos<3){
				if ( kb.queryFact(new Proposition('P', true, ( xPos + 1 ), yPos)) )return -1003;
				if ( kb.queryFact(new Proposition('W', true, ( xPos + 1 ), yPos)) )return -1003;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos + 1 ), yPos)) ) && ( !kb.queryFact(new Proposition('V', true, ( xPos + 1 ), yPos))) ) )return 997;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos + 1 ), yPos)) ) && ( kb.queryFact(new Proposition('V', true, ( xPos + 1 ), yPos))) ) )return -3;
				if ( kb.queryFact (new Proposition('O', true, ( xPos + 1 ), yPos)) )   return -203;
			} else return -4002;
			break;
		case 3:
			if(yPos<3){
				if ( kb.queryFact(new Proposition('P', true, xPos, ( yPos + 1 ))) )return -1003;
				if ( kb.queryFact(new Proposition('W', true, xPos, ( yPos + 1 ))) )return -1003;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos + 1 ))) ) && ( !kb.queryFact(new Proposition('V', true, xPos, ( yPos + 1 )))) ) )return 997;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos + 1 ))) ) && ( kb.queryFact(new Proposition('V', true, xPos, ( yPos + 1 )))) ) )return -3;
				if ( kb.queryFact (new Proposition('O', true, xPos, ( yPos + 1 ))) )   return -203;
			} else return -4002;
			break;	
		case 0:
			if(xPos>0){
				if ( kb.queryFact(new Proposition('P', true, ( xPos - 1 ), yPos)) )return -1003;
				if ( kb.queryFact(new Proposition('W', true, ( xPos - 1 ), yPos)) )return -1003;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos - 1 ), yPos)) ) && ( !kb.queryFact(new Proposition('V', true, ( xPos - 1 ), yPos))) ) )return 997;
				if ( ( kb.queryFact( (new Proposition('O', true, ( xPos - 1 ), yPos)) ) && ( kb.queryFact(new Proposition('V', true, ( xPos - 1 ), yPos))) ) )return -3;
				if ( kb.queryFact (new Proposition('O', true, ( xPos - 1 ), yPos)) )   return -203;
			} else return -4002;
			break;	
		case 1:
			if(yPos>0){
				if ( kb.queryFact(new Proposition('P', true, xPos, ( yPos - 1 ))) )return -1003;
				if ( kb.queryFact(new Proposition('W', true, xPos, ( yPos - 1 ))) )return -1003;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos - 1 ))) ) && ( !kb.queryFact(new Proposition('V', true, xPos, ( yPos - 1 )))) ) )return 997;
				if ( ( kb.queryFact( (new Proposition('O', true, xPos, ( yPos - 1 ))) ) && ( kb.queryFact(new Proposition('V', true, xPos, ( yPos - 1 )))) ) )return -3;
				if ( kb.queryFact (new Proposition('O', true, xPos, ( yPos - 1 ))) )   return -203;
			} else return -4002;
			break;	
		default:
			return -4002;

		}
		return -4002;
	}
	
	/**
	 * This function changes internal agent's status and enables it to track its position
	 */
	private void rewisePosition(){
		//System.out.println("Changing Agent's Direction");
		switch( direction ){
		case 0:
			if( xPos < cave.getSize() - 1 )++xPos;
			else System.out.println("Agent is confused and trying move toward x and it is wall!");
			break;
		case 1:
			if( yPos < cave.getSize() - 1 )++yPos;
			else System.out.println("Agent is confused and trying move toward y and it is wall!");
			break;
		case 2:
			if( xPos > 0 )--xPos;
			else System.out.println("Agent is confused and trying move toward -x and it is wall!");
			break;
		case 3:
			if( yPos > 0 )--yPos;
			else System.out.println("Agent is confused and trying move toward -y and it is wall!");
			break;
		default:
			System.out.println("Agent is confused trying move toward nowhere!");
			break;
		}
	}

}
