package com.MarioKart.MKUtilities;

/**
 * MKPoint.java - Represents a point in space with x and y coordinates.
 * 
 * @author Benjamin C. Humphries & Christopher Anglin
 * @version 08-APR-2015
 */
public class MKPoint {

	private float x;
	private float y;
	
	/**
	 * Default Constructor
	 * 
	 * @param _x float x position
	 * @param _y float y position
	 */
	public MKPoint(float _x, float _y) {
		this.x = _x;
		this.y = _y;
	}
	
	/**
	 * Empty constructor
	 * 
	 */
	public MKPoint() {
		this(0,0);
	}
	
	/**
	 * Get the x position
	 * 
	 * @return float x position
	 */
	public float getX()
	{
		return this.x;
	}
	
	/**
	 * Get the y position
	 * 
	 * @return float y position
	 */
	public float getY()
	{
		return this.y;
	}
	
	/**
	 * Returns a MKPoint containing x and y position
	 * 
	 * @return MKPoint object
	 */
	public MKPoint getPosition()
	{
		return this;
	}
	
	/**
	 * Set the x position.
	 * 
	 * @param _x float x position
	 */
	public void setX(float _x)
	{
		this.x = _x;
	}
	
	/**
	 * Set the y position.
	 * 
	 * @param _y float y position.
	 */
	public void setY(float _y)
	{
		this.y = _y;
	}
	
	/**
	 * Get the distance between two MKPoints
	 * 
	 * @param _to MKPoint
	 * @return float distance
	 */
	public float getDistance(MKPoint _to)
	{
		return (float) Math.sqrt(Math.pow(this.getX() - _to.getX(), 2) + Math.pow(this.getY() - _to.getY(), 2));
	}
	
	/**
	 * ToString
	 */
	public String toString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}
}
