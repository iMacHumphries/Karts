package com.MarioKart;
import java.awt.Font;
import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.MarioKart.MKUtilities.MKLabelNode;
import com.MarioKart.MKUtilities.MKPoint;
import com.MarioKart.MKUtilities.MKSpriteNode;
import com.MarioKart.MKUtilities.MKTimer;
import com.MarioKart.MKUtilities.Timer;
import com.MarioKart.Constants.MKConstants;

/**
 * KartsGame.java - Slick basic game.
 * 
 * @author Benjamin C. Humphries & Christopher Anglin
 * @version 08-APR-2015
 */
public class KartsGame extends BasicGameState implements Timer{

	private MKMultiplayerManager multiplayerManager;
	private boolean multiplayerEnabled;
	
	protected MKPowerUpManager powerUpManager;  // Holds a list of all MKPowerUps
	protected MKTimeManager timeManager;	 	  // Holds a list of every MKTimer.
	protected MKRacePlacer racePlacer;     	  // Holds a List of every racer (including player) determines places.
	protected MKItemManager itemManager;
	protected MKPlayer player;              	  // The Player
	protected MKCamera camera;              	  // The camera that centers on the player 
	protected MKMap map;                    	  // The map Path (Slick Shape object)
	protected MKMap rightMapCopy;           	  // Used for looks
	protected MKMap leftMapCopy;            	  // Used for looks
	protected MKLabelNode lapCountLabel;    	  // Label showing lap count of the player / total laps
	protected MKLabelNode posLabel;         	  // Label showing the players position.
	protected MKLabelNode xLabel;
	protected MKPowerUpOverlay powerUpOverlay; 
	protected MKBackground background;
	protected boolean gameOver;
	protected Audio backgroundMusic;
	private List<MKPlayerTemp> playersToAdd;
	
		
	public static KartsGame sharedInstance;
	
	/**
	 * Returns a sharedInstance of this class.
	 * 
	 * @param _title String 
	 * @return KartsGame Object
	 */
	public static KartsGame KartsGameSharedInstance()
	{
		if (sharedInstance == null)
			sharedInstance = new KartsGame(1);
		return sharedInstance;
	}

	/**
	 * Constructor
	 * 
	 * @param _title String
	 */
	public KartsGame(int state) {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.render(gc, g);
		camera.centerOnNode(player); 
		g.translate(camera.getX(), camera.getY()); 
		g.setBackground(new Color(40, 150, 255));
	
		// Maps
		Image image = new Image("Images/test.png");
		ShapeRenderer.textureFit(map, image);
		ShapeRenderer.textureFit(rightMapCopy, image);
		ShapeRenderer.textureFit(leftMapCopy, image);
		
		this.racePlacer.renderAll(gc, g);
		this.powerUpManager.render(gc, g);
		this.itemManager.render(gc, g);
		g.resetTransform();
		xLabel.render(gc,g);
		lapCountLabel.render(gc,g);
		posLabel.render(gc,g);
		powerUpOverlay.render(gc, g);
		this.racePlacer.renderIcons(gc, g);
		g.resetTransform();
	}
	

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.initPlayerList();
		powerUpManager = new MKPowerUpManager();
		racePlacer = new MKRacePlacer();
		timeManager = new MKTimeManager();
		itemManager = new MKItemManager();
		//player = new MKPlayerMP(null, -1, MKConstants.getUsername());
		camera = new MKCamera();
		powerUpOverlay = new MKPowerUpOverlay();
		background = new MKBackground();
		
		lapCountLabel = new MKLabelNode("Lap 0/" + MKConstants.LAPS);
		lapCountLabel.setPostion(new MKPoint(100,10));
		
		posLabel = new MKLabelNode();
		posLabel.setPostion(new MKPoint(MKConstants.GAME_WIDTH - 200, MKConstants.GAME_HEIGHT - 50));
		posLabel.setFont(new Font("Verdana", Font.BOLD, 50));
		
		xLabel = new MKLabelNode("X: Power Up");
		xLabel.setPostion(new MKPoint(MKConstants.GAME_WIDTH/5, MKConstants.GAME_HEIGHT - 50));
		posLabel.setFont(new Font("Verdana", Font.BOLD, 30));

			
		// Create map
		MKMapCreator mapcreator = new MKMapCreator();
		map = mapcreator.getMap();
		mapcreator = new MKMapCreator();
		rightMapCopy = mapcreator.getMap();
		rightMapCopy.setLocation(map.getBottomRightCorner().getX() - 70, 0);
		mapcreator = new MKMapCreator();
		leftMapCopy = mapcreator.getMap();
		leftMapCopy.setLocation(70-map.getBottomRightCorner().getX(), map.getY());
		
		gc.setTargetFrameRate(300);	
		
