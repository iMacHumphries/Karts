package com.MarioKart;
import com.MarioKart.MKUtilities.MKNode;
import com.MarioKart.Constants.*;

/**
 * Represents the camera following a node.
 * 
 * @author Benjamin C. Humphries
 * @version 10-APR-2015
 */
public class MKCamera extends MKNode{


	/**
	 * Constructor
	 */
	public MKCamera() {
		super();
	}
	
	/**
	 * Center the camera on given node.
	 * 
	 * @param _node MKNode to center on.
	 */
	public void centerOnNode(MKNode _node)
	{
		this.setX(-1 * (_node.getX() - MKConstants.GAME_WIDTH/2));
		if (_node.getY() < 297)
			this.setY(-1 * (_node.getY() - MKConstants.GAME_HEIGHT/2));
	}
}
