/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands.moves;

import server.commands.Command;
import server.gameinfocontainer.GameInfoContainer;
import server.persistence.Persistence;
import shared.communication.params.moves.PlayRoadBuildingRequest;
import shared.exceptions.HTTPBadRequest;
import shared.model.Model;

/**
 *
 * @author Scott
 */
public class Road_Building extends Command{

    @Override
    public String execute(String json, int gameID, int user) throws HTTPBadRequest {
        if(isUserInGame(gameID, user)) {
            PlayRoadBuildingRequest request = (PlayRoadBuildingRequest) this.getDeserializer()
                                                    .toClass(PlayRoadBuildingRequest.class, json);
           
            Model currentModel = GameInfoContainer.getInstance().getGameModel(gameID);
            currentModel.playRoadBuilding(request);
            
            this.addHistoryMessage(gameID, "played Road Building", user);
            Persistence.getInstance().saveCommand("Road_Building", json, gameID, user,null);
            return this.getSerializer().toJson(currentModel);    
        }
        else {
            return null;
        }
    }
}
