package com.MarioKart;
import org.newdawn.slick.state.BasicGameState;
import com.MarioKart.Game.net.*;

public class MKMultiplayerManager {

	private MKServer server;
	private MKClient client;
	private KartsGame kg = KartsGame.KartsGameSharedInstance();
	
	public static MKMultiplayerManager manager;
	public static MKMultiplayerManager getSharedInstance()
	{
		if (manager == null)
			manager = new MKMultiplayerManager();
		return manager;
	}
	
	public MKMultiplayerManager() {
		client = new MKClient(kg, "localhost");
		client.start();
		System.out.println("Starting client ...");
	}

	public void hostServer()
	{
		System.out.println("Hosting server");
		server = new MKServer(kg);
		server.start();
		System.out.println("Server running ...");
	}
	/**
	 * @return the server
	 */
	public MKServer getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(MKServer server) {
		this.server = server;
	}

	/**
	 * @return the client
	 */
	public MKClient getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(MKClient client) {
		this.client = client;
	}
	
}
