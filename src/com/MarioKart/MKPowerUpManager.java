package com.MarioKart;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.MarioKart.MKUtilities.*;

/**
 * MKPowerUpManager.java - Manages powerups in game.
 * 
 * @author Benjamin C. Humphries
 * @version 23-APR-2015
 */
public class MKPowerUpManager {
	private List<MKPowerUp> powerUps;
	
	public MKPowerUpManager() {
		powerUps = new LinkedList<MKPowerUp>();
	}

	public void addPowerUp(MKPowerUp _p)
	{
		this.powerUps.add(_p);
	}
	
	public void removePowerUp(MKPowerUp _p)
	{
		this.powerUps.remove(_p);
	}
	
	public void update(GameContainer gc, int delta, MKMap map) throws SlickException
	{
		for (MKPowerUp pu : powerUps)
			pu.update(gc, delta, map);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		for (MKPowerUp pu : powerUps)
			pu.render(gc, g);
	}
	
	public void updateCollision(MKRacer racer)
	{
		if (racer.getCurrentItem() != null) return;
		
		for (MKPowerUp pu : powerUps)
			if (racer.didCollideWith(pu))
			{
				KartsGame kg = KartsGame.KartsGameSharedInstance();
				kg.getTimeManager().addTimerAndFire(new MKTimer(3,kg, pu.getName(), false));	
				racer.setCurrentItem(pu.getRandomItem(racer));
				MKDebug.log("removing " + pu.getName());
				pu.remove();
				if (racer instanceof MKPlayer)
				{
					this.playerCollectedPowerUp();
					KartsGame.KartsGameSharedInstance().getOverlay().setPowerUpWithNode(racer.getCurrentItem());
				}
			}
	}
	
	private void playerCollectedPowerUp()
	{
		try {
			Audio wavEffect = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("Sounds/powerup.wav"));
			wavEffect.playAsSoundEffect(1, 1, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void spawnPowerUpWithName(String _selector)
	{
		for (MKPowerUp pu : powerUps)
		{
			if (_selector.equals(pu.getName()))
			{
				MKDebug.log("spawning " + pu.getName());
				pu.respawn();
			}
		}
	}
	
}
