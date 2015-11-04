package com.MarioKart;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.MarioKart.MKUtilities.MKPhysicsBody;

/**
 * MKItem.java-
 * 
 * @author Ben
 * @version 23-APR-2015
 */
public abstract class MKItem extends MKGravitySpriteNode {

	private MKRacer itemHolder;
	private boolean renderable;
	
	public MKItem(){
		super();
		this.addPhysicsBody(new MKPhysicsBody(new Circle(getX(),getY(), 10),this));
	}
	
	public void use(MKRacer racer){
		this.setItemHolder(racer);
	}
	
	public void update(GameContainer gc, int delta, MKMap map) throws SlickException
	{	
		super.update(gc, delta, map);
	}

	public void remove()
	{
		KartsGame.KartsGameSharedInstance().getItemManager().removeItem(this);
	}
	
	public void applyTo(MKRacer racer)
	{
		if (racer instanceof MKPlayer)
		{
			try {
				Audio wavEffect = AudioLoader.getAudio("WAV",
						ResourceLoader.getResourceAsStream("Sounds/hit.wav"));
				wavEffect.playAsSoundEffect(1, 1, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return the itemHolder
	 */
	public MKRacer getItemHolder() {
		return itemHolder;
	}

	/**
	 * @param itemHolder the itemHolder to set
	 */
	public void setItemHolder(MKRacer itemHolder) {
		this.itemHolder = itemHolder;
	}

	/**
	 * @return the renderable
	 */
	public boolean isRenderable() {
		return renderable;
	}

	/**
	 * @param renderable the renderable to set
	 */
	public void setRenderable(boolean renderable) {
		this.renderable = renderable;
	}
}
