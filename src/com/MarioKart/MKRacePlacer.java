package com.MarioKart;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.MarioKart.MKUtilities.*;
import com.MarioKart.Constants.*;
/**
 * MKRacePlacer.java - Handles the place of each racer in the race.
 * 
 * @author Benjamin C. Humphries
 * @version 22-APR-2015
 */
public class MKRacePlacer{
	private List<MKRacer> racers;
	private List<MKRacer> toBeRemoved;
	
	/**
	 * Constructor.
	 */
	public MKRacePlacer() {
		racers = new LinkedList<MKRacer>();
		toBeRemoved = new LinkedList<MKRacer>();
	}
	
	/**
	 * Adds a racer to the list of racers.
	 * Also updates that racers position.
	 * 
	 * @param _racer MKRacer
	 */
	public void addRacer(MKRacer _racer)
	{
		this.racers.add(_racer);
		_racer.setPlace(this.racers.indexOf(_racer) + 1);
	}
	
	public boolean removeRacer(MKRacer _racer)
	{
		boolean result = false;
		for (MKRacer racer : this.racers)
		{
			if (racer.equals(_racer))
			{
				toBeRemoved.add(racer);
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * Checks each racer in the list, and sets
	 * it to be in the correct place based on
	 * the distance that the racer has traveled.
	 * 
	 */
	public void checkAllPlaces()
	{
		for (int i = 0; i < this.racers.size(); i++)
		{
			MKRacer previous = null;
			if (i > 0) previous = this.racers.get(i -1);
			MKRacer racer = this.racers.get(i);
			if (previous != null && racer.getDistanceTraveled() > previous.getDistanceTraveled())
			{
				this.racers.set(i-1, racer);
				this.racers.set(i, previous);
				racer.setPlace(i-1   +1);
				previous.setPlace(i  +1);
			}
			racer.getIcon().setY(racer.getPlace() * racer.getIcon().getHeight() * racer.getIcon().getScale() + 5);
		}
	}
	
	/**
	 * Renders all racers.
	 * 
	 * @param gc GameContainer
	 * @param g Graphics
	 * @throws SlickException
	 */
	public void renderAll(GameContainer gc, Graphics g) throws SlickException
	{
		for (MKRacer racer : this.racers)
		{
			KartsGame kg = KartsGame.KartsGameSharedInstance();
			MKPlayer p = kg.getPlayer();
			float playerX = p.getX();
			float xPos = racer.getX();
			if (xPos >= playerX - MKConstants.GAME_WIDTH/2 && xPos <= playerX + MKConstants.GAME_WIDTH/2)
				racer.render(gc,g);
		}
		
	}
	
	public void renderIcons(GameContainer gc, Graphics g) throws SlickException
	{
		for (MKRacer racer : this.racers)
		{
			racer.getIcon().render(gc, g);
		}
	}
	
	
	/**
	 * Updates all Racers.
	 * 
	 * @param gc GameContainer
	 * @param delta int
	 * @param map MKMap Object
	 * @throws SlickException
	 */	
	public void updateAll(GameContainer gc, int delta,MKMap map) throws SlickException 
	{
		for (MKRacer racer : this.racers)
		{
			racer.update(gc, delta, map);
			this.checkIfOffMap(racer, map);	
			KartsGame kg = KartsGame.KartsGameSharedInstance();
			kg.getPowerUpManager().updateCollision(racer);
			kg.getItemManager().updateCollision(racer);
		}
		for (MKRacer racer : toBeRemoved)
		{
			this.racers.remove(racer);
			toBeRemoved.remove(racer);
		}
	}
	
	/**
	 * Helper method.
	 * 
	 * *Checks to see if the racer is off the map.
	 * If so, sets the racer back at the start
	 * of the map.
	 * 
	 * *If the racer is the player, then it updates 
	 * the lapCountLabel
	 * 
	 * *If it is the racers last lap, then it stops
	 * the racer.
	 * 
	 * @param racer MKRacer object.
	 */
	private void checkIfOffMap(MKRacer racer, MKMap map)
	{
		
		if (racer.getX() >= map.getX() + map.getWidth() - racer.getWidth())
		{
			racer.setPostion(new MKPoint(0, racer.getPosition().getY() - 10));
			racer.addLap();
			if (racer instanceof MKPlayerMP)
				if (((MKPlayerMP) racer).getUsername() == MKConstants.getUsername())
					KartsGame.KartsGameSharedInstance().getLabCountLabel().setText("Lap "+ racer.getLapCount() +"/" + MKConstants.LAPS);
			if (racer.getLapCount() > MKConstants.LAPS)
			{
				racer.setCanMove(false);
				if (racer instanceof MKPlayer)
					KartsGame.KartsGameSharedInstance().endGame();
			}
		}
	}

	public void startGame()
	{
		for (MKRacer racer : racers)
		{
			racer.setCanMove(true);
		}
	}
	
	public void setRacerImage(Image _image, String _icon) {
		for (int i = 0; i < racers.size(); i++)
		{
			if (racers.get(i) instanceof MKPlayer)
			{
				racers.get(i).setImage(_image);
				racers.get(i).setIcon(new MKSpriteNode(_icon));
			}
			else if (racers.get(i).getImage().getResourceReference().equals(_image.getResourceReference()))
			{
				racers.get(i).setImage("kart0.png");
				racers.get(i).setIcon(new MKSpriteNode("k" + 0 + ".png"));
			}
		}
		
	}

	public MKRacer getFirstPlace() {
		MKRacer result = null;
		for (MKRacer racer : racers)
		{
			if (racer.getPlace() == 1)
			{
				result = racer;
			}
		} 
		return result;
	}

	public Image[] getPlaces() {
		Image[] result = new Image[3];
		
		for (MKRacer racer : racers)
		{
			if (racer.getPlace() == 1)
				result[0] = racer.getImage();
			else if (racer.getPlace() == 2)
				result[1] = racer.getImage();
			else if (racer.getPlace() == 3)
				result[2] = racer.getImage();
		}
		
		return result;
	}

	/**
	 * @return the racers
	 */
	public List<MKRacer> getRacers() {
		return racers;
	}

	/**
	 * @param racers the racers to set
	 */
	public void setRacers(List<MKRacer> racers) {
		this.racers = racers;
	}

	/**
	 * @return the toBeRemoved
	 */
	public List<MKRacer> getToBeRemoved() {
		return toBeRemoved;
	}

	/**
	 * @param toBeRemoved the toBeRemoved to set
	 */
	public void setToBeRemoved(List<MKRacer> toBeRemoved) {
		this.toBeRemoved = toBeRemoved;
	}
}
