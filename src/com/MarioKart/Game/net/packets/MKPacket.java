package com.MarioKart.Game.net.packets;

import com.MarioKart.Game.net.*;

public abstract class MKPacket {
	
	private byte ID;
	
	public MKPacket(int _ID) {
		this.ID = (byte) _ID;
	}
	
	/**
	 * Sends data to the server from this client.
	 * 
	 * @param client
	 */
	public abstract void writeData(MKClient client);
	
	/**
	 * Sends data from the server to all the clients
	 * within the server.
	 * 
	 * @param server
	 */
	public abstract void writeData(MKServer server);
	
	public abstract byte[] getData();
	
	public String readData(byte[] data){
		String message = new String(data).trim();
		return message.substring(2);
	}
	
	public static PacketType lookUpPacket(int _id)
	{
		PacketType result = PacketType.INVALID;
		for (PacketType p : PacketType.values())
		{
			if (p.getID() == _id)
				result = p;
		}
		return result;
	}
	
	public static PacketType lookUpPacket(String _id)
	{
		try{
			return lookUpPacket(Integer.parseInt(_id.substring(0, 2)));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return PacketType.INVALID;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public static enum PacketType{
		INVALID(-1),
		DISCONNECT(00),
		LOGIN(01),
		MOVE(02);
		
		private int packetID;
		private PacketType(int _id)
		{
			this.packetID = _id;
		}
		
		public int getID()
		{
			return this.packetID;
		}
	}

	public String toString()
	{
		return new String(this.getData());
	}
}
