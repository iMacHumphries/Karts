package com.MarioKart;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.MarioKart.MKUtilities.*;

public class MKPBlueShell extends MKItem implements Timer{

	private MKTimer timer;
	private MKRacer target;
	
	public MKPBlueShell() {
		super();
		this.setImage("blueShell.png");
		this.setName("blue Shell");
		timer = new MKTimer(1, this, "");
		this.setRenderable(true);
		target = null;
	}
	
	/**
	 * Called when an item is to be used. 
	 * Ex. banana needs to be dropped.
	 *  
	 * Sets the racer who dropped the item,
	 * sets the position of the item, 
	 * then spawns the item.
	 *  
	 * @param racer MKRacerObject
	 */
	@Override
	public void use(MKRacer racer){
		super.use(racer);
		KartsGame kg = KartsGame.KartsGameSharedInstance();
		
		this.setPostion(racer.getPosition());
		kg.getItemManager().addItem(this);
		timer = new MKTimer(3, this, "");
		timer.fire();
	}
	
	
	@Override
	public void applyTo(MKRacer racer)
	{
		racer.getVelocity().setX(-1.0f);
	}
	
	@Override
	public void update(GameContainer gc, int delta, MKMap map) throws SlickException
	{
		target = KartsGame.KartsGameSharedInstance().getRacePlacer().getFirstPlace();
		if (target == null) return;
		
		final float MAX_SPEED = 0.8f;
		
		if (this.getX() > target.getX())
		{
			//target is behind us
			if (this.getVelocity().getX() >= -MAX_SPEED)
				this.getVelocity().setX(this.getVelocity().getX() - 0.15f);
		}
		else {
			//target is in front of us.
			if (this.getVelocity().getX() <= MAX_SPEED)
				this.getVelocity().setX(this.getVelocity().getX() + 0.15f);
		}
		
		if (this.getY() > target.getY())
		{
			//target is above us
			if (this.getVelocity().getY() <= -MAX_SPEED)
				this.getVelocity().setY(this.getVelocity().getY() - 0.15f);
		}
		else {
			//target is under us.
			if (this.getVelocity().getY() <= MAX_SPEED)
				this.getVelocity().setY(this.getVelocity().getY() + 0.15f);
		}
		
	
		super.update(gc, delta, map);
		timer.update(delta);
	}

	@Override
	public void timerDidFinish(String _selector) {
		this.remove();
	}
}
