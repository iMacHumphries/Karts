package com.MarioKart;

import java.net.InetAddress;

public class MKPlayerTemp {
	private InetAddress ip;
	private int port;
	private String username;
	private int x,y;
	private int kartID;
	
	public MKPlayerTemp(String _username, InetAddress _ip, int _port, int _kartID) {
		this.username = _username;
		this.ip = _ip;
		this.port = _port;
		this.kartID = _kartID;
	}

	/**
	 * @return the ip
	 */
	public InetAddress getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the kartID
	 */
	public int getKartID() {
		return kartID;
	}

	/**
	 * @param kartID the kartID to set
	 */
	public void setKartID(int kartID) {
		this.kartID = kartID;
	}

}
