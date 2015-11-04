package com.MarioKart;
/**
 * MKMapCreator.java - Creates a MKMap
 * 
 * @author Christopher Anglin
 * @version 22-APR-2015
 */
public class MKMapCreator {

	private MKMap map;
	
	/**
	 * Constructor
	 */
	public MKMapCreator() {
		map = new MKMap();
		map.hill();
		map.curveUpConcaveDown();
		map.straightLine();
		map.curveUpConcaveDown();
		map.curveUpConcaveDown();
		map.lineDown();
		map.lineDown();
		map.lineDown();
	}
	
	/**
	 * Gets the MKMap.
	 * 
	 * @return map MKMap object.
	 */
	public MKMap getMap()
	{
		return map;
	}
}