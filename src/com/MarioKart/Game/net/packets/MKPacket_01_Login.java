package com.MarioKart.Game.net.packets;

import com.MarioKart.Game.net.MKClient;
import com.MarioKart.Game.net.MKServer;

public class MKPacket_01_Login extends MKPacket{

	private String username;
	private int kartID;
	
	public MKPacket_01_Login(String _username, int _kartID) {
		super(01);
		this.username = _username;
		this.kartID = _kartID;
	}
	
	public MKPacket_01_Login(byte[] data) {
		super(01);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.kartID = Integer.parseInt(dataArray[1]);
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
		return ("01" + this.username + "," + this.kartID).getBytes(); 
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public int getKartID()
	{
		return this.kartID;
	}

}
