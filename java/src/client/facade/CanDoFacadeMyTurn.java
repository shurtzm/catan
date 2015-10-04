package client.facade;

import client.proxy.IServerProxy;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.definitions.ResourceType;
import shared.exceptions.InsufficientSupplies;
import shared.exceptions.InvalidLocation;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.model.Model;
import shared.model.ResourceList;

/**
 * A facade for when it's my turn -- separated from the NotMyTurn facade to
 * avoid an <code>if (it's my turn)</code> statement at the beginning of every
 * single method.
 */
public class CanDoFacadeMyTurn extends CanDoFacade {

    @Override
    public boolean canFinishTurn() {
        return model.canFinishTurn();
    }

    @Override
    public boolean canRobPlayer(int playerIndex) {
        return model.canRobPlayer(playerIndex);
    }

    @Override
    public boolean canRollNumber() {
        return model.canRollNumber();
    }

    @Override
    public boolean canSendChat() {
        return true;
    }

    @Override
    public boolean canBuyCity() {
        return model.canBuyCity();
    }

    @Override
    public boolean canBuildCity(VertexLocation vertex) {
        return model.canBuildCity(vertex); 
    }

    @Override
    public boolean canBuySettlement() {
        return model.canBuySettlement();
    }

    @Override
    public boolean canBuildSettlement(VertexLocation vertex) {
        return canBuySettlement() && model.canBuildSettlement(vertex);
    }
    
    @Override
    public boolean canPlayMonument() {
        return model.canPlayMonopoly(CatanFacade.getMyPlayerIndex());
    }

    @Override
    public boolean canPlayMonopoly() {
        return model.canPlayMonopoly(CatanFacade.getMyPlayerIndex());
    }

    @Override
    public boolean canPlaySoldier() {
        return model.canPlaySoldier(CatanFacade.getMyPlayerIndex());
    }

    @Override
    public boolean canPlayRoadBuilding() {
        return model.canPlayRoadBuilding(CatanFacade.getMyPlayerIndex()); 
    }

    @Override
    public boolean canPlayYearOfPlenty() {
        return model.canPlayYearOfPlenty(CatanFacade.getMyPlayerIndex()); 
    }

    @Override
    public boolean canBuyDevCard() {
        return model.canBuyDevCard(CatanFacade.getMyPlayerIndex()); 
    }

    @Override
    public boolean canOfferMaritimeTrade(ResourceType resourceType) {
        return model.canOfferMaritimeTrade(resourceType);
    }
    
    @Override
    public boolean canAcceptMaritimeTrade(ResourceType resourceType) {
        return model.canAcceptMaritimeTrade(resourceType);
    }
    
    @Override
    public boolean canAcceptTrade(ResourceList tradeOffer) {
        return false;
    }

    @Override
    public boolean canOfferTrade(int playerIndex, ResourceList resourceList) {
        return true;
    }
    
    @Override
    public boolean canBuyRoad() {
    	return model.canBuyRoad();
    }
    
    @Override
    public boolean canBuildRoad(EdgeLocation roadLocation) throws InvalidLocation {
    	return model.canBuyRoad() && model.canBuildRoad(roadLocation);
    }
    
    @Override
    public boolean canDiscardCards(int playerIndex, ResourceList discardedCards ) {
        return model.canDiscardCards(playerIndex);
    }
    
   public CanDoFacadeMyTurn(IServerProxy proxy, Model model) {
       super( proxy, model );
   }
   
}
