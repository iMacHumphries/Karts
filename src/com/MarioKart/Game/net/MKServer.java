package com.MarioKart.Game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.state.BasicGameState;

import com.MarioKart.Constants.MKConstants;
import com.MarioKart.Game.net.packets.MKPacket;
import com.MarioKart.Game.net.packets.MKPacket_00_Disconnect;
import com.MarioKart.Game.net.packets.MKPacket.PacketType;
import com.MarioKart.Game.net.packets.MKPacket_01_Login;
import com.MarioKart.Game.net.packets.MKPacket_02_Move;
import com.MarioKart.*;

public class MKServer extends Thread{

	private DatagramSocket socket;
	//private BasicGameState game;
	private List<MKPlayerTemp> connectedPlayers = new LinkedList<MKPlayerTemp>();
	
	public MKServer(BasicGameState _game) {
		//this.game = _game;
		try {
			this.socket = new DatagramSocket(MKConstants.PORT_NUMBER);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run()
	{
		while(true)
		{
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parsePacket(packet);
		}
	}
	
	private void parsePacket(DatagramPacket packet)
	{
		//System.out.println("server got a packet from a client " + packet);
		byte[] data = packet.getData();
		InetAddress ip = packet.getAddress();
		int port = packet.getPort();
		String message = new String(packet.getData());
		String ID = message.substring(0, 2);
		
		PacketType type = MKPacket.lookUpPacket(ID);
		switch (type)
		{
			case INVALID:
				break;
			case MOVE:
				MKPacket_02_Move movePack = new MKPacket_02_Move(data);
				this.handleMoveFrom(movePack);
				break;
			case DISCONNECT:
				MKPacket_00_Disconnect discp = new MKPacket_00_Disconnect(data);
				System.out.println("["+ip.getHostAddress()+":"+ port+"] " + discp.getUsername() +" has disconnected...");
				//MKPlayerTemp player = new MKPlayerTemp( p.getUsername(),ip, port);
				this.removeConnection(discp);
				break;
			case LOGIN:
				MKPacket_01_Login p = new MKPacket_01_Login(data);
				System.out.println("["+ip.getHostAddress()+":"+ port+"] " + p.getUsername() +" connected...");
				MKPlayerTemp player = new MKPlayerTemp( p.getUsername(),ip, port, p.getKartID());
				this.addConnection(player, p);
				break;
		}
	}
	
	private void handleMoveFrom(MKPacket_02_Move movePack) {
		if (this.getPlayerTemp(movePack.getUsername()) != null)
		{
			int index = getPlayerIndex(movePack.getUsername());
			this.connectedPlayers.get(index).setX(movePack.getX());
			this.connectedPlayers.get(index).setY(movePack.getY());
			movePack.writeData(this);
		}
	}

	
	private int getPlayerIndex(String _username)
	{
		int index = 0;
		for (MKPlayerTemp player : this.connectedPlayers)
		{
			if (player.getUsername().equals(_username))
			{
				return index;
			}
			index++;
		}
		return -1;
	}
	
	private void removeConnection(MKPacket_00_Disconnect packet) {
		//get player to remove
		MKPlayerTemp player = getPlayerTemp(packet.getUsername());
		//remove from list
		this.connectedPlayers.remove(player);
		//Send remove to all clients
		packet.writeData(this);
	}
	
	public MKPlayerTemp getPlayerTemp(String _username)
	{
		MKPlayerTemp result = null;
		for (MKPlayerTemp p : this.connectedPlayers)
		{
			if (p.getUsername().equals(_username))
			{
				result = p;
			}
		}
		return result;
	}

	public void addConnection(MKPlayerTemp _player, MKPacket_01_Login _packet)
	{
		boolean alreadyConnected = false;
		
		// Loop through connected players
		for (MKPlayerTemp p : this.connectedPlayers)
		{
			// if the player is already in the connected players
			if (_player.getUsername().equals(p.getUsername()))
			{
				if (p.getIp() == null)
				{
					p.setIp(_player.getIp());
				}
				if (p.getPort() == -1)
				{
					p.setPort(_player.getPort());
				}
				alreadyConnected = true;
			}
			else {
				// Send the new player packet to every connected player
				sendData(_packet.getData(), p.getIp(), p.getPort());
				
				// Log this player into every other player
				MKPacket_01_Login packetLogin = new MKPacket_01_Login(p.getUsername(),p.getKartID());
				sendData(packetLogin.getData(), _player.getIp(), _player.getPort());
			}
		}
		
		if (!alreadyConnected)
		{
			this.connectedPlayers.add(_player);
		}
	}
	
	public void sendData(byte[] data, InetAddress ip, int port)
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		for (MKPlayerTemp player : this.connectedPlayers)
		{
			sendData(data, player.getIp(), player.getPort());
		}
	}

	/**
	 * @return the connectedPlayers
	 */
	public List<MKPlayerTemp> getConnectedPlayers() {
		return connectedPlayers;
	}

	/**
	 * @param connectedPlayers the connectedPlayers to set
	 */
	public void setConnectedPlayers(List<MKPlayerTemp> connectedPlayers) {
		this.connectedPlayers = connectedPlayers;
	}
}