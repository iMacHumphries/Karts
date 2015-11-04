package com.MarioKart;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.MarioKart.MKUtilities.*;
import com.MarioKart.Constants.*;

/**
 * Driver.java - Starts the KartsGame
 * 
 * @author Benjamin C. Humphries & Christopher Anglin
 * @version 08-APR-2015
 */
public class Driver{
	
	
	public static void main(String[] args)
	{	
		setDockIcon();
		MKDebug.log("Starting client");
		AppGameContainer container = null;
		try {
			StateBasedGameController game = StateBasedGameController.getSharedInstance();
			container= new AppGameContainer(game, MKConstants.GAME_WIDTH,MKConstants.GAME_HEIGHT, false);
			container.setShowFPS(false);
			container.start();
		} catch (SlickException e) {
			MKDebug.log("Error(AppGameContainer): " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void setDockIcon()
	{
		try {
		    Class<?> util = Class.forName("com.apple.eawt.Application");
		    Method getApplication = util.getMethod("getApplication", new Class[0]);
		    Object application = getApplication.invoke(util);
		    Class<?> params[] = new Class[1];
		    params[0] = Image.class;
		    Method setDockIconImage = util.getMethod("setDockIconImage", params);
		    java.awt.Image image = (java.awt.Image) ImageIO.read(new File("Images/icon.png"));
		    setDockIconImage.invoke(application, (java.awt.Image)image);
		    
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		    // log exception
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		    // log exception
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		    // log exception
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		    // log exception
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
