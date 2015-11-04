package com.MarioKart;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.MarioKart.MKUtilities.*;
public class MKPGreenShell extends MKItem implements Timer{

	private MKTimer timer;
	
	public MKPGreenShell() {
		super();
		this.setImage("greenShell.png");
		this.setName("Green Shell");
		timer = new MKTimer(1, this, "");
		this.setRenderable(true);
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
		this.setPostion(racer.getPosition());
		KartsGame.KartsGameSharedInstance().getItemManager().addItem(this);
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
		final float MAX_SPEED = 0.8f;
		if (this.getVelocity().getX() <= MAX_SPEED)
			this.getVelocity().setX(this.getVelocity().getX() + 0.15f * delta);
		super.update(gc, delta, map);
		timer.update(delta);
	}

	@Override
	public void timerDidFinish(String _selector) {
		this.remove();
	}
}
