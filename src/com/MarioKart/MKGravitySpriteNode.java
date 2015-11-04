package com.MarioKart;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import com.MarioKart.MKUtilities.*;

/**
 * MKGravitySpriteNode.java - basic sprite node with applied 
 * gravity.
 * 
 * @author Ben
 * @version 23-APR-2015
 */
public class MKGravitySpriteNode extends MKSpriteNode{

	private boolean gravityEnabled;
	
	public MKGravitySpriteNode() {
		super();
	}

	public void update(GameContainer gc, int delta, MKMap map) throws SlickException
	{	
		for (MKPhysicsBody body : this.getPhysicsBodies())
		{
			Shape powerBody = null;
			if (body.getName() != "main")
				powerBody = body.getBody();
			if (powerBody != null)
			{
				// Check for collision
				if (map.intersects(powerBody) || map.contains(powerBody)) // If Power up is on top of map no gravity
				{
					// There was collision so turn off gravity
					this.setGravityEnabled(false);
					// Check to see if the player is moving
					if (this.getVelocity().getX() > 0)
						this.setY(this.getY() - 0.25f * delta); // Move the powerup up with the curved map.      
				}
				else
				{
					//There was no collision, so apply gravity
					this.setGravityEnabled(true);	
				}	
			}
		}
		if (this.isGravityEnabled())
		{
			//gravity 0.008f
			this.getVelocity().setY(this.getVelocity().getY() + 0.005f);
		}
		else
		{
			//no gravity
			this.getVelocity().setY(0);
		}
		
		// update position
		MKPoint newPos = new MKPoint(this.getX() + this.getVelocity().getX() * delta,
									 this.getY() + this.getVelocity().getY() * delta);				
		this.setPostion(newPos);
				
		for (MKPhysicsBody body : this.getPhysicsBodies())
			body.update();
	}

	/**
	 * @return the gravityEnabled
	 */
	public boolean isGravityEnabled() {
		return gravityEnabled;
	}

	/**
	 * @param gravityEnabled the gravityEnabled to set
	 */
	public void setGravityEnabled(boolean gravityEnabled) {
		this.gravityEnabled = gravityEnabled;
	}

}
