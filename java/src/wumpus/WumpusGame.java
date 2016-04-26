package wumpus;
import java.lang.System;

/**
*
* WumpusGame.java
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
* This is the main class of our project.
* 
*/


public class WumpusGame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int outputMode = Integer.valueOf(args[0]).intValue();
		long startTime = System.currentTimeMillis();
		
		if( args.length < 2 ){
			Cave myCave = new Cave(outputMode);
			Agent myAgent = new Agent(myCave, 0, 0, 0, outputMode);
		} else {
			Cave myCave = new Cave(outputMode,args[1]);
			Agent myAgent = new Agent(myCave, 0, 0, 0, outputMode);
		}

		
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.print(elapsedTime);
	    System.out.println(" miliseconds");
	}

}
