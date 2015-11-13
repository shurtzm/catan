/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.gameinfocontainer;

import java.util.ArrayList;
import java.util.List;

import shared.communication.responses.GameResponse;
import shared.model.Model;

/**
 *
 * @author Scott
 */
public class GameInfoContainer {
	
	private static GameInfoContainer instance;
	
	public static GameInfoContainer getInstance() {
		if (instance == null) {
			instance = new GameInfoContainer();
		}
		return instance;
	}
	
    private ModelBank models;
    private UserInfoBank users;
    
    /**
     * Constructs the GameInfoContainer Object
     */
    GameInfoContainer() {
        models = new ModelBank();
        users = new UserInfoBank();
    }
    
    GameInfoContainer(ModelBank games, UserInfoBank users) {
        models = games;
        users = users;
    }
    
    /**
     * Called by the create.java command class and adds a game to the 
     * GameInfoContainer
     * @param randomTiles 
     * @param randomPorts 
     * @param randomNumbers 
     * @param name 
     * @return 
     */
    public int createGame(String name, boolean randomNumbers, boolean randomPorts, boolean randomTiles){
        this.getModels().addGame(new Model(name, randomNumbers, randomPorts, randomTiles));
        //return the id of the game just added
        return -1;
    }
    
    /**
    *Checks password if it is valid adds user to 
     */
    public int login(String username, String password){
        return users.login(username, password);
    }
    
    /**
     * Adds player to the list of games stored in the correct model
     * @param i 
     * @param string 
     * @param playerId 
     */
    public boolean joinGame(int playerId, String color, int gameId){
        return false;
    }
    
    /**
     * Returns a list of the games in this model
     */
    public String Games(){
        return "list of games";
    }
    
    /**
     * adds new registered player to the userInfoBank
     * @param string2 
     * @param string 
     */
    public int register(String username, String password){
        return users.addUser(username, password);
    }
    
    /**
     * 
     * @return ModelBank of game models currently being handled by the server.
     */
    public ModelBank getModels() {
    	return models;
    }
  
    /**
     * 
     * @return UserInfoBank of registered user information.
     */
    UserInfoBank getRegisteredUserInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	public List<GameResponse> getListOfGames() {
		return getModels().toGameResponseList();
	}
}