		//power ups
		powerUpManager.addPowerUp(new MKPowerUp(600,0));
		powerUpManager.addPowerUp(new MKPowerUp(1500,0));
		powerUpManager.addPowerUp(new MKPowerUp(400,0));
		powerUpManager.addPowerUp(new MKPowerUp(2000,0));
		powerUpManager.addPowerUp(new MKPowerUp(map.getWidth() - 100, 0));
				
	}
	
	private void initMuliplayer()
	{
		
		player.setY(40);
		racePlacer.addRacer(player);
		if (!multiplayerEnabled) return;
		
		
		multiplayerManager = MKMultiplayerManager.getSharedInstance();
		this.checkForNewPlayersToAdd();
	}
	
	public void checkForNewPlayersToAdd()
	{
		for (MKPlayerTemp p : playersToAdd)
		{
			MKPlayerMP play = new MKPlayerMP(p.getIp(), p.getPort(), p.getUsername(), p.getKartID());
			System.out.println(play + " joined the game...");
			play.setIcon(new MKSpriteNode("k"+p.getKartID() + ".png")); 
			play.setImage("kart" + p.getKartID() + ".png");
			System.out.println("kart ID is :" + p.getKartID());
			this.racePlacer.addRacer(play);
		}
		playersToAdd.removeAll(playersToAdd);
	}
	
	public void viewDidLoad(boolean multiplayer)
	{
		this.multiplayerEnabled = multiplayer;
		
		if (!multiplayerEnabled)
		{
			for (int i = 1; i < 8; i++)
			{
				MKRacerAI newRacerAI = new MKRacerAI("kart"+i + ".png");
				newRacerAI.setIcon(new MKSpriteNode("k" + i + ".png"));
				newRacerAI.setName("kart" + i);
				newRacerAI.setX((i  * 50) + 10);
				newRacerAI.setY(40);
				racePlacer.addRacer(newRacerAI);
			}
		}
		
		this.initMuliplayer();
		this.timeManager.addTimerAndFire(new MKTimer(3, this, "countDown"));
		try {
			Audio wavEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Sounds/countDown.wav"));
			wavEffect.playAsMusic(1, 1, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		timeManager.updaterTimers(delta);             				   	    // Updates all timers
		this.powerUpManager.update(gc, delta, map);  				   		// Updates all power ups
		this.racePlacer.updateAll(gc, delta, map);    				   		// Updates all racers
		this.itemManager.updateAll(gc, delta, map);
		this.racePlacer.checkAllPlaces();             				  	    // Checks for placement of racers (i.e. 1st, 2nd, 3rd...)
		posLabel.setText(MKConstants.PLACEMENTS[(int)player.getPlace()]);   // Updates Players placement.
		if (this.multiplayerEnabled)
			this.checkForNewPlayersToAdd();
	}

	
	protected void startGame()
	{
		this.racePlacer.startGame();
		try {
			backgroundMusic = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Sounds/themeSong.wav"));
			backgroundMusic.playAsMusic(1, 1, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endGame()
	{
		gameOver = true;
		backgroundMusic.stop();
		StateBasedGameController.getSharedInstance().enterState(MKConstants.END);
		this.playersToAdd.removeAll(playersToAdd);
	}
	
	/**
	 * Called when an MKTimer is invalidated. Sends a 
	 * String _selector indicating which timer was 
	 * invalidated.
	 * 
	 * @param _selector String
	 */
	public void timerEnded(String _selector)
	{
		powerUpManager.spawnPowerUpWithName(_selector);
	}

	/**
	 * Gets the current map.
	 * 
	 * @return MKMap object
	 */
	public MKMap getMap()
	{
		return this.map;
	}
	
	/**
	 * Gets the time manager.
	 * 
	 * @return MKTimeManager Object
	 */
	public MKTimeManager getTimeManager()
	{
		return this.timeManager;
	}

	/**
	 * Gets the powerup manager.
	 * 
	 * @return MKPowerUpManager Object.
	 */
	public MKPowerUpManager getPowerUpManager()
	{
		return this.powerUpManager;
	}
	
	public MKRacePlacer getRacePlacer()
	{
		return this.racePlacer;
	}
	
	public MKItemManager getItemManager()
	{
		return this.itemManager;
	}
	
	/**
	 * Gets the player.
	 * 
	 * @return MKPlayer object.
	 */
	public MKPlayer getPlayer()
	{
		return this.player;
	}
	
	public void setPlayer(MKPlayer _p)
	{
		this.player = _p;
	}
	
	/**
	 * Gets the lap countLabel.
	 * 
	 * @return MKLabelNode object
	 */
	public MKLabelNode getLabCountLabel()
	{
		return this.lapCountLabel;
	}
	
	public MKPowerUpOverlay getOverlay()
	{
		return this.powerUpOverlay;
	}

	// #paragma MKTimer
	@Override
	public void timerDidFinish(String _selector) {
		powerUpManager.spawnPowerUpWithName(_selector);
		if (_selector.equals("countDown"))
		{
			this.startGame();
		}
	}
	
	@Override
	public int getID()
	{
		return 1;
	}

	/**
	 * @return the gameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * @param gameOver the gameOver to set
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public GameContainer getGC() {
		return StateBasedGameController.getSharedInstance().getContainer();
	}

	public void addNewPlayerFromLogin(InetAddress _ip, int _port,
			String _username, int kartID) {
		this.initPlayerList();
		playersToAdd.add(new MKPlayerTemp(_username, _ip, _port, kartID));
	}	
	
	private void initPlayerList()
	{
		if (playersToAdd == null)
			playersToAdd = new LinkedList<MKPlayerTemp>();
	}

	public void removePlayerMP(String username) {
		for (MKRacer racer : racePlacer.getRacers())
		{
			if (racer instanceof MKPlayerMP)
			{
				MKPlayerMP play = (MKPlayerMP) racer;
				if (play.getUsername().equals(username))
					racePlacer.getToBeRemoved().add(play);
			}
		}
	}
	
	public void movePlayerTo(String _username, int x, int y)
	{
		for (MKRacer racer : racePlacer.getRacers())
		{
			if (racer instanceof MKPlayerMP)
			{
				MKPlayerMP play = (MKPlayerMP) racer;
				if (play.getUsername().equals(_username))
				{
					int index = this.racePlacer.getRacers().indexOf(play);
					this.racePlacer.getRacers().get(index).setPostion(new MKPoint(x,y));
				}
			}
		}
	}

}