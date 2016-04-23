package wumpus;

/**
 * 
 */

/**
 * @author reza
 *
 */
public class KnowledgeBase {

	private Clause[] clauses;
	
	private int recentNumberOfResolutionSteps = 0;
	
	private boolean wumpusAlive = true;

	/**
	 * 
	 * @param p
	 * @return
	 * 
	 * This function adds facts, used by agent
	 */
	public int addFact(Preposition p){
	recentNumberOfResolutionSteps = 0;
	if( wumpusAlive ){
		if( addClause(new Clause(p)) ) resolve();
		
	}else{
		if ( p.getType() != 'S' )
			if( addClause(new Clause(p)) ) resolve();
	}
	return recentNumberOfResolutionSteps;
	}

	/**
	 * 
	 * @param q
	 * @return
	 * 
	 * This function returns the answer of queries.
	 * 
	 * 
	 */
	public boolean queryFact(Preposition q){
		for( int i = 0 ; i < clauses.length ; ++i )
			if( clauses[i].isFact(q) ) return true;
		return false;
	}




	/**
	 * 
	 */
	public KnowledgeBase() {
		super();
		initializeRules();

	}


	/**
	 * This function initializes reasoning rules
	 */
	private void initializeRules(){
		int i = 0, j = 0;

		for( i = 0 ; i < 4 ; ++i )     // V(x,y) => O(x,y)
			for( j = 0 ; j < 4 ; ++j ){
				Clause temp = new Clause();
				temp.addPreposition(new Preposition('V', false, i, j));
				temp.addPreposition(new Preposition('O', true, i, j));
				addClause(temp);
			}

		for( i = 0 ; i < 4 ; ++i )     // ~P(x,y) & ~W(x,y) => O(x,y)
			for( j = 0 ; j < 4 ; ++j ){
				Clause temp = new Clause();
				temp.addPreposition(new Preposition('P', true, i, j));
				temp.addPreposition(new Preposition('W', true, i, j));
				temp.addPreposition(new Preposition('O', true, i, j));
				addClause(temp);
			}


		for( i = 0 ; i < 4 ; ++i )     
			for( j = 0 ; j < 4 ; ++j ){
				// ~B(x,y) => ~P(x-1,y)
				if( i > 0 ){ 
					Clause temp = new Clause();
					temp.addPreposition(new Preposition('B', true, i, j));
					temp.addPreposition(new Preposition('P', false, ( i - 1 ), j));
					addClause(temp);
				}
				// ~B(x,y) => ~P(x+1,y)
				if( i < 3 ){ 
					Clause temp = new Clause();
					temp.addPreposition(new Preposition('B', true, i, j));
					temp.addPreposition(new Preposition('P', false, ( i + 1 ), j));
					addClause(temp);
				}
				// ~B(x,y) => ~P(x,y-1)
				if( j > 0 ){ 
					Clause temp = new Clause();
					temp.addPreposition(new Preposition('B', true, i, j));
					temp.addPreposition(new Preposition('P', false, i, ( j - 1 )));
					addClause(temp);
				}
				// ~B(x,y) => ~P(x,y+1)
				if( j < 3 ){ 
					Clause temp = new Clause();
					temp.addPreposition(new Preposition('B', true, i, j));
					temp.addPreposition(new Preposition('P', false, i, ( j + 1 )));
					addClause(temp);
				}
			}

		for( i = 0 ; i < 4 ; ++i )     
			for( j = 0 ; j < 4 ; ++j ){
				// ~S(x,y) => ~W(x-1,y)
				if( i > 0 ){ 
					Clause temp = new Clause();
					temp.addPreposition(new Preposition('S', true, i, j));
					temp.addPreposition(new Preposition('W', false, ( i - 1 ), j));
					addClause(temp);
				}
				// ~S(x,y) => ~W(x+1,y)
				if( i < 3 ){ 
					Clause temp = new Clause();
					temp.addPreposition(new Preposition('S', true, i, j));
					temp.addPreposition(new Preposition('W', false, ( i + 1 ), j));
					addClause(temp);
				}
				// ~S(x,y) => ~W(x,y-1)
				if( j > 0 ){ 
					Clause temp = new Clause();
					temp.addPreposition(new Preposition('S', true, i, j));
					temp.addPreposition(new Preposition('W', false, i, ( j - 1 )));
					addClause(temp);
				}
				// ~S(x,y) => ~W(x,y+1)
				if( j < 3 ){ 
					Clause temp = new Clause();
					temp.addPreposition(new Preposition('S', true, i, j));
					temp.addPreposition(new Preposition('W', false, i, ( j + 1 )));
					addClause(temp);
				}
			}



	}

