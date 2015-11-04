package com.MarioKart;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import com.MarioKart.MKUtilities.*;
import com.MarioKart.Constants.MKConstants;
import com.MarioKart.Game.net.packets.MKPacket_01_Login;


public class MenuMultiplayer extends Menu{

	private MKLabelNode multiLb;
	private TextField textField;
	
	public MenuMultiplayer(int _id) {
		super(MKConstants.MULTIPLAYER_CHARACTER_SELECT_MENU);
	}
	
	public void viewDidLoad()
	{
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException 
	{
		super.render(gc, sbg, g);
		multiLb.render(gc, g);	
		textField.render(gc, g);
	}
	
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException 
	{
		super.init(gc, sbg);
		multiLb = new MKLabelNode("Multiplayer");
		multiLb.setPostion(new MKPoint(50, 50));
		multiLb.setFont(new Font("Verdana", Font.BOLD, 50));
		//gc font x y w h
		textField = new TextField(gc, new TrueTypeFont (new Font("Verdana", Font.BOLD, 30), false), 50, 10, 500, 50);
		textField.setText("username...");
		textField.setCursorPos("username...".length());
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
		
		// Reference to the KartsGame
		KartsGame kg = KartsGame.KartsGameSharedInstance();	
		try {
			kg.init(StateBasedGameController.getSharedInstance().getContainer(), StateBasedGameController.getSharedInstance());
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get the username for multiplayer
		MKMultiplayerManager manager = MKMultiplayerManager.getSharedInstance();

		MKConstants.setUserName(this.textField.getText());
		
		// Create the local or original player
		MKPlayerMP newPlayer = new MKPlayerMP(null, -1, MKConstants.getUsername(), node.getTag());
		newPlayer.setOriginalPlayer(true);
		newPlayer.setImage("kart" + node.getTag() + ".png");
		kg.setPlayer(newPlayer);
		
		// Construct a login packet.
		MKPacket_01_Login loginPacket = new MKPacket_01_Login(newPlayer.getUsername(), node.getTag());
		loginPacket.writeData(manager.getClient());		
		
		if (manager.getServer() !=null)
		{
			manager.getServer().addConnection(newPlayer.convertToTemp(), loginPacket);
		}
							
		kg.getRacePlacer().setRacerImage(node.getImage(),"k" + node.getTag() + ".png");
		kg.getPlayer().setSound("s" + node.getTag() + ".wav");
		kg.getPlayer().setIcon(new MKSpriteNode("k" + node.getTag() + ".png"));
		
		StateBasedGameController.getSharedInstance().enterState(MKConstants.GAME);
		
		// load the game. multiplayer true.
		kg.viewDidLoad(true);
	}
	
	public int getID() 
	{
		return MKConstants.MULTIPLAYER_CHARACTER_SELECT_MENU;
	}
}
