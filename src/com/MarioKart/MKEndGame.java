package com.MarioKart;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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

public class MKEndGame extends BasicGameState {

	private MKSpriteNode background;
	private MKSpriteNode stand;
	private MKSpriteNode first;
	private MKSpriteNode second;
	private MKSpriteNode third;
	private MKSpriteNode replay;
	private Image[] places;
	private Shape mouseShape;
	private Audio audioPlayer;
	private boolean hasPlayedAudio;

	public MKEndGame(int state) 
	{

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		try {
			audioPlayer = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("Sounds/hit.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mouseShape = new Ellipse(0, 0, 20,20);
		background = new MKSpriteNode("menuBack.jpg");
		background.setSize(MKConstants.GAME_RECT);
		stand = new MKSpriteNode("winners.png");
		stand.setWidth(MKConstants.GAME_WIDTH-100);
		stand.setHeight(300);
		stand.setPostion(new MKPoint(MKConstants.GAME_WIDTH/2-(stand.getWidth()/2),MKConstants.GAME_HEIGHT-stand.getHeight()));
		first = new MKSpriteNode("kart0.png");
		second = new MKSpriteNode("kart0.png");
		third = new MKSpriteNode("kart0.png");
		stand.addChild(first);
		stand.addChild(second);
		stand.addChild(third);
		first.setPostion(new MKPoint(18,-180));
		second.setPostion(new MKPoint(-235,-85));
		third.setPostion(new MKPoint(250,-50));
		replay = new MKSpriteNode("replay.png");
		replay.setHeight(200);
		replay.setWidth(200);
		replay.setPostion(new MKPoint(MKConstants.GAME_WIDTH/3-(replay.getWidth()),MKConstants.GAME_HEIGHT/40));
		replay.addPhysicsBody(new MKPhysicsBody(new Rectangle(replay.getX(),replay.getY(),
				replay.getWidth(), replay.getHeight()),replay));

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.render(gc, g);
		stand.render(gc, g);
		first.render(gc, g);
		second.render(gc, g);
		third.render(gc, g);
		replay.render(gc, g);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		places = KartsGame.KartsGameSharedInstance().getRacePlacer().getPlaces();
		try{
			first.setImage(places[0]);
			second.setImage(places[1]);
			third.setImage(places[2]);
			places[0].setRotation(0);
			places[1].setRotation(0);
			places[2].setRotation(0);
			
			for (int i = 0; i < places.length; i++)
			{
				if (places[i].getResourceReference().equals( 
						KartsGame.KartsGameSharedInstance().getPlayer().getImage().getResourceReference()))
				{
					playSound("winSong.wav");
				}
				else {
					playSound("lose.wav");
				}
			}
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
		
		
		
	}
	
	public void playSound(String sound)
	{
		if (audioPlayer.isPlaying() || hasPlayedAudio) return;
		
		try {
			audioPlayer = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("Sounds/"+sound));
			audioPlayer.playAsSoundEffect(1, 1, false);
			hasPlayedAudio = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		mouseShape.setCenterX(newx);
		mouseShape.setCenterY(newy);
	}

	public void mouseClicked(int na, int x, int y, int clickCount)
	{
		for (MKPhysicsBody body : replay.getPhysicsBodies())
		{
			Shape rigidBody = body.getBody();
			if (rigidBody.contains(mouseShape) || rigidBody.intersects(mouseShape))
			{
				
				try {
					KartsGame.KartsGameSharedInstance().init(
							KartsGame.KartsGameSharedInstance().getGC(), 
							StateBasedGameController.getSharedInstance());
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.playSound("selectPlayer.wav");
				StateBasedGameController.getSharedInstance().enterState(MKConstants.START_SCREEN);
				
			}
		}
	}


	@Override
	public int getID() {
		return MKConstants.END;
	}

}