	/**
	 * @param newClause
	 * @return true if it can add some knowledge to knowledge base and false if it is not a valuable knowledge
	 * 
	 *  This function add clauses to KB
	 */
	private boolean addClause(Clause newClause){
		if( clauses == null ) {
			clauses = new Clause[1];
			clauses[0] = newClause;
			recentNumberOfResolutionSteps++;
			return true;
		}

		Clause[] newClauses = new Clause[clauses.length + 1];

		for( int i = 0 ; i < clauses.length ; ++i ) {
			if ( clauses[i].isSameClause(newClause) ) return false;
			else newClauses[i] = clauses[i];
		}
		newClauses[ clauses.length ] = newClause;
		clauses = newClauses;
		recentNumberOfResolutionSteps++;
		return true;
	}



	/**
	 * 
	 * This function does the resolution in the System
	 */

	private void resolve(){
		int previousClauseCount = clauses.length;
		for ( int i = 0 ; i < (previousClauseCount - 1) ; ++i){
			for ( int j = 1 ; j < previousClauseCount ; ++j ){
				Clause temp = clauses[i].resolvant(clauses[j]);
				if ( temp != null ){
					if ( addClause(temp) ){
						resolve();
						return;
					}
				}
			}


		}
	}

	public void wumpusHasBeenKilled(){
		for( int i = 0 ; i < 4 ; ++i )
			for( int j = 0 ; j < 4 ; ++j )
				removeClause(new Clause(new Preposition('W', true, i, j))); 
		for( int i = 0 ; i < 4 ; ++i )
			for( int j = 0 ; j < 4 ; ++j )
				removeClause(new Clause(new Preposition('S', true, i, j))); 
		for( int i = 0 ; i < 4 ; ++i )
			for( int j = 0 ; j < 4 ; ++j )
				addClause(new Clause(new Preposition('W', false, i, j))); 
		for( int i = 0 ; i < 4 ; ++i )
			for( int j = 0 ; j < 4 ; ++j )
				addClause(new Clause(new Preposition('S', false, i, j)));
		wumpusAlive = false;
		resolve();
	}

	
	/**
	 * 
	 * @param c
	 * 
	 * This function is a helping function
	 */
	private void removeClause(Clause c){
		boolean anythingRemoved = false;
		for( int i = 0 ; i < clauses.length ; ++i )
			if ( clauses[i].isSameClause(c) ){
				clauses[i] = null;
				anythingRemoved = true;
			}
		if ( anythingRemoved ){
			Clause[] temp = new Clause[ clauses.length - 1 ];
			int k = 0;
			for ( int i = 0 ; i < clauses.length ; ++i )
				if ( clauses[i] != null ) temp[k++] = clauses[i];
		}

	}
	
	/**
	 * 
	 * @return
	 * 
	 * This function is a helping function for statistics
	 */
	public int numberOfFacts(){
		int count = 0;
		for( int i = 0 ; i < clauses.length ; ++i )
			if( clauses[i].isFact() ) ++count;
		return count;
	}
	
	/**
	 * 
	 * @return
	 * 
	 * This function is a helping function for statistics
	 */
	public int numberOfClauses(){
		return clauses.length;
	}
	
	
}
