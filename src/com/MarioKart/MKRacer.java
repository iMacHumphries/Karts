package com.MarioKart;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.MarioKart.MKUtilities.*;
import com.MarioKart.Constants.*;
/**
 * MKRacer.java - represents a racer in the game.
 * 
 * @author Benjamin C. Humphries
 * @version 22-APR-2015
 */
public class MKRacer extends MKGravitySpriteNode{

	private MKPhysicsBody leftWheel;
	private MKPhysicsBody rightWheel;
	private MKPhysicsBody mainBody;
	private boolean canMove;
	private int lapCount = 0;
	private int place = 0;
	private MKItem currentItem;
	private MKSpriteNode icon;
	
	private float maxSpeed;
	private float defaultSpeed;
	
	/**
	 * Constructor
	 * @param _imageName String
	 */
	public MKRacer(String _imageName) {
		super();
		MKDebug.log("Creating racer...");
		this.setImage(_imageName);
		this.setX(MKConstants.GAME_WIDTH/2);
		this.setY(MKConstants.GAME_HEIGHT/2);
		this.setScale(0.7f);
		this.addWheels();
		mainBody = new MKPhysicsBody(new Rectangle(getX(), getY(), getWidth() * getScale(), getHeight() * getScale()), this);
		mainBody.setName("main");
		this.addPhysicsBody(mainBody);
		canMove = false;
	}

	/**
	 * Helper method to add wheels to kart
	 */
	private void addWheels()
	{
		int wheelScale = 10;
		leftWheel = new MKPhysicsBody(new Ellipse(this.getX(), this.getY(), this.getWidth()/wheelScale * this.getScale(), this.getHeight()/wheelScale * this.getScale()), this);
		leftWheel.setAnchorPoint(new MKPoint(0,this.getHeight()/2));
		leftWheel.setName("left");
		this.addPhysicsBody(leftWheel);
		
//		leftWheelImage = new MKSpriteNode();
//		leftWheelImage.setImage("wheel.png");
//		leftWheelImage.setScale(0.2f);
//		leftWheelImage.setPostion(new MKPoint(leftWheelImage.getWidth()/2 * leftWheelImage.getScale() - this.getWidth()/2.5f, this.getHeight()/5.0f));
//		this.addChild(leftWheelImage);
		
		rightWheel = new MKPhysicsBody(new Ellipse(this.getX(), this.getY(), this.getWidth()/wheelScale * this.getScale(), this.getHeight()/wheelScale * this.getScale()), this);
		rightWheel.setAnchorPoint(new MKPoint(this.getWidth()/2,this.getHeight()/2));
		rightWheel.setName("right");
		this.addPhysicsBody(rightWheel);
		
//		rightWheelImage = new MKSpriteNode();
//		rightWheelImage.setImage("wheel.png");
//		rightWheelImage.setScale(0.2f);
//		rightWheelImage.setPostion(new MKPoint(this.getWidth()/(3.3f / this.getScale()), this.getHeight()/5.0f));
//		this.addChild(rightWheelImage);
	}
	
	/**
	 * Called when game is updated
	 * 
	 * @param gc GameContainer
	 * @param delta time
	 * @throws SlickException 
	 */
	public void update(GameContainer gc, int delta, MKMap map) throws SlickException {	
	
		Shape left = leftWheel.getBody();
		Shape right = rightWheel.getBody();
	
		if (!left.intersects(map) && right.intersects(map))
		{
			while ( !left.intersects(map) && right.intersects(map))
			{
				float dTheta =(float) this.getOrientation() -0.01f;
				//MKDebug.log("right touching " + dTheta);
	    		this.setOrientation(dTheta);
	    		this.render(gc, gc.getGraphics());
				leftWheel.setOffsetPoint(new MKPoint(0,(float)-Math.tan(dTheta) * this.getWidth()/5)); //perfect
				//leftWheelImage.setPostion(new MKPoint(leftWheelImage.getX(),(float)-Math.tan(dTheta) * this.getWidth()/5));
				leftWheel.update();	
			}
		}
		
		float distance =  (this.getY() + this.getHeight()/2) - leftWheel.getBody().getY();
		float theta = 60 * (float)Math.atan(distance/this.getWidth());
		this.rotateTo(theta);
		this.render(gc, gc.getGraphics());
		
		if (!canMove)
			this.getVelocity().setX(0);
		super.update(gc, delta, map);
	}
	
