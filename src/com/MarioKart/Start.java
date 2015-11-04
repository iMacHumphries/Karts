package com.MarioKart;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.MarioKart.Constants.MKConstants;
import com.MarioKart.MKUtilities.MKPhysicsBody;
import com.MarioKart.MKUtilities.MKPoint;
import com.MarioKart.MKUtilities.MKSpriteNode;

public class Start extends BasicGameState {

	private Shape mouseShape;
	private MKSpriteNode background;
	private MKSpriteNode button;
	private MKSpriteNode multiButton;
	private StateBasedGame sbg2;

	public Start(int state)
	{

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		background = new MKSpriteNode("startback.png");
		background.setSize(MKConstants.GAME_RECT);
		button = new MKSpriteNode(370,400,"startbutton.jpg");
		button.setHeight(40);
		button.setWidth(150);
		button.setCenterPoint(MKConstants.CENTER);
		button.addPhysicsBody(new MKPhysicsBody(button.getRect(),button));
		
		multiButton = new MKSpriteNode("multiButton.png"); 
		multiButton.setHeight(40);
		multiButton.setWidth(150);
		multiButton.setPostion(new MKPoint(button.getX(),
				button.getY() + multiButton.getHeight() + 5));	
		multiButton.addPhysicsBody(new MKPhysicsBody(multiButton.getRect(), multiButton));
		
	
		mouseShape = new Ellipse(0, 0, 20,20);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.render(gc, g);
		button.render(gc, g);
		multiButton.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		
		if(sbg2 == null)
			sbg2 = sbg;

	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		mouseShape.setCenterX(newx);
		mouseShape.setCenterY(newy);
	}

	public void mouseClicked(int na, int x, int y, int clickCount)
	{
		// Single player button
		for (MKPhysicsBody body : button.getPhysicsBodies())
		{
			Shape rigidBody = body.getBody();
			if (rigidBody.contains(mouseShape) || rigidBody.intersects(mouseShape))
			{
				this.singlePlayerButton();
			}
		}
		
		for (MKPhysicsBody body : multiButton.getPhysicsBodies())
		{
			Shape rigidBody = body.getBody();
			if (rigidBody.contains(mouseShape) || rigidBody.intersects(mouseShape))
			{
				this.multiPlayerButton();
			}
		}
	}

	private void singlePlayerButton()
	{
		StateBasedGameController game = StateBasedGameController.getSharedInstance();
		game.enterState(MKConstants.CHARACTER_SELECT_MENU);
		try {
			Audio wavEffect = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("Sounds/selectPlayer.wav"));
			wavEffect.playAsSoundEffect(1, 1, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void multiPlayerButton()
	{
		// Ask if user wants to run a server
		if (JOptionPane.showConfirmDialog(null, "Host server?", "Mario Kart Server?", 0) == JOptionPane.YES_OPTION)
		{
			// Start the server.
			MKMultiplayerManager.getSharedInstance().hostServer();
		}
		
		StateBasedGameController game = StateBasedGameController.getSharedInstance();
		game.enterState(MKConstants.MULTIPLAYER_CHARACTER_SELECT_MENU);
		
		try {
			Audio wavEffect = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("Sounds/selectPlayer.wav"));
			wavEffect.playAsSoundEffect(1, 1, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public int getID() {

		return MKConstants.START_SCREEN;
	}

}
