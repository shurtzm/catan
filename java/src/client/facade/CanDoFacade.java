/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.facade;

import client.proxy.IServerProxy;
import shared.model.Model;

/**
 *
 */
public class CanDoFacade {
    
    IServerProxy proxy;
    Model model;
    
    public CanDoFacade(IServerProxy proxy, Model model){
        this.proxy = proxy;
        this.model = model;
    }
    
    public void canOfferTrade(){
        
    }
    
    /**
     * type (acceptTrade),
     * playerIndex (integer): Who's accepting / rejecting this trade,
     * willAccept (boolean): Whether you accept the trade or not
     */
    public boolean canAcceptTrade() {
    	return false;
    }
    
    public void canMaritimeTrade(){
        
    }
        
    public void canBuyDevCard(){
        
    }
    
    public void canPlayYearOfPlenty(){
        
    }
    
    public void canPlayRoadBuilding(){
        
    }
    
    public void canPlaySoldier(){
        
    }
    
    public void canPlayMonopoly(){
        
    }
     /** 
     * @return true if player has enough resources to buy a road, the edge location in question is connected to
     * a road or settlement/city belonging to the player. 
     */
    public boolean canBuildRoad(int currentPlayerIndex){
        return model.getPlayer(currentPlayerIndex).canBuildRoad();
    }
    
     /** 
     * @return true if player has enough resources to buy a Settlement 
     */
    public boolean canBuildSettlement(){
        return false;
    }
    
    /** 
     * @return true if player has enough resources to buy a City
     */
    public boolean canBuildCity() {
    	return false;
    }
    
    public void canSendChat(){
        
    }
    
    public void canRollNumber(){
        
    }
    
    public void canRobPlayer(){
        
    }
    
    public void canFinishTurn(){
        
    }
    
    
    
    
}
