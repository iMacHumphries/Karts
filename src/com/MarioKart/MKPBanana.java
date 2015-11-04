package com.MarioKart;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;


/**
 * MKPBanana.java - Mario Kart Power up Banana
 * 
 * @author Ben
 * @version 23-APR-2015
 */
public class MKPBanana extends MKItem{

	public MKPBanana() {
		super();
		this.setImage("banana.png");
		this.setName("banana");
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
		KartsGame.KartsGameSharedInstance().getItemManager().addItem(racer.getCurrentItem());
	
	}
	
	
	@Override
	public void applyTo(MKRacer racer)
	{
		racer.getVelocity().setX(-0.5f);
	}
	
	@Override
	public void update(GameContainer gc, int delta, MKMap map) throws SlickException
	{
		super.update(gc, delta, map);
	}
}
