package com.MarioKart.MKUtilities;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.MarioKart.MKExceptions.MKEmptyPhysicsBodyException;

/**
 * MKNode.java - Very basic class used as template for different types of nodes.
 * 
 * @author Benjamin C. Humphries & Christopher Anglin
 * @version 08-APR-2015
 */
public class MKNode{
	
	protected MKNode parent;
	protected List<MKNode> children;
	protected MKPoint position;
	private MKSize size;
	private MKVector2 velocity;
	private Color colorFilter;
	private float scale=1.0f; 
	private List<MKPhysicsBody> bodies;
	protected float orientation;
	private boolean interactionEnabled;
	private String name;
	private int tag;
	
	/**
	 * Constructor.
	 */
	public MKNode() {
		this.children = new LinkedList<MKNode>();
		this.position = new MKPoint();
		this.size = new MKSize();
		this.velocity = new MKVector2();
		this.bodies = new LinkedList<MKPhysicsBody>();
		this.colorFilter = null;
		parent = null;
		interactionEnabled = true;
		name = "";
	}
	
	/**
	 * Add a child MKNode to this node.
	 * 
	 * @param _node MKNode child.
	 */
	public void addChild(MKNode _node)
	{
		_node.parent = this;
		this.children.add(_node);
	}
	
	/**
	 * Removes the specified child from the 
	 * list of children
	 * 
	 * @param _child MKNode child to be removed.
	 */
	public void removeChild(MKNode _child)
	{
		this.children.remove(_child);
	}
	
	/**
	 * Removes all children in this nodes 
	 * list of children
	 */
	public void removeAllChildren()
	{
		for (MKNode child : this.children)
			this.removeChild(child);
	}
	
	/**
	 * Get a list of children to this node.
	 * 
	 * @return List<MKNode> of children
	 */
	public List<MKNode> getChildren()
	{
		return this.children;
	}
	
	/**
	 * Removes this current node from the parent.
	 */
	public void removeFromParent()
	{
		if (parent!= null)
			parent.children.remove(this);
		else 
			MKDebug.log("Tried to remove but parent is null");
	}
	
	/**
	 * Get the number of children of this node.
	 * 
	 * @return int number of children
	 */
	public int numberOfChildren()
	{
		return this.children.size();
	}
	
	/**
	 * Render Children of MKNode if they are MKSpriteNodes with images.
	 * A child must be rendered before the parent in order to get the 
	 * correct z order.
	 * This adds the child to the center of the parent, and its position
	 * is now dependent on the position of the parent.
	 * Also if parent is scaled this will affect the size of the child.
	 * Children inherit color from the parent if color is null.
	 */
	public void render(GameContainer gc, Graphics g)
	{
		if (!this.isInteractionEnabled()) return;
		
		// Render Children
		for (MKNode child: this.children)
		{
			if (child != null)
				child.render(gc, g);	
		}
		
	}
	
	/**
	 * gets the rectangle of the object
	 * @return rectangle of the bounds
	 */
	public Rectangle getRect(){
		 return new Rectangle(getX(),getY(),getWidth(),getHeight());
		 
	}
	
	/**
	 * Get the center point for a node.
	 * 
	 * @return MKPoint in the center of the node.
	 */
	public MKPoint getCenterPoint()
	{
		return new MKPoint(this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2);
	}
	
	public void setCenterPoint(MKPoint _point)
	{
		this.setPostion(new MKPoint(_point.getX() - this.getWidth()/2, _point.getY()));
	}
	/*
	 * Getters and Setters
	 */
	
	/**
	 * Returns the parent of this node.
	 * 
	 * @return MKNode parent node
	 */
	public MKNode getParent()
	{
		return this.parent;
	}
	
	/**
	 * Getter for x position of node.
	 * 
	 * @return float x
	 */
	public float getX()
	{
		return this.position.getX();
	}
	
	/**
	 * Getter for y position of node.
	 * 
	 * @return float y
	 */
	public float getY()
	{
		return this.position.getY();
	}
	
	/**
	 * 
	 * @return
	 */
	public MKVector2 getVelocity()
	{
		return this.velocity;
	}
	
	/**
	 * Get the current scale of the node.
	 * 
	 * @return float scale
	 */
	public float getScale()
	{
		return this.scale;
	}
	
	/**
	 * Get the width of the node.
	 * 
	 * @return float width
	 */
	public float getWidth()
	{
		return this.size.getWidth() * this.scale;
	}
	
	/**
	 * Get the height of the node.
	 * 
	 * @return float height
	 */
	public float getHeight()
	{
		return this.size.getHeight() * this.scale;
	}
	
	/**
	 * Get the size of the node.
	 * 
	 * @return MKSize object containing width and height
	 */
	public MKSize getSize()
	{
		return this.size;
	}
	
	/**
	 * Get the current color filter for this node.
	 * 
	 * @return Color color filter for node.
	 */
	public Color getColorFilter()
	{ 
		return this.colorFilter; 
	}
	
	/*
	 * Setters 
	 */
	
	/**
	 * Set the x position of the node.
	 * 
	 * @param _x float
	 */
	public void setX(float _x)
	{
		this.position.setX(_x);
	}
	
	/**
	 * Set the y position of the node.
	 * 
	 * @param _y float
	 */
	public void setY(float _y)
	{
		this.position.setY(_y);
	}
	
