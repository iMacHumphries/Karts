package com.MarioKart;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.MarioKart.MKUtilities.MKDebug;

/**
 * MKItemManager.java - manages all the items in the game
 * 
 * @author Ben
 * @version 23-APR-2015
 */
public class MKItemManager {
	private List<MKItem> items;
	private List<MKItem> itemsToRemove;
	
	public MKItemManager() {
		items = new LinkedList<MKItem>();
		itemsToRemove = new LinkedList<MKItem>();
	}
	
	public void addItem(MKItem _item)
	{
		items.add(_item);
	}
	
	public void removeItem(MKItem _item)
	{
		itemsToRemove.add(_item);
	}
	
	public void updateAll(GameContainer gc, int delta, MKMap map) throws SlickException
	{
		for (MKItem item : items)
			item.update(gc, delta, map);
		for (MKItem item : itemsToRemove)
		{
			items.remove(item);
		}
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		for (MKItem item : items)
			if (item.isRenderable())
				item.render(gc, g);
	}
	
	public void updateCollision(MKRacer racer)
	{
		//List<MKItem> toRemove = new LinkedList<MKItem>();
		for (MKItem item : items)
		{
			if (racer.didCollideWith(item))
			{
				MKDebug.log("Apply item: " + item.getName() + " to racer: " + racer.getName());
				item.applyTo(racer);
				itemsToRemove.add(item);
			}
		}
		
	}
}
