package com.MarioKart.Constants;

import javax.swing.JOptionPane;

import com.MarioKart.MKUtilities.MKPoint;
import com.MarioKart.MKUtilities.MKSize;

/**
 * MKConstants.java - global constants class.
 * 
 * @author Benjamin C. Humphries & Christopher Anglin
 * @version 08-APR-2015
 */
public final class MKConstants  {

	public final static String GAME_NAME = "Mario Kart";
	public final static int GAME_WIDTH=900;
	public final static int GAME_HEIGHT=600;
	
	public final static boolean DEBUG_PHYSICS = false;
	
	public final static int LAPS = 3;
	
	public final static int FIRST_PLACE = 1;
	
	public final static int LAST_PLACE = 8;
	
	public final static String[] PLACEMENTS = {"0","1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "Last :(", "Error"};
	public final static String[] CHARACTER_SOUNDS = {"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7"};
	
	public final static MKSize GAME_RECT = new MKSize(GAME_WIDTH, GAME_HEIGHT);
	public final static MKPoint CENTER = new MKPoint(GAME_WIDTH/2, GAME_HEIGHT/2);
	
	public static final int CHARACTER_SELECT_MENU = 0;
	public static final int GAME = 1;
	public static final int START_SCREEN = 2;
	public static final int END = 3;
	public static final int MULTIPLAYER_CHARACTER_SELECT_MENU = 4;
	
	public final static int PORT_NUMBER = 1337;
	
	private static String username = "player";
	
	public static String getUsername()
	{
		return username;
	}
	
	public static void setUserName(String _name)
	{
		username = _name;
	}
			
	// power ups : http://www.spriters-resource.com/gamecube/mkdoubledash/sheet/48281/
	// Mario Kart images: http://thewiredslain.deviantart.com/art/Perler-Art-Mario-Kart-276864894
	// background http://i675.photobucket.com/albums/vv119/almyki/RPGBG_forest.png
	// menu background : http://i.imgur.com/pnWgiuD.jpg
	// play button: http://piq.codeus.net/static/media/userpics/piq_181003_400x400.png
	// checkered: https://www.google.com/search?q=mario+kart+you+win&es_sm=91&biw=1290&bih=890&source=lnms&tbm=isch&sa=X&ei=sCJAVfi1DsGYgwSfhICoDQ&ved=0CAcQ_AUoAg&dpr=0.9#tbm=isch&q=mario+kart+background&imgrc=moFcqL60P-1GcM%253A%3BJAi73CzZ9IxGgM%3Bhttp%253A%252F%252Ffc08.deviantart.net%252Ffs70%252Ff%252F2013%252F172%252F7%252Fe%252Fmario_kart_7_sprite_rip___minimap_background_by_omgweegee2-d6a06vf.png%3Bhttp%253A%252F%252Fomgweegee2.deviantart.com%252Fart%252FMario-Kart-7-Sprite-Rip-Minimap-Background-379602123%3B320%3B240
	// flame: http://fornstrom.tjcomputermagnet.com/studentProjects/2010_Sr_Projects/Barber_Coburn/Vespertine%20Story/Graphics/Characters/!Flame.png
	// bullet bill: https://img0.etsystatic.com/059/0/7240088/il_340x270.699489244_7uto.jpg
	// Character Sounds : http://themushroomkingdom.net/media/mk64/wav
	// game sounds : http://www.talkingwav.com/nintendo_wav_sounds.html
	// replay button : http://fc05.deviantart.net/fs70/f/2011/215/6/3/replay_button_by_ismolbil-d4332bk.png
	// other backgrounds : http://img1.wikia.nocookie.net/__cb20120613123708/clubpenguin/images/7/79/Winners_Podium_furniture_icon.png
	// start : http://i.ytimg.com/vi/Z6uDtRjaycU/maxresdefault.jpg
	// start : http://hsto.org/storage2/e4c/b83/b0d/e4cb83b0dcce4b59e140a3f54c8b445d.jpg
	
  /**
   	Do not construct this class.
  */
  private MKConstants(){
    throw new AssertionError();
  }
}