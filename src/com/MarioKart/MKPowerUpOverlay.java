package com.MarioKart;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.MarioKart.MKUtilities.*;
import com.MarioKart.Constants.MKConstants;

public class MKPowerUpOverlay extends MKSpriteNode{

	private MKSpriteNode imageOverlayNode;
	
	public MKPowerUpOverlay() {
		super();
		this.setImage("powerUpDisplay.png");
		this.setY(MKConstants.GAME_HEIGHT - this.getHeight());
		imageOverlayNode = new MKSpriteNode();
		this.addChild(imageOverlayNode);
	}
	
	public void setPowerUp(Image _image)
	{
		this.imageOverlayNode.setImage(_image);
		if (_image != null)
			this.setScale(1.08f);
	}
	
	public void setPowerUpWithNode(MKSpriteNode _node)
	{
		if (_node == null)
		{
			this.setPowerUp(null);
		}
		else {
			this.setPowerUp(_node.getImage());
		}
		
	}
	
	/**
	 * Renders the node.
	 */
	@SuppressWarnings("unused")
	public void render(GameContainer gc, Graphics g){
		super.render(gc, g);
		// Render self
		if (!this.isInteractionEnabled()) return;
		
		if (this.getImage() != null)
		{
			Color color = this.getColorFilter();
			if (color == null && this.getParent() != null)
				color = this.getParent().getColorFilter();

			if (this.getParent() != null)
					this.getImage().draw(this.getParent().getCenterPoint().getX() + this.getX() - this.getWidth()/2,
							this.getParent().getCenterPoint().getY() + this.getY() - this.getHeight()/2,
							this.getWidth() * this.getParent().getScale(),
							this.getHeight() * this.getParent().getScale(),
							color);
			else 
				this.getImage().draw(this.getX(), this.getY(), this.getWidth() * this.getScale(), this.getHeight() *this.getScale(), this.getColorFilter());
		}
		if (MKConstants.DEBUG_PHYSICS && this.getPhysicsBodies() != null )
		{
			g.setColor(Color.green);  
			for (MKPhysicsBody body : this.getPhysicsBodies())
				g.fill(body.getBody());
		}		
		this.setScale(1.0f);
	}
}
