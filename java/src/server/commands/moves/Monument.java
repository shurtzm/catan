/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands.moves;

import server.commands.Command;
import server.gameinfocontainer.GameInfoContainer;
import server.persistence.Persistence;
import shared.communication.params.moves.MoveRequest;
import shared.exceptions.HTTPBadRequest;
import shared.model.Model;

/**
 *
 * @author Scott
 */
public class Monument extends Command{

    @Override
    public String execute(String json, int gameID, int user) throws HTTPBadRequest {
        if(isUserInGame(gameID, user)) {
            MoveRequest request = (MoveRequest)this.getDeserializer().toClass(MoveRequest.class, json);
            Model currentModel = GameInfoContainer.getInstance().getGameModel(gameID);
            currentModel.playMonument(request);
            
            this.addHistoryMessage(gameID, "played a Monument", user);
            Persistence.getInstance().saveCommand(this.getClassName(this.getClass()), json, gameID, user,null);
            
            return this.getSerializer().toJson(currentModel); 
        }
        else {
            return null;
        }
    }
    
}
