package com.MarioKart;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.MarioKart.MKUtilities.*;

import java.util.Random;
/**
 * MKRacerAI.java - Represents an artificial intelligence (AI) Non Player Character (NPC)
 * 
 * @author Benjamin C. Humphries
 * @version 22-APR-2015
 */
public class MKRacerAI extends MKRacer implements Timer{

	private final float ACCELERATION = (float) (Math.random()*.003+.007f);
	private boolean isWaitingToUseItem;
	private MKTimer timer;
	private Random random;
	
	/**
	 * Constructor 
	 * @param _imageName String 
	 */
	public MKRacerAI(String _imageName) {
		super(_imageName);
		isWaitingToUseItem = false;
		random = new Random();
		timer = new MKTimer(random.nextInt(2)+1,this,"waitingToUse");
		float speed = (float) (Math.random()/3+.5);
		this.setMaxSpeed(speed);
		this.setDefaultSpeed(speed);
	}
	
	/**
	 * Regular MKRacer update, the apply automatic acceleration. 
	 */
	public void update(GameContainer gc, int delta, MKMap map) throws SlickException
	{
		super.update(gc, delta, map);
		timer.update(delta);
		this.accelerate();
		if (this.getCurrentItem() != null)
			this.usePowerUpItem();
	}
	
	/**
	 * Override to allow for randomly generated speed and 
	 * acceleration.
	 */
	@Override
	protected void accelerate()
	{
		if (this.getVelocity().getX() < this.getMaxSpeed())
		{
			this.getVelocity().setX((this.getVelocity().getX() + ACCELERATION));
		}
		else{
			this.getVelocity().setX(this.getMaxSpeed());
		}
		
	}
	
	private void usePowerUpItem()
	{
		if (!isWaitingToUseItem)
		{
			MKDebug.log("waitng to use");
			timer.fire();
			isWaitingToUseItem = true;
		}
		
	}

	@Override
	public void timerDidFinish(String _selector) {
		MKDebug.log("called");
		this.useItem();
		isWaitingToUseItem = false;
		timer = new MKTimer(random.nextInt(2) + 1,this,"waitingToUse");
	}

}
