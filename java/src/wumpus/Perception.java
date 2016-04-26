package wumpus;

/**
*
* Perception.java
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



public class Perception {
	
	private boolean stench = false;
	private boolean breeze = false;
	private boolean glitter = false;
	private boolean bump = false;
	private boolean scream = false;
	/**
	 * @param stench
	 * @param breeze
	 * @param glitter
	 * @param bump
	 * @param scream
	 */
	public Perception(boolean stench, boolean breeze, boolean glitter,
			boolean bump, boolean scream) {
		super();
		this.stench = stench;
		this.breeze = breeze;
		this.glitter = glitter;
		this.bump = bump;
		this.scream = scream;
	}
	/**
	 * @return the stench
	 */
	public boolean isStench() {
		return stench;
	}
	/**
	 * @param stench the stench to set
	 */
	public void setStench(boolean stench) {
		this.stench = stench;
	}
	/**
	 * @return the breeze
	 */
	public boolean isBreeze() {
		return breeze;
	}
	/**
	 * @param breeze the breeze to set
	 */
	public void setBreeze(boolean breeze) {
		this.breeze = breeze;
	}
	/**
	 * @return the glitter
	 */
	public boolean isGlitter() {
		return glitter;
	}
	/**
	 * @param glitter the glitter to set
	 */
	public void setGlitter(boolean glitter) {
		this.glitter = glitter;
	}
	/**
	 * @return the bump
	 */
	public boolean isBump() {
		return bump;
	}
	/**
	 * @param bump the bump to set
	 */
	public void setBump(boolean bump) {
		this.bump = bump;
	}
	/**
	 * @return the scream
	 */
	public boolean isScream() {
		return scream;
	}
	/**
	 * @param scream the scream to set
	 */
	public void setScream(boolean scream) {
		this.scream = scream;
	}
	
	
	
	

}
