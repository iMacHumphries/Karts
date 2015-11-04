package com.MarioKart;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.MarioKart.MKUtilities.*;
import com.MarioKart.Constants.MKConstants;

/**
 * MKPowerUp.java
 * 
 * @author Ben
 * @version 22-APR-2015
 */
public class MKPowerUp extends MKGravitySpriteNode{

	private static int nameNumber;
	private Random random;
	
	public MKPowerUp() {
		this(0,0);
	}
	
	public MKPowerUp(float x, float y) {
		this(new MKPoint(x, y));
	}
	
	public MKPowerUp(MKPoint _position)
	{
		super();
		this.setPostion(_position);
		this.setImage("powerUp.png");
		MKPhysicsBody body = new MKPhysicsBody(this.getRect(), this);
		body.setName("powerup");
		this.addPhysicsBody(body);
		this.setNewName();
		random = new Random();
	}
	
	public void setNewName()
	{
		this.setName("p" + nameNumber++);
	}
	
	public void update(GameContainer gc, int delta, MKMap map) throws SlickException
	{	
		super.update(gc, delta, map);
	}
	
	public void remove()
	{
		this.setInteractionEnabled(false);
	}
	
	public void respawn()
	{
		this.setY(0);
		this.setInteractionEnabled(true);
	}
	
	public MKItem getRandomItem(MKRacer _racer)
	{
		final int TOTAL_ITEMS = 5;
		
		MKItem result = null;
		int items = random.nextInt(TOTAL_ITEMS);
		
		switch(items){
			case 0: result = new MKPBanana();
				break;
			case 1: result = new MKPGreenShell();
				break;
			case 2:
				if (_racer.getPlace() == 2 || _racer.getPlace() == 3)
					result = new MKPBlueShell();
				else 
					result = new MKPBanana();
				break;
			case 3: result = new MKPRedMushroom();
				break;
			case 4:
				if (_racer.getPlace() == MKConstants.LAST_PLACE)
					result = new MKPBulletBill();
				else 
					result = new MKPBanana();
				break;
			default:
				result = new MKPBanana();
				break;
		}
		return result;
	}
}