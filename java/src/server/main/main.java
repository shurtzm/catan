/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.main;

import com.sun.net.httpserver.HttpServer;
import server.HTTPhandlers.GameHandler;
import server.HTTPhandlers.GamesHandler;
import server.HTTPhandlers.MovesHandler;
import server.HTTPhandlers.UserHandler;
import server.facade.IServerFacade;
import server.facade.MockResponderFacade;
import server.facade.ResponderFacade;

import java.io.IOException;
import java.net.InetSocketAddress;
import server.HTTPhandlers.SwaggerHandlers;
import server.persistence.Persistence;



/**
 *Initializes web server. Maybe this will read games from a file later (phase 4)
 * Create Server listening on the port specified. 
 */
public class main{
    
	
    private static final int DEFAULT_SERVER_PORT_NUMBER = 8081;
    private static final String DEFAULT_PLUGIN = "sql";
    private static final int DEFAULT_DELTA = 5;
    private static final int MAX_WAITING_CONNECTIONS = 10;   
    
    private static IServerFacade facade = new ResponderFacade();
    

	public static void setFacade(IServerFacade facade) {
		main.facade = facade;
	}
	
	public static void useMockFacade() {
		main.facade = new MockResponderFacade();
	}

	public static int getDefaultServerPortNumber() {
		return DEFAULT_SERVER_PORT_NUMBER;
	}
	
	public HttpServer getServer() {
		return server;
	}

	/**
     * Specifies port to listen to and starts the server. 
     * @param args port number
     */
    /**
	 * The main function that receives the port number and starts the server
	 * 
	 * @param args
	 *            The port number
	 */
	public static void main(String[] args) {
	//start server with default port if no argument is given
		Persistence persis;
		String persistance_type;
		int delta;
		
		
		if (args.length >= 2 && args.length < 4) {
		//if (!args[2].equals("${wipe}")) {
			if(!args[0].equals("${persist}") && !args[1].equals("${delta}")) {	
				persistance_type = args[0].toString();				
				if (Integer.parseInt(args[1]) >=0) {
					delta = Integer.parseInt(args[1]);
					persis = Persistence.getInstance();
					persis.set(persistance_type, delta);
				} else {
					System.err.println("delta must be 0 or greater");
					return;
				}								
				if(args.length == 3 && args[2].toString().equals("wipe")) {
					persis.wipe();					
				} else if (args.length == 3 && args[2].equals("${wipe}")) {
				} else if (args.length == 3){
					System.err.println("you can only 'wipe'");					
				}
				persis.loadData();
				new main().run(DEFAULT_SERVER_PORT_NUMBER);
			}
        }
        else {
                persis = Persistence.getInstance();
				persis.set(DEFAULT_PLUGIN, DEFAULT_DELTA);
				persis.loadData();
				new main().run(DEFAULT_SERVER_PORT_NUMBER);
		}
	}
	private HttpServer server;

	/**
	 * The run for the HTTP server
	 * 
	 * @param port_number
	 *            The port number
	 */
	public void run(int port_number) {
	//initializes HTTP server
		try {
			server = HttpServer.create(new InetSocketAddress(port_number),
					MAX_WAITING_CONNECTIONS);
			System.out.println("listening on port: " + port_number);
		} catch (IOException e) {
			System.out.println("Could not create HTTP server: "
					+ e.getMessage());
			//e.printStackTrace();
			return;
		}
		
		//IServerFacade facade = new MockResponderFacade();
		
	//specify handlers
		server.setExecutor(null); // use the default executor

		server.createContext("/game/", new GameHandler(facade));
		server.createContext("/games/", new GamesHandler(facade));
		server.createContext("/moves/", new MovesHandler(facade));
		server.createContext("/user/", new UserHandler(facade));
		server.createContext("/", new SwaggerHandlers.BasicFile("index.html"));
		server.createContext("/docs/api/data", new SwaggerHandlers.JSONAppender(""));
        server.createContext("/docs/api/view", new SwaggerHandlers.BasicFile(""));
	//start server
		server.start();
	}
    
}
