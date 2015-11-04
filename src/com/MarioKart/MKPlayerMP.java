package com.MarioKart;
import java.net.InetAddress;


public class MKPlayerMP extends MKPlayer{

	private InetAddress ip;
	private int port;
	private String username;
	
	public MKPlayerMP(InetAddress _ip, int _port, String _username, int kartID) {
		super();
		this.ip = _ip;
		this.port = _port;
		this.username = _username;
		this.getLabel().setText(username);
		this.setKartID(kartID);
	}
	
	public MKPlayerTemp convertToTemp()
	{
		return new MKPlayerTemp(this.username, this.ip, this.port, this.getKartID());
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
	
	public String toString()
	{
		return "[Player: " + username+ " : ip:" + ip.getHostAddress() + " port:" + port + "]";
	}

}
