package com.MarioKart.MKUtilities;

import org.newdawn.slick.geom.Vector2f;

/**
 * Represents a basic 2d vector for physics
 * 
 * @author Benjamin C. Humprhies & Christopher Anglin
 * @version 10-APR-2015
 */
public class MKVector2 extends Vector2f{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Empty Constructor
	 */
	public MKVector2()
	{
		this(0.0f,0.0f);
	}
	
	/**
	 * Default constructor
	 * @param _dx float delta x
	 * @param _dy float delta y
	 */
	public MKVector2(float _dx, float _dy)
	{
		super(_dx, _dy);
	}
	
	/**
	 * Default constructor
	 * @param _dx float delta x
	 * @param _dy float delta y
	 */
	public MKVector2(Vector2f _vector2f)
	{
		super(_vector2f);
	}

	/**
	 * @param dx the dx to set
	 */
	public void setX(float _dx)
	{
		this.x = _dx;
	}

	/**
	 * @param dy the dy to set
	 */
	public void setY(float _dy)
	{
		this.y = _dy;
	}

	/**
	 * Returns a MKVector2 containing a dx and dy
	 * 
	 * @return MKVector2
	 */
	public MKVector2 getVector()
	{
		return this;
	}
	
	/**
	 * Subtract two vectors
	 * 
	 * @param _vector
	 * @return
	 */
	public MKVector2 sub(MKVector2 _vector)
	{
		return new MKVector2(super.sub(_vector));
	}
	
	/**
	 * Normal of a vector
	 * 
	 * @return MKVector2 object
	 */
	public MKVector2 normal()
	{
		Vector2f v = super.getNormal();
	
		return new MKVector2(v);
	}
	
	/**
	 * Magnitude of a vector.
	 * 
	 * @return float representing the magnitude of a vector
	 */
	public float magnitude()
	{
		return (float)Math.sqrt( Math.pow(getX(), 2) + Math.pow(getY(), 2));
	}
	
	/**
	 * Dot product of two vectors
	 * 
	 * @param _vector
	 * @return
	 */
	public float dot(MKVector2 _vector)
	{
		return super.dot(new Vector2f(_vector.getX(), _vector.getY()));
	}
	
	/**
	 * Cross 2 vectors
	 * 
	 * @return float result of cross product
	 */
	public float Cross( MKVector2 _b )
	{
		return this.getX() * _b.getY() - this.getY() * _b.getX();
	}
	 
	/**
	 * Cross with float
	 * 
	 * @return MKVector2 object
	 */
	public MKVector2 Cross(float _scaler)
	{
	  return new MKVector2( _scaler * this.getY(), -_scaler * this.getX() );
	}
	 
	
	/**
	 * toString() <dx,dy>
	 */
	public String toString()
	{
		return "<" + this.getX() + ", " + this.getY() + ">";
	}
}
