package shared.communication.params;

/**
 * Wrapper object for the IServerProxy.joinGame request
 *
 *
 * JoinGameRequest { id (integer): The ID of the game to join, color (string) =
 * ['red' or 'green' or 'blue' or 'yellow' or 'puce' or 'brown' or 'white' or
 * 'purple' or 'orange']: What color you want to join (or rejoin) as. }
 */
public class JoinGameRequest {
	//int id - ID of the game the player wants to join
    //String color - color that the player wants to be

    int id;
    String color;
    
    public JoinGameRequest() {
        
    }

    public JoinGameRequest(int gameID, String color) {
        this.id = gameID;
        this.color = color;
    }

    public int getGameID() {
        return id;
    }

    public void setGameID(int gameID) {
        this.id = gameID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    

}
