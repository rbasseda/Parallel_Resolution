package wumpus;

/**
*
* Clause.java
*
* Copyright (C) The Research Foundation of SUNY, 2015
* All rights reserved.
*
* This software may be modified and distributed under the terms
* of the BSD license.  See the LICENSE file for details.
*
* Created on: October 20th, 2011 
* @author rbasseda
*  
* 
*/


public class Clause {

	private Proposition[] prepositions = null;
	
	
	
	
	/**
	 * @param prepositions1
	 * @param prepositions2
	 * 
	 * Constructor according to the list of prepositions
	 */
	public Clause(Proposition[] prepList1, Proposition[] prepList2) {
		super();
		if ( ( prepList1 == null ) && ( prepList2 != null ) ){
			prepositions = new Proposition[ prepList2.length ];
			for ( int i = 0 ; i < prepList2.length ; ++i ){
				prepositions[i] = prepList2[i];
			}
			return;
		}
		if ( ( prepList1 != null ) && ( prepList2 == null ) ){
			prepositions = new Proposition[ prepList1.length ];
			for ( int i = 0 ; i < prepList1.length ; ++i ){
				prepositions[i] = prepList1[i];
			}
			return;
		}
		if ( ( prepList1 == null ) && ( prepList2 == null ) ){
			System.out.println("Invalid prepositions are give!!!!!");
			return;
		}
		prepositions = new Proposition[ prepList1.length + prepList2.length ];
		for ( int i = 0 ; i < prepList1.length ; ++i ){
			prepositions[i] = prepList1[i];
		}		
		
		for ( int i = 0 ; i < prepList2.length ; ++i ){
			prepositions[ i + prepList1.length ] = prepList2[i];
		}
		removeDuplicates();
	}

	/**
	 * @param First Preposition in the cluase
	 * 
	 */
	public Clause(Proposition p) {
		super();
		// TODO Auto-generated constructor stub
		
		prepositions = new Proposition[1];
		prepositions[0] = p;
		
	}

	
	
	
	/**
	 * @param prepositions
	 */
	public Clause(Proposition[] prepositions) {
		super();
		this.prepositions = prepositions;
	}

	/**
	 * 
	 */
	public Clause() {
		super();
		// TODO Auto-generated constructor stub
	}



	/**
	 * 
	 * @return
	 * 
	 * This function is a helping function for consitency
	 */
	public boolean isEmpty(){
		if ( prepositions.length == 0 ) return true;
		else return false;
	}
	
	/**
	 * 
	 * @param newPreposition
	 * 
	 * This function is a helping function for adding prepositions
	 */
	public void addPreposition(Proposition newPreposition){
		if( prepositions == null ) {
			prepositions = new Proposition[1];
			prepositions[0] = newPreposition;
			return;
		}
		
		Proposition[] newPrepositions = new Proposition[prepositions.length + 1];
		
		for( int i = 0 ; i < prepositions.length ; ++i ) newPrepositions[i] = prepositions[i];
		newPrepositions[ prepositions.length ] = newPreposition;
		prepositions = newPrepositions;
		
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 * 
	 * This function is a helping function which looks for a particular preposition
	 */
	public boolean containPreposition(Proposition p){
		if ( prepositions == null ) return false;
		for( int i = 0 ; i < prepositions.length ; ++i ){
			if ( prepositions[i].isSame(p) )return true;
		}
		return false;
	}

	/**
	 * 
	 * @param p
	 * @return
	 * 
	 * This function is a helping function which looks for the negation of a particular preposition
	 */
	public boolean containNegationOfPreposition(Proposition p){
		if ( prepositions == null ) return false;
		for( int i = 0 ; i < prepositions.length ; ++i ){
			if ( prepositions[i].isNegation(p) )return true;
		}
		return false;
	}

	/**
	 * 
	 * @param c
	 * @return
	 * 
	 * This function is a helping function checking similarity of two clauses
	 */
	public boolean isSameClause(Clause c){
		for ( int i = 0 ; i < prepositions.length ; ++i ){
			if ( !c.containPreposition(prepositions[i]) )return false;
		}
		return true;
	}
	

	
	
	/**
	 * @return the prepositions
	 */
	public Proposition[] getPrepositions() {
		return prepositions;
	}



	/**
	 * 
	 * @param c
	 * @return
	 * 
	 * This function is a helping function which returns the resolvant of two clauses
	 */
	public Clause resolvant(Clause c){
		for ( int i = 0 ; i < prepositions.length ; ++i ){
			if ( c.containNegationOfPreposition(prepositions[i]) ){
				if ( ( removePreposition(prepositions[i]).getPrepositions()==null ) && ( c.removePreposition(prepositions[i].toggleNegation()).getPrepositions()==null ) ) {
					writeClause();
					c.writeClause();
				}
				return new Clause(removePreposition(prepositions[i]).getPrepositions(), c.removePreposition(prepositions[i].toggleNegation()).getPrepositions());
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param p
	 * @return
	 * 
	 * This function is a helping function which deletes a particular preposition
	 * 
	 */
	public Clause removePreposition(Proposition p){
		Clause output = new Clause();
		for( int i = 0 ; i < prepositions.length ; ++i )
			if( !prepositions[i].isSame(p) )output.addPreposition(prepositions[i]);
		return output;
	}
	
	
	/**
	 * This function is a helping function which deletes duplicated prepositions
	 */
	public void removeDuplicates(){
		int k = 0;
		for ( int i = 0 ; i < ( prepositions.length - 1 ) ; ++i ){
			for ( int j = i + 1; j < prepositions.length ; ++j )
				if ( prepositions[j] == prepositions[i] ){
					prepositions[j] = null;
					++k;
				}
		}
		Proposition[] newPrepositions = new Proposition[ prepositions.length - k ];
		int l = 0;
		for( int i = 0 ; i < prepositions.length ; ++i)
			if ( prepositions[i] != null ) newPrepositions[l++] = prepositions[i];
		prepositions = newPrepositions;
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 * 
	 * This function is a helping function which checks a particular preposition to be the fact in KB
	 */
	public boolean isFact(Proposition p){
		if ( ( prepositions.length == 1 ) && ( prepositions[0].isSame(p) ) ) return true;
		return false;
	}
	
	/**
	 * This function is a helping function for debugging
	 */
	public void writeClause(){
		System.out.println();
		for( int i = 0 ; i < prepositions.length ; ++i ){
			prepositions[i].writePreposition();
			if ( i != 3 )System.out.print(",");
		}
		
	}
	
	
	/**
	 * 
	 * @return
	 * This function is a helping function checking to be a fact
	 */
	public boolean isFact(){
		if( prepositions == null )return false;
		if( prepositions.length == 1 )return true;
		else return false;
	}
	
}
