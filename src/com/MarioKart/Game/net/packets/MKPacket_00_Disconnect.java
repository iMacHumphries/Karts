package com.MarioKart.Game.net.packets;

import com.MarioKart.Game.net.MKClient;
import com.MarioKart.Game.net.MKServer;

public class MKPacket_00_Disconnect extends MKPacket{
	
	private String username;
	
	public MKPacket_00_Disconnect(byte[] data) {
		super(00);
		this.username = readData(data);
	}
	
	public MKPacket_00_Disconnect(String _username) {
		super(00);
		this.username = _username;
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
		return ("00" +username).getBytes(); 
	}
	
	public String getUsername()
	{
		return this.username;
	}

}
