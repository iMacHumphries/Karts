package com.MarioKart.MKUtilities;

/**
 * MKSize.java - Object representing a size that is made up
 * of a width and a height.
 * 
 * @author Benjamin C. Humphries
 * @version 09-APR-2015
 */
public class MKSize {
	private float width;
	private float height;
	
	/**
	 * Empty constructor
	 */
	public MKSize() {
		this(0f,0f);
	}
	
	/**
	 * Default constructor.
	 * 
	 * @param _width float new width
	 * @param _height float new height
	 */
	public MKSize(float _width, float _height)
	{
		this.width = _width;
		this.height = _height;
	}
	
	/**
	 * Gets the width of this size object.
	 * 
	 * @return float width of object
	 */
	public float getWidth()
	{
		return this.width;
	}
	
	/**
	 * Gets the height of this size object.
	 * 
	 * @return float height of object.
	 */
	public float getHeight()
	{
		return this.height;
	}
	
	/**
	 * Sets the size for a given width and height.
	 * 
	 * @param _width float new width
	 * @param _height float new height.
	 */
	public void setSize(float _width, float _height)
	{
		this.width = _width;
		this.height = _height;
	}
	
	/**
	 * Sets the size for a give MKSize
	 * 
	 * @param _size new MKSize object.
	 */
	public void setSize(MKSize _size)
	{
		this.width = _size.getWidth();
		this.height = _size.getHeight();
	}
	
	/**
	 * Get the size of the object
	 * 
	 * @return MKSize object containing width and height
	 */
	public MKSize getSize()
	{
		return this;
	}
		
	/**
	 * toString()
	 */
	public String toString()
	{
		return this.getWidth() + " " + this.getHeight();
	}

}
