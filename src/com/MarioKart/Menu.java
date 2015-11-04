package com.MarioKart;
import java.io.IOException;
import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.MarioKart.MKUtilities.*;
import com.MarioKart.Constants.MKConstants;

public class Menu extends BasicGameState implements MouseListener{

	protected Shape mouseShape;
	protected StateBasedGame sbg2;
	protected LinkedList<MKSpriteNode> buttons;

	protected MKRacer displayRacer;
	protected MKSpriteNode background;
	protected MKSpriteNode playButton;
	
	public Menu(int state)
	{

	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException 
	{
		StateBasedGameController.getSharedInstance().addState(KartsGame.KartsGameSharedInstance());
		displayRacer = new MKRacer("kart0.png");
		displayRacer.setScale(0.8f);
		displayRacer.setPostion(new MKPoint(MKConstants.GAME_WIDTH/2 - displayRacer.getWidth()/2, MKConstants.GAME_HEIGHT/2 - displayRacer.getHeight() - 100));
		
		background = new MKSpriteNode("checkered.png");
		background.setSize(MKConstants.GAME_RECT);
		
		playButton = new MKSpriteNode("playbutton.png");
		playButton.setPostion(new MKPoint(MKConstants.GAME_WIDTH - playButton.getWidth() - 50, 20));
		playButton.addPhysicsBody(new MKPhysicsBody(new Rectangle(playButton.getX(),playButton.getY(),
				playButton.getWidth(), playButton.getHeight()),playButton));
		mouseShape = new Ellipse(0, 0, 20,20);
		buttons = new LinkedList<MKSpriteNode>();
		float x =0;
		float y =200;
		for (int i = 1; i < 9; i++)
		{
			if (i==5)
			{
				x=0;
				y=400;
			}
			x += 150;
			
			String image = "k" + (i-1) + ".png";
			MKSpriteNode node = new MKSpriteNode(x,y, image);
			
			node.setName("kart"+ (i-1) +".png");
			node.addPhysicsBody(new MKPhysicsBody(new Rectangle(node.getX(),node.getY(),
					node.getWidth(), node.getHeight()),node));
			node.setTag(i-1);
			buttons.add(node);
		}

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException 
	{
		playButton.render(gc, g);
		
		for(MKSpriteNode node : buttons)
			node.render(gc, g);
		
		displayRacer.render(gc, g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException 
	{
		if(sbg2 == null)
			sbg2 = sbg;
	}

	public int getID() 
	{
		return MKConstants.CHARACTER_SELECT_MENU;
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		mouseShape.setCenterX(newx);
		mouseShape.setCenterY(newy);
		for(MKSpriteNode node : buttons)
		{
			for (MKPhysicsBody body : node.getPhysicsBodies())
			{
				Shape rigidBody = body.getBody();
				if (rigidBody.contains(mouseShape) || rigidBody.intersects(mouseShape))
					node.setScale(1.025f);
				else 
					node.setScale(1.0f);
			}
			
		}
	}
	
	public void setDisplayTo(MKNode _node)
	{
		displayRacer.setImage(_node.getName());
		displayRacer.setTag(_node.getTag());
		
		try {
			Audio wavEffect = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("Sounds/s"+_node.getTag()+".wav"));
			wavEffect.playAsSoundEffect(1, 1, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mouseClicked(int na, int x, int y, int clickCount)
	{
		for(MKSpriteNode node : buttons)
		{
			for (MKPhysicsBody body : node.getPhysicsBodies())
			{
				Shape rigidBody = body.getBody();
				if (rigidBody.contains(mouseShape) || rigidBody.intersects(mouseShape))
					this.setDisplayTo(node);
			}
			
		}
		
		for (MKPhysicsBody body : playButton.getPhysicsBodies())
		{
			Shape rigidBody = body.getBody();
			if (rigidBody.contains(mouseShape) || rigidBody.intersects(mouseShape))
				this.moveToGameWithPlayer(displayRacer);
		}
	}
	
	private void moveToGameWithPlayer(MKSpriteNode node){
		KartsGame kg = KartsGame.KartsGameSharedInstance();
		
		// Create the local or original player
		MKPlayerMP newPlayer = new MKPlayerMP(null, -1, MKConstants.getUsername(), node.getTag());
		newPlayer.setOriginalPlayer(true);
		newPlayer.setImage("kart" + node.getTag() + ".png");
		newPlayer.setIcon(new MKSpriteNode("k"+node.getTag() +".png"));
		kg.setPlayer(newPlayer);
		
		StateBasedGameController.getSharedInstance().addState(KartsGame.KartsGameSharedInstance());
		kg.getRacePlacer().setRacerImage(node.getImage(),"k" + node.getTag() + ".png");
		//kg.getPlayer().setSound("s" + node.getTag() + ".wav");
		StateBasedGameController.getSharedInstance().enterState(MKConstants.GAME);
		kg.viewDidLoad(false);
	}

}
