package com.MarioKart.MKUtilities;
import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 * MKLabelNode.java - A node with text.
 * 
 * @author Ben
 * @version 22-APR-2015
 */
public class MKLabelNode extends MKNode{

	private String text;
	private Font font;
	private TrueTypeFont trueTypeFont;
	private Color color;
	
	/**
	 * Constructor
	 */
	public MKLabelNode()
	{
		this(" ");
	}
	
	/**
	 * Constructor
	 * 
	 * @param _text
	 */
	public MKLabelNode(String _text) {
		super();
		this.text = _text;
		this.font = new Font("Verdana", Font.BOLD, 20);
		this.setFont(font);
		//this.trueTypeFont = new TrueTypeFont(font, true);
		this.color = Color.white;
	}
	
	/**
	 * Sets the text to be displayed
	 */
	public void setText(String _text)
	{
		this.text = _text;
	}
	
	/**
	 * Get the text.
	 * @return String text
	 */
	public String getText()
	{
		return this.text;
	}
	
	/**
	 * Sets the color of the text.
	 * @param _c Color 
	 */
	public void setColor(Color _c)
	{
		this.color = _c;
	}
	
	/**
	 * Get the current color of the text.
	 * 
	 * @return Color 
	 */
	public Color getColor()
	{
		return this.color;
	}
	
	/**
	 * Set the font.
	 * 
	 * @param font Font object.
	 */
	public void setFont(Font font)
	{
		this.trueTypeFont = new TrueTypeFont(font, true);
	}
	
	/**
	 * Render the label to the screen.
	 */
	public void render(GameContainer gc, Graphics g)
	{
		if (this.getParent() != null)
			trueTypeFont.drawString(getX() + getParent().getX(), getY() + getParent().getY(), getText(), getColor());
		else 
			trueTypeFont.drawString(getX(), getY(), getText(), getColor());
	}
}
