package com.MarioKart.MKUtilities;

/**
 * MKDebug.java - debugger
 * 
 * @author Benjamin C. Humphries & Christopher Anglin
 * @version 08-APR-2015
 */
public final class MKDebug {

	public static final boolean debug = false; 
	
	/**
	 * Constructor
	 */
	public MKDebug() {
		
	}
	
	/**
	 * Print a String.
	 * 
	 * @param _debugString String
	 */
	public static void log(String _debugString)
	{
		if (debug)
			System.out.println(_debugString);
	}
	
	/**
	 * Print an int.
	 * 
	 * @param _debugInt int
	 */
	public static void log(int _debugInt)
	{
		if (debug)
			System.out.println(_debugInt);
	}
	
	/**
	 * Print a float.
	 * 
	 * @param _debugFloat float
	 */
	public static void log(float _debugFloat)
	{
		if (debug)
			System.out.println(_debugFloat);
	}
	
	/**
	 * Print a boolean.
	 * 
	 * @param _debugBoolean boolean
	 */
	public static void log(boolean _debugBoolean)
	{
		if (debug)
		{
			if (_debugBoolean)
				System.out.println("TRUE");
			else
				System.out.println("FALSE");
		}
	}
	
	/**
	 * Print an Object.
	 * 
	 * @param _debugObject object
	 */
	public static void log(Object _debugObject)
	{
		if (debug && _debugObject != null)
			System.out.println(_debugObject);
	}
	
	/**
	 * Print an Object[].
	 * 
	 * @param _debugObject object
	 */
	public static void log(Object[] _debugObject)
	{
		if (debug && _debugObject != null)
			for (Object obj : _debugObject)
				System.out.println(obj);
	}
	
	/**
	 * Print an float[].
	 * 
	 * @param _debugObject object
	 */
	public static void log(float[] _array)
	{
		if (debug && _array != null)
			for (float f : _array)
				System.out.println(f);
	}
	
}