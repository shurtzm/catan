package shared.communication.params;

/**
 * Wrapper object for the IServerProxy.joinGame request
 * @author Alex&Nicky
 *
 */
public class JoinGameRequest {
	//int id - ID of the game the player wants to join
	//String color - color that the player wants to be
        int gameID;
        String color;
   
        public JoinGameRequest(int gameID, String color){
            this.gameID = gameID;
            this.color = color;
        }
        
        
}