package com.MarioKart;
import java.util.LinkedList;
import java.util.List;

import com.MarioKart.MKUtilities.*;
/**
 * MKTimeManager.java - manages game timers.
 * 
 * @author Ben
 * @version 22-APR-2015
 */
public class MKTimeManager {

	private List<MKTimer> timers;
	
	public MKTimeManager() {
		timers = new LinkedList<MKTimer>();
	}
	
	public void addTimer(MKTimer _t)
	{
		timers.add(_t);
	}
	
	public void removeTimer(MKTimer _t)
	{
		timers.remove(_t);
	}
	
	public void addTimerAndFire(MKTimer _t)
	{
		timers.add(_t);
		_t.fire();
	}
	
	public void updaterTimers(int _delta)
	{
		List<MKTimer> toBeRemoved = new LinkedList<MKTimer>();
		for (MKTimer timer : timers)
		{
			timer.update(_delta);
			if (timer.isFinished())
			{
				toBeRemoved.add(timer);
			}
		}
		for (MKTimer t : toBeRemoved)
		{
			removeTimer(t);
		}
	}

}
