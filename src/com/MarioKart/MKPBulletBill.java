package com.MarioKart;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.MarioKart.MKUtilities.MKSpriteNode;
import com.MarioKart.MKUtilities.MKTimer;
import com.MarioKart.MKUtilities.Timer;


public class MKPBulletBill extends MKItem implements Timer{

	private MKTimer timer;
	private float DURATION = 3.0f;
	MKSpriteNode flame;
	Image previousImage;
	Image previousIcon;
	
	public MKPBulletBill() {
		super();
		this.setImage("bulletBillItem.png");
		timer = new MKTimer(DURATION,this,"");
		this.setName("Bullet Bill");
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
		racer.setMaxSpeed(2.0f);
		previousImage = racer.getImage();
		previousIcon = racer.getIcon().getImage();
		racer.setImage("bulletBill.png");
		racer.getIcon().setImage("kb.png");
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
		this.getItemHolder().setImage(previousImage);
		this.getItemHolder().getIcon().setImage(previousIcon);
		this.getItemHolder().setMaxSpeed(this.getItemHolder().getDefaultSpeed());
		this.remove();
	}
}