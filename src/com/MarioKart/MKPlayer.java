package com.MarioKart;
import java.awt.Font;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.MarioKart.Constants.MKConstants;
import com.MarioKart.Game.net.packets.MKPacket_02_Move;
import com.MarioKart.MKUtilities.*;

/**
 * MKPlayer - Represents the player in the game. 
 * 
 * @author Benjamin C. Humphries
 * @version 7-APR-2015
 */
public class MKPlayer extends MKRacer{
	
	private Audio sound;
	private MKLabelNode label;
	private boolean originalPlayer;
	private int KartID;
	
	/**
	 * Constructor
	 */
	public MKPlayer()
	{
		super("kart0.png");
		this.setName("player");
		this.setMaxSpeed(0.7f);
		this.setDefaultSpeed(0.7f);
		label = new MKLabelNode("Player");
		label.setPostion(new MKPoint(22,-this.getHeight()/5f));
		label.setText("Player");
		label.setFont(new Font("Verdana", Font.BOLD, 10));
		this.addChild(label);
	}
	
	/**
	 * @return the label
	 */
	public MKLabelNode getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(MKLabelNode label) {
		this.label = label;
	}

	public void speak()
	{
		//sound.playAsSoundEffect(1, 1, false);
	}
	
	/**
	 * Update as a racer, then update input.
	 */
	public void update(GameContainer gc, int delta, MKMap map) throws SlickException
	{
		super.update(gc, delta, map);
		if (!this.originalPlayer) return;
		
		Input i=gc.getInput();
		
		if(i.isKeyDown(Input.KEY_LEFT))
			this.brake();

		else if(i.isKeyDown(Input.KEY_RIGHT))
			this.accelerate();
		else
		{
			//no button was pressed slow down
			this.negativeAcceleration();
		}
		
		if (i.isKeyPressed(Input.KEY_X))
		{
			useItem();
			KartsGame.KartsGameSharedInstance().getOverlay().setPowerUpWithNode(this.getCurrentItem());
		}
		if (i.isKeyDown(Input.KEY_SPACE))
			this.speak();
		

		MKPacket_02_Move packet = new MKPacket_02_Move(MKConstants.getUsername(), (int)getX(), (int)getY());
		packet.writeData(MKMultiplayerManager.getSharedInstance().getClient());
	}
	
	public boolean didCollideWith(MKPowerUp _powerUp)
	{
		boolean result = super.didCollideWith(_powerUp);	
		KartsGame.KartsGameSharedInstance().getOverlay().setPowerUpWithNode(this.getCurrentItem());
		return result;
	}
	
	/**
	 * @return the sound
	 */
	public Audio getSound() {
		return sound;
	}

	/**
	 * @param sound the sound to set
	 */
	public void setSound(Audio sound) {
		this.sound = sound;
	}
	
	/**
	 * @param string sound the sound to set
	 */
	public void setSound(String sound) {
		try {
			this.sound = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("Sounds/"+sound));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the originalPlayer
	 */
	public boolean isOriginalPlayer() {
		return originalPlayer;
	}

	/**
	 * @param originalPlayer the originalPlayer to set
	 */
	public void setOriginalPlayer(boolean originalPlayer) {
		this.originalPlayer = originalPlayer;
	}

	/**
	 * @return the kartID
	 */
	public int getKartID() {
		return KartID;
	}

	/**
	 * @param kartID the kartID to set
	 */
	public void setKartID(int kartID) {
		KartID = kartID;
	}
	
	
}

