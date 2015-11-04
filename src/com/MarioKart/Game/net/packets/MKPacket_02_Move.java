package com.MarioKart.Game.net.packets;

import com.MarioKart.Game.net.MKClient;
import com.MarioKart.Game.net.MKServer;

public class MKPacket_02_Move extends MKPacket{
	
	private String username;
	private int x,y;
	
	public MKPacket_02_Move(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
	}
	
	public MKPacket_02_Move(String _username, int _x, int _y) {
		super(02);
		this.username = _username;
		this.x =_x;
		this.y = _y;
	}
	
	
	@Override
	public void writeData(MKClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(MKServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		//02name,x,y
		return ("02" + username + "," + this.x + "," + this.y).getBytes(); 
	}
	
	public String getUsername()
	{
		return this.username;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
}
