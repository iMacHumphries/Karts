package com.MarioKart.Game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.newdawn.slick.state.BasicGameState;

import com.MarioKart.KartsGame;
import com.MarioKart.MKPlayerMP;
import com.MarioKart.Constants.MKConstants;
import com.MarioKart.Game.net.packets.MKPacket;
import com.MarioKart.Game.net.packets.MKPacket.PacketType;
import com.MarioKart.Game.net.packets.MKPacket_00_Disconnect;
import com.MarioKart.Game.net.packets.MKPacket_01_Login;
import com.MarioKart.Game.net.packets.MKPacket_02_Move;

public class MKClient extends Thread{

	private InetAddress hostIPAddress;
	private DatagramSocket socket;
	private BasicGameState game;
	
	public MKClient(BasicGameState _game, String _ipAddress) {
		this.game = _game;
		try {
			this.hostIPAddress = InetAddress.getByName(_ipAddress);
			this.socket = new DatagramSocket();
		} catch (UnknownHostException e){
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}

	public void run()
	{
		while (true)
		{
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	private void parsePacket(byte[] _data, InetAddress _ip, int _port)
	{
		String messageFromServer = new String(_data).trim();
		PacketType type = MKPacket.lookUpPacket(messageFromServer.substring(0, 2));
		switch(type)
		{
			case MOVE:
				MKPacket_02_Move packetMove = new MKPacket_02_Move(_data);
				this.handleMove(packetMove);
				break;
			case LOGIN:
				MKPacket_01_Login packet = new MKPacket_01_Login(_data);
				System.out.println("Got a login message from the server saying " + packet.getUsername() + " is suppose to join the game.");
				KartsGame.KartsGameSharedInstance().addNewPlayerFromLogin(_ip, _port, packet.getUsername(), packet.getKartID());
				break;
			case DISCONNECT:
				MKPacket_00_Disconnect packetDisc = new MKPacket_00_Disconnect(_data);
				System.out.println(packetDisc.getUsername() + " has left the game...");
				KartsGame.KartsGameSharedInstance().removePlayerMP(packetDisc.getUsername());
				break;
			default:
			case INVALID:
				break;
		}
	}
	
	private void handleMove(MKPacket_02_Move packetMove) {
		KartsGame.KartsGameSharedInstance().movePlayerTo(packetMove.getUsername(),
				packetMove.getX(),
				packetMove.getY());
	}

	public void sendData(byte[] data)
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, hostIPAddress, MKConstants.PORT_NUMBER);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}