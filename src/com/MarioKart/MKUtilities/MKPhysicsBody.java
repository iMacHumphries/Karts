package com.MarioKart.MKUtilities;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * MKPhysicsBody.java - Represents a rigid body over an MKNode object that is
 * used to determine physics.
 *  
 * @author Ben
 * @version 22-APR-2015
 */
public class MKPhysicsBody{

	private Shape body;                   // The rigid body
	private MKNode parent;                // The MKNode that associated with this physics body.
	private MKPoint anchorPoint;          // AnchorPoint on the Parent Node.
	private MKPoint offsetPoint;          // Offset
	private String name;                  // Name of the body for reference to object.
	
	private float inverse_mass;           // 1/mass of object. 
	private float restitution;            // bounciness
	private float staticFriction;         // friction
	
	/**
	 * Constructor
	 */
	public MKPhysicsBody() {
		this.inverse_mass = 0.0f;
		this.restitution = 0.0f;
		this.staticFriction= 0.0f;
		this.anchorPoint = new MKPoint();
		this.offsetPoint = new MKPoint();
	}
	
	/**
	 * Constructor with ellipse PhysicsBody.
	 * 
	 * @param _ellipse Shape
	 * @param _parent MKNode
	 */
	public MKPhysicsBody(Ellipse _ellipse, MKNode _parent) {
		this();
		this.parent = _parent;
		this.body = _ellipse;
	}
	
	/**
	 * Constructor with Rectangle PhysicsBody.
	 * 
	 * @param _rectangle Shape
	 * @param _parent MKNode
	 */
	public MKPhysicsBody(Rectangle _rectangle, MKNode _parent) {
		this();
		this.parent = _parent;
		this.body = _rectangle;
	}
	
	public MKPhysicsBody(Shape _shape, MKNode _parent)
	{
		this();
		this.parent = _parent;
		this.body = _shape;
	}
	
	/**
	 * Gets the actual shape rigid body.
	 * 
	 * @return body Shape
	 */
	public Shape getBody()
	{
		return this.body;
	}

	/**
	 * Called every update.
	 * Sets the position of the rigid body 
	 * over the parent (MKNode) and applies offset.
	 * 
	 */
	public void update()
	{
		this.body.setLocation(parent.getX() + this.getAnchorPoint().getX() + this.getOffsetPoint().getX(),
							  parent.getY() + this.getAnchorPoint().getY() + this.getOffsetPoint().getY());	
	}
	
	/**
	 * Gets the position of the PhysicsBody.
	 * 
	 * @return MKPoint
	 */
	public MKPoint getPosition()
	{
		return new MKPoint(this.body.getX(), this.body.getY());
	}

	/**
	 * Handles two physicsBodies colliding, and applies an impulse 
	 * vector to each accordingly (based on their mass and restitution).
	 * 
	 * @param _bodyB MKPhysicsBody other object colliding with this object.
	 * @param delta time change.
	 */
	public void collide(MKPhysicsBody _bodyB, int delta)
	{
		//I = (1+e)*N*(Vr • N) / (1/Ma + 1/Mb)
		
		MKVector2 relativeVelocity = _bodyB.getParent().getVelocity().sub(this.getParent().getVelocity());
		MKVector2 normal = relativeVelocity.normal();

		//if (relativeVelocity.dot(normal) > 0) return;
		float epsilon = determineRestitution(_bodyB);
		
		//float normalMagnitude = normal.magnitude();

		//float impulse =( -1 * (1 + epsilon) * (relativeVelocity.dot(normal)) ) / ( this.getInverseMass() + _bodyB.getInverseMass() );
		float impulse = ( -1 *(1+epsilon) * (relativeVelocity.dot(normal) ) / ( this.getInverseMass() + _bodyB.getInverseMass()) );
		MKDebug.log("impulse = " + impulse);
			    
	    MKVector2 impulseVector = new MKVector2(normal.getX() * impulse, normal.getY() * impulse);
	  
	    MKDebug.log("*impulseVector:" + impulseVector);
	    MKDebug.log("test vel = " + this.parent.getVelocity());
	    
		this.parent.setVelocity(new MKVector2(this.parent.getVelocity().getX() - impulseVector.getX() * this.getInverseMass(),
											  this.parent.getVelocity().getY() - impulseVector.getY() * this.getInverseMass() ));
		_bodyB.parent.setVelocity(new MKVector2(_bodyB.parent.getVelocity().getX() + impulseVector.getX() * _bodyB.getInverseMass(),
												_bodyB.parent.getVelocity().getY() + impulseVector.getY() * _bodyB.getInverseMass() ));
		
	
	}
	
//	private float pythagoreanSolve(float a, float b)
//	{
//		return (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
//	}
	
	/**
	 * Helper to find the min restitution.
	 * 
	 * @param _bodyB MKPhysicsBody.
	 * @return float representing the least of the two.
	 */
	private float determineRestitution(MKPhysicsBody _bodyB)
	{
		return min(this.getRestitution(), _bodyB.getRestitution());
	}
	
	/**
	 * Helper to find the min of two numbers.
	 * 
	 * @param _num1 float
	 * @param _num2 float
	 * @return float min
	 */
	private float min(float _num1, float _num2) {
		float min;
		if (_num1 > _num2)
			min = _num2;
		else 
			min = _num1;
		return min;
	}

	/**
	 * @return the inverse_mass
	 */
	public float getInverseMass() {
		return inverse_mass;
	}

	/**
	 * @param inverse_mass the inverse_mass to set
	 */
	public void setInverseMass(float inverse_mass) {
		this.inverse_mass = inverse_mass;
	}
	
	/**
	 * @param Sets the inverse_mass using 1/mass
	 */
	public void setMass(float mass)
	{
		this.inverse_mass = 1/mass;
	}
	/**
	 * @return the restitution
	 */
	public float getRestitution() {
		return restitution;
	}

	/**
	 * @param restitution the restitution to set
	 */
	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}
	
	/**
	 * Gets the parent of this MKPhysicsBody
	 * 
	 * @return MKNode that is the parent node associated
	 * with this MKPhysicsBody.
	 */
	public MKNode getParent()
	{
		return this.parent;
	}
	
	/**
	 * Sets the parent of the PhysicsBody.
	 * 
	 * @param _parent
	 */
	public void setParent(MKNode _parent)
	{
		this.parent = _parent;
	}
	
	/**
	 * Sets staticFriction
	 * 
	 * @param _friction float
	 */
	public void setStaticFriction(float _friction)
	{
		this.staticFriction = _friction;
	}
	
	/**
	 * Gets the static Friction.
	 * 
	 * @return float 
	 */
	public float getStaticFriction()
	{
		return this.staticFriction;
	}
	
	/**
	 * Sets the anchor point, determining where the body is
	 * placed on the parent MKNode.
	 * 
	 * @param _point MKPoint.
	 */
	public void setAnchorPoint(MKPoint _point)
	{
		this.anchorPoint = _point;
	}
	
	/**
	 * Get the anchor point.
	 * 
	 * @return MKPoint
	 */
	public MKPoint getAnchorPoint()
	{
		return this.anchorPoint;
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

	/**
	 * @return the offsetPoint
	 */
	public MKPoint getOffsetPoint() {
		return offsetPoint;
	}

	/**
	 * @param offsetPoint the offsetPoint to set
	 */
	public void setOffsetPoint(MKPoint offsetPoint) {
		this.offsetPoint = offsetPoint;
	}

	
}