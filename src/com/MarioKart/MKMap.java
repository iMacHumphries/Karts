package com.MarioKart;
import org.newdawn.slick.geom.Path;

import com.MarioKart.MKUtilities.*;
import com.MarioKart.Constants.*;

import java.util.Random;
/**
 * Creates a map shape using path
 * 
 * @author Ben
 * @version 10-APR-2015
 */
public class MKMap extends Path{

	private static final long serialVersionUID = 1L;
	private float startingGroundPoint;
	private final float MKMILE_LENGTH = 500.0f;
	private final float UP = -250.0f;
	private final float DOWN = 250.0f;
	private float currentX;
	private float currentY;
	private MKPhysicsBody body;
	
	/**
	 * Constructor
	 * 
	 * @param _miles
	 */
	public MKMap()
	{
		this(MKConstants.GAME_HEIGHT); // start bottom left corner
	}
	
	/**
	 * Constructor
	 * 
	 * @param _miles
	 */
	public MKMap(float _groundYPoint) {
		super(0, _groundYPoint);
		this.startingGroundPoint = _groundYPoint;
		currentX = 0;
		currentY = this.startingGroundPoint;
		body = new MKPhysicsBody();
		//body.setMass(100.0f);
		body.setInverseMass(0);
		body.setRestitution(0.0f);
		body.setStaticFriction(0.0f);
		MKNode node = new MKNode(); // just so that the collision will work
		body.setParent(node);
	}
	
	/**
	 * Curves the map up by the default amount
	 */
	public void curveUpConcaveDown()
	{
		float newX = nextX();
		float newY = getCurrentY() + UP;
		
		float cx1 = newX - 300;
		float cy1 = newY + 20;
		
		float cx2 = newX - 10;
		float cy2 = newY;
		
		this.curveTo(newX, newY, cx1, cy1, cx2, cy2);
	}
	
	/**
	 * Curves the map up by the default amount
	 */
	public void curveDownConcaveUp()
	{
		float newX = nextX();
		float newY = getCurrentY() + DOWN;
		
		float cx1 = newX - 300;
		float cy1 = newY - 230;
		
		float cx2 = newX - 10;
		float cy2 = newY - 70;
		
		this.curveTo(newX, newY, cx1, cy1, cx2, cy2);
	}
	
	
	public void hill()
	{
		this.curveUpConcaveDown();
		this.curveDownConcaveUp();
	}
	
	
	
	/**
	 * Curves the map down by the default amount
	 */
	public void curveDown()
	{
		float newX = nextX();
		float newY = getCurrentY() + DOWN;
		
		float cx1 = newX -300;
		float cy1 = newY;
		
		float cx2 = newX -10;
		float cy2 = newY;
		
		this.curveTo(newX, newY, cx1, cy1, cx2, cy2);
		//float[] array ={newX, newY, cx1, cy1, cx2, cy2}; 
		//MKDebug.log(array);
	}
	
	/**
	 * Curves the map to the ground.
	 */
	public void curveToGround()
	{
		float newX = nextX();
		float newY = this.startingGroundPoint;
		
		float cx1 = newX -300;
		float cy1 = newY;
		
		float cx2 = newX -10;
		float cy2 = newY;
		
		this.curveTo(newX, newY, cx1, cy1, cx2, cy2);
		//float[] array ={newX, newY, cx1, cy1, cx2, cy2}; 
		//MKDebug.log(array);
	}
	
	/**
	 * Draws line to the ground.
	 */
	public void lineToGround()
	{
		float newX = nextX();
		float newY = this.startingGroundPoint;
		
		this.lineTo(newX, newY);
	}
	
	/**
	 * Draws line up to the default amount
	 */
	public void lineUp()
	{
		float newX = nextX();
		float newY = getCurrentY() + UP;
		
		this.lineTo(newX, newY);
	}
	
	/**
	 * Draws line down to the default amount
	 */
	public void lineDown()
	{
		float newX = nextX();
		float newY = getCurrentY() + DOWN;
		this.lineTo(newX, newY);
	}
	
	/**
	 * Draws straight line
	 */
	public void straightLine()
	{
		float newX = nextX();
		float newY = getCurrentY();
		this.lineTo(newX, newY);
	}
	
	/**
	 * Gets the next x position on the map.
	 * 
	 *  @return float nextAvailible x position
	 */
	public float nextX()
	{
		return this.currentX + this.MKMILE_LENGTH;
	}

	/**
	 * Overrides curveTo in order to keep track of current x and y
	 * 
	 * @param _x float new x position
	 * @param _y float new y position
	 * @param _cx1 float control point #1 x position
	 * @param _cy1 float control point #1 y position
	 * @param _cx2 float control point #2 x position
	 * @param _cy2 float control point #2 y position
	 */
	public void curveTo(float _x, float _y, float _cx1, float _cy1, float _cx2, float _cy2)
	{
		super.curveTo(_x, _y, _cx1, _cy1, _cx2, _cy2);
		this.currentX = _x;
		this.currentY = _y;
	}
	
	/**
	 * Overrides lineTo in order to keep track of current x and y
	 * 
	 * @param x new x position
	 * @param y new y position
	 */
	public void lineTo(float x, float y)
	{
		super.lineTo(x, y);
		this.currentX = x;
		this.currentY = y;
	}
	
	/**
	 * Performs a random drawing action. 
	 * Either curveUp(), curveDown(), lineUp(), or lineDown()
	 */
	public void random()
	{
		Random rand = new Random();
		int i = rand.nextInt(5);
		switch(i)
		{
		case 0:
			this.curveUpConcaveDown();
			break;
		case 1:
			this.curveDown();
			break;
		case 2:
			this.lineUp();
			break;
		case 3:
			this.lineDown();
			break;
		case 4: 
			this.straightLine();
		default:
			MKDebug.log(i + "index out of bounds int map creator");
			break;
		}
	}
	
	/**
	 * Gets the current y position
	 * 
	 * @return float current y
	 */
	public float getCurrentY()
	{
		return this.currentY;
	}
	
	/**
	 * Gets the maps PhysicsBody.
	 * 
	 * @return MKPhysicsBody Object
	 */
	public MKPhysicsBody getPhysicsBody()
	{
		return this.body;
	}
	
	/**
	 * Gets the current x position
	 * 
	 * @return float current x
	 */
	public float getCurrentX()
	{
		return this.currentX;
	}
	
	/**
	 * Gets the bottom right most corner of the map.
	 * 
	 * @return MKPoint Object
	 */
	public MKPoint getBottomRightCorner()
	{
		return new MKPoint(this.getCurrentX(),this.getCurrentY());
	}
}
