package com.MarioKart;
import com.MarioKart.MKUtilities.MKSize;
import com.MarioKart.MKUtilities.MKSpriteNode;
import com.MarioKart.Constants.MKConstants;


public class MKBackground extends MKSpriteNode{

	public MKBackground() {
		super();
		this.setImage("background.png");
		this.setSize(new MKSize(MKConstants.GAME_WIDTH, MKConstants.GAME_HEIGHT));
	}
}
