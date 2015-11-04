package com.MarioKart.MKUtilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.MarioKart.Constants.MKConstants;

/**
 * MKSpriteNode.java - mario kart sprite node is the basic game object templete
 * based of the D2L tutorial. *MKSpriteNode is an MKNode with an image.
 * 
 * @author Benjamin C. Humphries & Christopher Anglin
 * @version 08-APR-2015
 */
public class MKSpriteNode extends MKNode{
	private Image objImage; 
	
	/**
	 * Constructor
	 */
	public MKSpriteNode()
	{
		this("");
	}
	
	/**
	 * Constructor
	 * @param _image Image of new node.
	 */
	public MKSpriteNode(String _imageName)
	{
		this(0,0,_imageName);
	}
	
	/**
	 * Constructor
	 * @param _image Image of new node.
	 */
	public MKSpriteNode(Image _image)
	{
		this(0,0,"");
		this.setImage(_image);
	}
	
	/**
	 * Default Constructor
	 * 
	 * @param _x float x position
	 * @param _y float y position
	 * @param _image Image of new node
	 */
	public MKSpriteNode(float _x, float _y, String _imageName)
	{
		super();
		this.position = new MKPoint(_x, _y);
		if (_imageName != "")
			this.setImage(_imageName);
		else 
			this.objImage = null;
		
	}
	
	
	public void setScale(float _scale)
	{
		super.setScale(_scale);
	}
	
	/**
	 * Gets the width of the node
	 * 
	 * @return float width
	 */
	@Override
	public float getWidth(){
		if(objImage!=null)
			return objImage.getWidth() * this.getScale();
		else
			return 0;
	}
		
	/**
	 * Gets the height of the object
	 * @return the height
	 */
	@Override
	public float getHeight(){
		if(objImage!=null)
			return objImage.getHeight() * this.getScale();
		else
			return 0;
	}
	
	/**
	 * Sets the width of the MKSpriteNode
	 * 
	 * @param float new width
	 */
	@Override
	public void setWidth(float _width)
	{
		super.setWidth(_width);
		this.objImage = this.objImage.getScaledCopy((int)(_width * getScale()), (int)this.objImage.getHeight());
	}
	
	/**
	 * Sets the height of the MKSpriteNode
	 * 
	 * @param float new width
	 */
	@Override
	public void setHeight(float _height)
	{
		super.setHeight(_height);
		this.objImage = this.objImage.getScaledCopy((int)this.objImage.getWidth(), (int)(_height * getScale()));
	}
	
	public void setSize(MKSize _size)
	{
		this.setWidth(_size.getWidth());
		this.setHeight(_size.getHeight());
	}
	
	/**
	 * gets the image
	 * 
	 * @return image
	 */
	public Image getImage(){
		return objImage;
	}
	
	/**
	 * Sets the image of the game object
	 * @param filename the filename of the image
	 */
	public void setImage(String filename){
		try{
			objImage= new Image("Images/" + filename);
		}
		catch(SlickException e){
			System.err.print("BEN -> error" + e);
		}
	}
		
	/**
	 * Sets the image of the game object
	 * @param filename the filename of the image
	 */
	public void setImage(Image _image){
		this.objImage = _image;
	}
	
	/**
	 * Rotates the image by an angle.
	 * 
	 * @param _angle float
	 * @param delta int
	 */
	public void rotateBy(float _angle, int delta)
	{
		setObjectImageRotation(_angle * delta);
	}
	
	/**
	 * Rotates the image to given angle.
	 * 
	 * @param _angle float
	 */
	public void rotateTo(float _angle)
	{
		if (_angle > this.getRotation())
		{
			while (_angle > this.getRotation())
			{
				float dTheta = this.getRotation() + 0.2f;
				//this.objImage.setRotation(dTheta);
				setObjectImageRotation(dTheta);
			}
		}
		else {
			while (_angle < this.getRotation())
			{
				float dTheta = this.getRotation() - 0.2f;
				//this.objImage.setRotation(dTheta);
				setObjectImageRotation(dTheta);
			}
		}
	}
	
	/**
	 * Helper method to apply rotation to node and
	 * to children of the node.
	 * 
	 * @param _dTheta float
	 */
	private void setObjectImageRotation(float _dTheta)
	{
		this.objImage.setRotation(_dTheta);
		for (MKNode node : this.getChildren())
		{
			if (node instanceof MKSpriteNode)
			{
				MKSpriteNode n = (MKSpriteNode) node;
				Image objI = n.getImage();
				objI.setCenterOfRotation(this.objImage.getCenterOfRotationX(), this.objImage.getCenterOfRotationY());
				objI.setRotation(_dTheta);
			}
		}
	}
	
	/**
	 * Returns the rotation of the objects
	 * image.
	 * 
	 * @return rotation float
	 */
	public float getRotation()
	{
		return this.objImage.getRotation();
	}
	
	public void setColorFilter(Color _color)
	{
		super.setColorFilter(_color);
//		if (_color != null)
//			this.objImage.setImageColor(_color.r, _color.g, _color.b);
	}
	
	/**
	 * Renders the node.
	 */
	@SuppressWarnings("unused")
	public void render(GameContainer gc, Graphics g){
		// Render self
		
		if (!this.isInteractionEnabled()) return;
		
		if (this.getImage() != null)
		{
			Color color = getColorFilter();
			if (color == null && this.getParent() != null)
				color = this.getParent().getColorFilter();
			
			if (this.getParent() != null)
			{
					this.getImage().draw(
							this.getParent().getCenterPoint().getX() + this.getX() - this.getWidth()/2,
							this.getParent().getCenterPoint().getY() + this.getY() - this.getHeight()/2,
							this.getWidth() * this.getParent().getScale(),
							this.getHeight() * this.getParent().getScale(),
							color);
//				g.drawImage(getImage(),
//						this.getParent().getCenterPoint().getX() + this.getX() - this.getWidth()/2,
//						this.getParent().getCenterPoint().getY() + this.getY() - this.getHeight()/2,
//						color);
			}
			else 
			{
				this.getImage().draw(this.getX(), this.getY(),
						this.getWidth() * this.getScale(),
						this.getHeight() *this.getScale(),
						color);
//				getImage().draw(getX(), getY(), getScale(), color);
				
//				g.drawImage(getImage(), this.getX(), this.getY(), color);
				

			}
			
			
		}
		super.render(gc, g); // render children.
		
		if (MKConstants.DEBUG_PHYSICS && this.getPhysicsBodies() != null )
		{
			g.setColor(Color.green);  
			for (MKPhysicsBody body : this.getPhysicsBodies())
				g.fill(body.getBody());
		}	
	}
}
