package com.MarioKart.MKUtilities;

/**
 * MKTimer.java - a timer.
 * 
 * @author Ben
 * @version 22-APR-2015
 */
public class MKTimer {
	private float secondsToWait;
	private String selector;
	private float secondsPassed;
	private boolean valid;
	private boolean repeat;
	private boolean finished;
	private Timer delegate;
	
	/**
	 * Constructor that takes
	 * Seconds to wait float
	 * Timer delegate
	 * String _selector to return
	 * Boolean for repeat
	 * 
	 * @param _secondsToWait
	 * @param _target
	 * @param _selector
	 * @param _repeat
	 */
	public MKTimer(float _secondsToWait, Timer _target, String _selector, boolean _repeat)
	{
		this.secondsToWait = _secondsToWait;
		this.selector = _selector;
		secondsPassed = 0.0f;
		valid = false;
		finished = false;
		setRepeat(_repeat);
		delegate = _target;
	}
	
	/**
	 * Constructor that takes
	 * Seconds to wait float
	 * Timer delegate
	 * String _selector to return
	 * Defaults to no repeat
	 * 
	 * @param _secondsToWait float
	 * @param _target Timer
	 * @param _selector String that gets returned
	 */
	public MKTimer(float _secondsToWait, Timer _target, String _selector)
	{
		this(_secondsToWait, _target, _selector,false);
	}
	
	public void update(int delta)
	{
		if(!isValid()) return;
		secondsPassed += delta;
		
		if (secondsToWait <= secondsPassed / 1000.0f)
		{
			secondsPassed = 0.0f;
			//target.timerEnded(selector);
			delegate.timerDidFinish(selector);
			if (!isRepeat())
			{
				setValid(false);
				finished = true;
			}
		}
	}

	/**
	 * @return the delegate
	 */
	public Delegate getDelegate() {
		return delegate;
	}

	/**
	 * @param delegate the delegate to set
	 */
	public void setDelegate(Timer delegate) {
		this.delegate = delegate;
	}

	/**
	 * @return the valid
	 */
	private boolean isValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	private void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return the repeat
	 */
	private boolean isRepeat() {
		return repeat;
	}

	/**
	 * @param repeat the repeat to set
	 */
	private void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	
	/**
	 * Called from outside to start the timer.
	 */
	public void fire()
	{
		this.setValid(true);
	}
	
	/**
	 * Called from outside to stop the timer.
	 */
	public void invalidate()
	{
		this.setValid(false);
	}
	
	/**
	 * Gets if this timer is finished or not.
	 * 
	 * @return boolean isFinished
	 */
	public boolean isFinished()
	{
		return this.finished;
	}
}