	/**
	 * Called when brakes are applied,
	 *  gradually brings kart to stop.
	 *  
	 */
	protected void brake()
	{
		if (this.getVelocity().getX() > 0)
			this.getVelocity().setX(this.getVelocity().getX() - 0.002f);
	}
	
	/**
	 * Called when right button is pressed.
	 */
	protected void accelerate()
	{	
		final float ACCELERATION = 0.009f;
		if (this.getVelocity().getX() < maxSpeed)
		{
			this.getVelocity().setX((this.getVelocity().getX() + ACCELERATION));
		}
		else{
			this.getVelocity().setX(maxSpeed);
		}
		
	}
	
	/**
	 * Called when no button is pressed at the time of update.
	 * Slows the player down to a gradual stop.
	 */
	protected void negativeAcceleration()
	{
		if (this.getVelocity().getX() > 0)
			this.getVelocity().setX(this.getVelocity().getX() - 0.002f);
		else
			this.getVelocity().setX(0.0f);
	}
	
	public boolean didCollideWith(MKPowerUp _powerUp)
	{
		if (!_powerUp.isInteractionEnabled() || _powerUp == null) return false;
		
		boolean result = false;
		for (MKPhysicsBody body : _powerUp.getPhysicsBodies())
		{
			Shape pRigid = body.getBody();
			if (pRigid.intersects(this.getPhysicsBodyWithName("main")) || pRigid.contains(this.getPhysicsBodyWithName("main")))
				result = true;
		}
		return result;
	}
		
	public boolean didCollideWith(MKItem _item)
	{	
		boolean result = false;
		if (_item.getItemHolder() == this) return false;
		for (MKPhysicsBody body : _item.getPhysicsBodies())
		{
			Shape pRigid = body.getBody();
			if (pRigid.intersects(this.getPhysicsBodyWithName("main")) || pRigid.contains(this.getPhysicsBodyWithName("main")))
				result = true;
		}
		return result;
	}
	
	/**
	 * Gets the lap count.
	 * 
	 * @return int lap count.
	 */
	public int getLapCount()
	{
		return this.lapCount;
	}
	
	/**
	 * Adds 1 lap to the lap count.
	 */
	public void addLap()
	{
		this.lapCount += 1;
	}
	
	/**
	 * Sets if the player can move or not.
	 * is finished or not.
	 * 
	 * @param _b boolean
	 */
	public void setCanMove(boolean _b)
	{
		this.canMove = _b;	
	}
	
	/**
	 * Gets the current place of the MKRacer
	 * in the current race.
	 * 
	 * @return int place
	 */
	public int getPlace()
	{
		return this.place;
	}
	
	/**
	 * Sets the current place of the MKRacer
	 * in the race.
	 * 
	 * @param _p int place
	 */
	public void setPlace(int _p)
	{
		place = _p;
	}
		
	/**
	 * Gets the total distance traveled by the MKRacer.
	 * That is the number of laps * the map width + current x
	 *
	 * @return float representing the distance traveled.
	 */
	public float getDistanceTraveled()
	{
		return this.getLapCount() * KartsGame.KartsGameSharedInstance().getMap().getWidth() + this.getX();
	}

	/**
	 * @return the currentItem
	 */
	public MKItem getCurrentItem() {
		return currentItem;
	}

	/**
	 * @param currentItem the currentItem to set
	 */
	public void setCurrentItem(MKItem currentItem) {
		this.currentItem = currentItem;
	}
	
	public void useItem()
	{
		if (currentItem == null) return;
		currentItem.use(this);
		currentItem = null;
	}

	/**
	 * @return the icon
	 */
	public MKSpriteNode getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(MKSpriteNode icon) {
		this.icon = icon;
		this.icon.setScale(0.6f);
		if (this instanceof MKPlayer)
			this.getIcon().setX(this.getIcon().getX() + 20);
	}

	/**
	 * @return the maxSpeed
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * @return the defaultSpeed
	 */
	public float getDefaultSpeed() {
		return defaultSpeed;
	}

	/**
	 * @param defaultSpeed the defaultSpeed to set
	 */
	protected void setDefaultSpeed(float defaultSpeed) {
		this.defaultSpeed = defaultSpeed;
	}
	
	public boolean isFinished()
	{
		return this.canMove;
	}
	
}
