package com.MarioKart;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import com.MarioKart.Constants.MKConstants;
import com.MarioKart.Game.net.packets.MKPacket_00_Disconnect;

public class StateBasedGameController extends StateBasedGame{

	public static StateBasedGameController sharedController;
	public static StateBasedGameController getSharedInstance()
	{
		if (sharedController == null)
			sharedController = new StateBasedGameController();
		
		return sharedController;
	}
	
	public StateBasedGameController() {
		super(MKConstants.GAME_NAME);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new Start(MKConstants.START_SCREEN));
		this.enterState(MKConstants.START_SCREEN);
		this.addState(new Menu(MKConstants.CHARACTER_SELECT_MENU));
		this.addState(new MenuMultiplayer(MKConstants.MULTIPLAYER_CHARACTER_SELECT_MENU));
		this.addState(new MKEndGame(MKConstants.END));
		this.addState(KartsGame.KartsGameSharedInstance());	
	}
	
	public void addState(GameState state)
	{
		int ID = state.getID();
		if (this.getState(ID) == null)
			super.addState(state);
	}
	
	@Override
	public boolean closeRequested()
	{	
		KartsGame kg = KartsGame.KartsGameSharedInstance();
		MKPlayerTemp player = ((MKPlayerMP) kg.getPlayer()).convertToTemp();
		MKPacket_00_Disconnect discPacket = new MKPacket_00_Disconnect(player.getUsername());
		discPacket.writeData(MKMultiplayerManager.getSharedInstance().getClient());
		super.closeRequested();
		return true;
	}

}
