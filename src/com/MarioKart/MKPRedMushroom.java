package com.MarioKart;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.MarioKart.MKUtilities.*;


public class MKPRedMushroom extends MKItem implements Timer{

	private MKTimer timer;
	private float DURATION = 3.0f;
	MKSpriteNode flame;
	
	public MKPRedMushroom() {
		super();
		this.setImage("redMushroom.png");
		timer = new MKTimer(DURATION,this,"");
		this.setName("Red Mushroom");
		this.setRenderable(false);
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
		this.applyTo(racer);
	}
	
	
	@Override
	public void applyTo(MKRacer racer)
	{
		racer.setMaxSpeed(1.0f);
		flame = new MKSpriteNode("flame.png"); 
		flame.setScale(2.5f);
		flame.setX(-racer.getWidth()/2);
		racer.addChild(flame);
		timer = new MKTimer(DURATION,this,"");
		timer.fire();
		KartsGame.KartsGameSharedInstance().getItemManager().addItem(this);
	}
	
	@Override
	public void update(GameContainer gc, int delta, MKMap map) throws SlickException
	{
		timer.update(delta);
	}

	@Override
	public void timerDidFinish(String _selector)
	{
		this.getItemHolder().setMaxSpeed(this.getItemHolder().getDefaultSpeed());
		this.getItemHolder().removeChild(flame);
		this.remove();
	}
}