	/**
	 * Sets the position with a given MKPoint
	 * 
	 * @param _position MKPoint containing (x,y)
	 */
	public void setPostion(MKPoint _position)
	{
		this.position = _position;
	}
		
	public void setVelocity(MKVector2 _vector)
	{
		this.velocity = _vector;
	}
	
	/**
	 * Set the scale for the node.
	 * 
	 * @param _scale float
	 */
	public void setScale(float _scale)
	{
		this.scale = _scale;
	}
	
	/**
	 * Set the width for the node.
	 * 
	 * @param _width float
	 */
	public void setWidth(float _width)
	{
		this.size.setSize(_width, this.getHeight());
	}
	
	/**
	 * Set the height for the node.
	 * 
	 * @param _height float
	 */
	public void setHeight(float _height)
	{
		this.size.setSize(this.getWidth(), _height);
	}
	
	/**
	 * Set the size for a give MKSize
	 * 
	 * @param _size new MKSize object.
	 */
	public void setSize(MKSize _size)
	{
		this.size = _size;
	}
	
	/**
	 * Set the color filter for this node.
	 * 
	 * @param _color Color
	 */
	public void setColorFilter(Color _color)
	{
		this.colorFilter = _color;
	}
	
	/**
	 * Allows for multiple physics bodies to be added to node.
	 * 
	 * @param _body MKPhysicsBody
	 */
	public void addPhysicsBody(MKPhysicsBody _body)
	{
		this.bodies.add(_body);
	}
	
	/**
	 * Returns a list of all the MKPhysicsBodies associated
	 * with this node. 
	 * 
	 * @return bodies List<MKPhysicsBody>
	 */
	public List<MKPhysicsBody> getPhysicsBodies()
	{
		return this.bodies;
	}
	
	/**
	 * Gets a specific rigid body by name. 
	 * Must be associated with this node.
	 * 
	 * @param _name String name of physics body
	 * 
	 * @return Shape rigidBody.
	 */
	public Shape getPhysicsBodyWithName(String _name)
	{
		Shape result = null;
		for (MKPhysicsBody body : this.getPhysicsBodies())
		{
			if (body.getName().equals(_name));
				result = body.getBody();
		}
		return result;
	}

	/**
	 * Gets the orientation of the node.
	 * Used in rotation.
	 * 
	 * @return float orientation.
	 */
	public float getOrientation()
	{
		return this.orientation;
	}
	
	/**
	 * Sets the orientation of the node.
	 * Used in rotation.
	 * 
	 * @param _angle float 
	 */
	public void setOrientation(float _angle)
	{
		this.orientation = _angle;
	}
	
	/**
	 * Gets the position of the node.
	 * 
	 * @return position MKPoint
	 */
	public MKPoint getPosition()
	{
		return this.position;
	}

	/**
	 * @return the interactionEnabled
	 */
	public boolean isInteractionEnabled() {
		return interactionEnabled;
	}

	/**
	 * @param interactionEnabled the interactionEnabled to set
	 */
	public void setInteractionEnabled(boolean interactionEnabled) {
		this.interactionEnabled = interactionEnabled;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean intersects(MKNode node) throws MKEmptyPhysicsBodyException
	{
		boolean result = false;
		if (this.getPhysicsBodies().size() == 0 || 
				node.getPhysicsBodies().size() == 0)		
			throw new MKEmptyPhysicsBodyException("No PhysicsBodies on node that tried to detect intersection");
		
		for (MKPhysicsBody body : this.getPhysicsBodies())
		{
			for (MKPhysicsBody body2 : node.getPhysicsBodies())
			{
				if (body.getBody().intersects(body2.getBody()))
					result = true;
			}
		}
		
		return result;
	}
	
	public boolean intersects(Shape shape) throws MKEmptyPhysicsBodyException
	{
		boolean result = false;
		if (shape == null) return result;
		if (this.getPhysicsBodies().size() == 0)		
			throw new MKEmptyPhysicsBodyException("No PhysicsBodies on node that tried to detect intersection");
		
		for (MKPhysicsBody body : this.getPhysicsBodies())
		{
			if (body.getBody().intersects(shape))
				result = true;
		}
		
		return result;
	}
	
	public boolean contains(MKNode node) throws MKEmptyPhysicsBodyException
	{
		boolean result = false;
		if (this.getPhysicsBodies().size() == 0 || 
				node.getPhysicsBodies().size() == 0)		
			throw new MKEmptyPhysicsBodyException("No PhysicsBodies on node that tried to detect contains");
		
		for (MKPhysicsBody body : this.getPhysicsBodies())
		{
			for (MKPhysicsBody body2 : node.getPhysicsBodies())
			{
				if (body.getBody().contains(body2.getBody()))
					result = true;
			}
		}
		
		return result;
	}
	
	public boolean contains(Shape shape) throws MKEmptyPhysicsBodyException
	{
		boolean result = false;
		if (shape == null) return result;
		if (this.getPhysicsBodies().size() == 0)		
			throw new MKEmptyPhysicsBodyException("No PhysicsBodies on node that tried to detect contains");
		
		for (MKPhysicsBody body : this.getPhysicsBodies())
		{
			if (body.getBody().contains(shape))
				result = true;
		}
		
		return result;
	}

	/**
	 * @return the tag
	 */
	public int getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(int tag) {
		this.tag = tag;
	}
}
