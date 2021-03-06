package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import shared.model.Model;
import shared.model.map.*;
import client.base.*;
import client.data.*;
import client.facade.CatanFacade;

import java.util.logging.Level;
import java.util.logging.Logger;
import shared.exceptions.GetPlayerException;

import shared.exceptions.ServerException;
import shared.model.Player;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {
	
	private IRobView robView;
    private int roadBuildingCardCounter = 0;
    private boolean playingRoadBuilding = false;
    private EdgeLocation firstEdgeLocation;
    private EdgeLocation secondEdgeLocation;
	
	private boolean robbingModalUp = false;
	//private boolean isRobbing
	
	private HexLocation robLocation;
    
    private static HexLocation[] waterHexes = new HexLocation[] {
        new HexLocation( 0, -3 ),
        new HexLocation( 1, -3 ),
        new HexLocation( 2, -3 ),
        new HexLocation( 3, -3 ),
        new HexLocation( 3, -2 ),
        new HexLocation( 3, -1 ),
        new HexLocation( 3, 0 ),
        new HexLocation( 2, 1 ),
        new HexLocation( 1, 2 ),
        new HexLocation( 0, 3 ),
        new HexLocation( -1, 3 ),
        new HexLocation( -2, 3 ),
        new HexLocation( -3, 3 ),
        new HexLocation( -3, 2 ),
        new HexLocation( -3, 1 ),
        new HexLocation( -3, 0 ),
        new HexLocation( -2, -1 ),
        new HexLocation( -1, -2 )
    };
	
	public MapController(IMapView view, IRobView robView) {
		super(view);
        CatanFacade.addObserver( this );
		
		setRobView(robView);
		//isRobbing = false;
		
		initFromModel();
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	/**
	 * all of this is hard coded info, you need to use these same 
	 * functions and format to generate the map from your model
	 */
	protected void initFromModel() {
		//Get Model's map from CatanFacade
		Model model  = CatanFacade.getModel();
        if ( model == null )
            return;
        
		CatanMap map = model.getMap();
        if ( map == null )
            return;
		
        //Add all the hexes
		List<Hex> hexList = map.getHexes();
		for (Hex hex : hexList) {
			//Set the hex type
			HexType hexType = hex.getHexType();
			HexLocation hexLoc = hex.getLocation();
			getView().addHex(hexLoc, hexType);
			
			//Add the number to this hex
			getView().addNumber(hexLoc, hex.getNumber());
			
			/*
			//Add the robber if this hexType is Desert
			//Do not update the robber position if the robber modal is up
			if (hexType == HexType.DESERT && !isRobbing) {
				getView().placeRobber(hexLoc);
			}
			*/
		}
		
		//Place the robber
		HexLocation robLocation = CatanFacade.getModel().getMap().getRobber();
		getView().placeRobber(robLocation);
        
        for ( HexLocation waterHex : waterHexes ) {
            getView().addHex( waterHex, HexType.WATER );
        }
			
		//If the Model has settlements or cities, add those
		List<VertexObject> vObjects = map.getCitiesAndSettlements();
		for (VertexObject vObj : vObjects) {
				CatanColor color = vObj.getColor();
				VertexLocation vertLoc = vObj.getLocation();
			if (vObj instanceof Settlement) {
				getView().placeSettlement(vertLoc, color);
			} else if (vObj instanceof City) {
				getView().placeCity(vertLoc, color);
			}
		}
		
		//If the Model has roads, add those
		List<Road> roads = map.getRoads();
        if(firstEdgeLocation!=null){
            Road firstRoad = new Road(CatanFacade.getMyPlayerIndex(),firstEdgeLocation);
            roads.add(firstRoad);
        }
		for (Road road : roads) {
			EdgeLocation edgeLoc = road.getLocation();
			CatanColor color = road.getColor();
			getView().placeRoad(edgeLoc, color);
		}
		
		//Initialize the ports
		List<Port> ports = map.getPorts();
		for (Port port : ports) {
			EdgeLocation edgeLoc = port.getEdgeLocation();
			PortType type = port.getPortType();
			getView().addPort(edgeLoc, type);
		}
		
	}

	
	
	
	
	//================================================================
	// For each of the following "Can" methods: call the appropriate canDo(0
	//For each "Do" send the request to the server and update all info as a result of the 
	//action (bank amounts, remaining settlements, map...)
	
	
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
       if(firstEdgeLocation!=null){ 
        if(edgeLoc.getNormalizedLocation().equals(firstEdgeLocation.getNormalizedLocation())){
            return false;
        }
        if(isConnectedRoad(edgeLoc, CatanFacade.getMyPlayerIndex())){
           return true;
        }
        
       }
        int currentPlayer = CatanFacade.getModel().getTurnTracker().getCurrentTurn();
        return CatanFacade.getCurrentState().canBuildRoad(edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {		
		 return CatanFacade.getCurrentState().canBuySettlement() 
            && CatanFacade.getCurrentState().canBuildSettlement(vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return CatanFacade.getCurrentState().canBuyCity() 
            && CatanFacade.getCurrentState().canBuildCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
        //System.out.println("this is the hexlocation of robber: "+hexLoc);
        //System.out.println("current state: "+CatanFacade.getCurrentState().toString());
        
		return CatanFacade.getCurrentState().canPlaceRobber(hexLoc);		
	}

	public void placeRoad(EdgeLocation edgeLoc) {
        try {
            boolean free = CatanFacade.isSetup();
            if(playingRoadBuilding){
                free = true;
                if(roadBuildingCardCounter==0){
                    firstEdgeLocation = edgeLoc;
                    roadBuildingCardCounter++;
//                    BuildRoadRequest request = new BuildRoadRequest(edgeLoc,free);
//                    request.setPlayerIndex(CatanFacade.getModel().getTurnTracker().getCurrentTurn());
//                    CatanFacade.getModel().buildRoad(request);
//                    Model model = CatanFacade.getModel();
                    initFromModel();
                    getView().startDrop(PieceType.ROAD, getMyColor(), true);
                }else if(roadBuildingCardCounter==1){
                    secondEdgeLocation = edgeLoc;
                    CatanFacade.getCurrentState().playRoadBuilding(firstEdgeLocation, secondEdgeLocation);
                    roadBuildingCardCounter = 0;
                    playingRoadBuilding = false;
                    firstEdgeLocation = null;
                    secondEdgeLocation = null;
                    CatanFacade.getModel().setFirstRoadBuildingLocation(null);
                }
            }else{
            CatanFacade.getCurrentState().buildRoad(edgeLoc,free);
            getView().placeRoad(edgeLoc, getMyColor());
            }
        } catch (ServerException ex) {
            Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
//            CatanFacade.triggerUpdate();
        }
	}

	public void placeSettlement(VertexLocation vertLoc) {
        try {
            CatanFacade.getCurrentState().buildSettlement(vertLoc);
    		getView().placeSettlement(vertLoc, getMyColor());
        } catch (ServerException ex) {
            Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
//            CatanFacade.triggerUpdate();
        }
	}

	public void placeCity(VertexLocation vertLoc) {
        try {
            CatanFacade.getCurrentState().buildCity(vertLoc);
    		getView().placeCity(vertLoc, getMyColor());
        } catch (ServerException ex) {
            Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
//            CatanFacade.triggerUpdate();
        }
	}

	public void placeRobber(HexLocation hexLoc) {
		robbingModalUp = false;
       
		//Move the robber to the rob hex
		robLocation = hexLoc;
		//isRobbing = true; //Robber position won't revert back when the poller updates the model 
		getView().placeRobber(hexLoc);
		
		//Get all the players on the Hex where robber got moved to
		ArrayList<RobPlayerInfo> playersToRob = new ArrayList<RobPlayerInfo>();
		
		int numPlayers = CatanFacade.getCurrentGamePlayers().length;
		assert(numPlayers == 4); //Assuming there are 4 players in the game
		for (int i = 0; i < numPlayers; i++) {
			//If the player is rob-able at this hex
			if (CatanFacade.getModel().canRobPlayer(i, hexLoc, CatanFacade.getMyPlayerIndex())) {
				//Add them to the arraylist of players to rob
				RobPlayerInfo playerToRob = CatanFacade.getModel().getRobPlayerInfo(i);
				playersToRob.add(playerToRob);
			}
		}
		
		//Convert playersToRob from ArrayList to Array
		RobPlayerInfo[] robPlayerArray = new RobPlayerInfo[playersToRob.size()];
		robPlayerArray = playersToRob.toArray(robPlayerArray);
		
		//Set the RobPlayerInfo objects in the RobView modal

		getRobView().setPlayers(robPlayerArray);
		
		//Bring up the RobView's modal
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
        // Allow to cancel if it's in normal gameplay rather than setup
		boolean isCancelAllowed = CatanFacade.isPlaying();
        
        if ( ! getView().isModalShowing() )
            getView().startDrop(pieceType, getMyColor(), isCancelAllowed);
	}
	
	public void cancelMove() {
        
        if(playingRoadBuilding){
            roadBuildingCardCounter = 0;
            playingRoadBuilding = false;
            getView().removeRoad(firstEdgeLocation);
            firstEdgeLocation = null;
            secondEdgeLocation = null;
            initFromModel();
            CatanFacade.triggerUpdate();
        }
        if(getView().isModalShowing())
		((MapView)getView()).cancelDrop();
	}
	
	public void playSoldierCard() {	
		getView().startDrop(PieceType.ROBBER, getMyColor(), true);
	}
	
	public void playRoadBuildingCard() {
        
        playingRoadBuilding = true;
        getView().startDrop(PieceType.ROAD, getMyColor(), true);
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {
        try {
            // if state playing send play dev card request to server
            CatanFacade.getCurrentState().robPlayer(victim.getPlayerIndex(), robLocation);
            OverlayView.closeAllModals();
        } catch (ServerException ex) {
            Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    @Override
    public void robNoPlayer() {
        RobPlayerInfo noPlayer = new RobPlayerInfo();
        noPlayer.setPlayerIndex(-1);
        this.robPlayer( noPlayer );
    }

    @Override
    public void update(Observable o, Object arg) {
        //Initialize map from the Model
    	initFromModel();
        
        //Show the placeRobber modal if turnTracker status is "ROBBING" and if this is the current player
        if (CatanFacade.isRobbing()) {
        	if ( !robbingModalUp && CatanFacade.getModel().getTurnTracker().getCurrentTurn() == CatanFacade.getMyPlayerIndex()) {
        		// Sometimes update() gets called twice. This prevents it from calling startDrop() multiple times.
                robbingModalUp = true;
                delayedStartRobber();
        	}
        }
    }
    
    private void delayedStartRobber() {
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                @Override
                public void run() {
                    getView().startDrop(PieceType.ROBBER, getMyColor(), false);
                }
            }, 
            750
        );
    }
    
    private CatanColor getMyColor() {
        Model theModel = CatanFacade.getModel();
        int mypidx = CatanFacade.getMyPlayerIndex();
        Player thePlayer = null;
        try {
            thePlayer = theModel.getPlayer(mypidx);
        } catch (GetPlayerException ex) {
            Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        CatanColor myColor = thePlayer.getColor();
        
        return myColor;
    }
    
    public boolean isConnectedRoad(EdgeLocation location, int myPlayerIndex){
        EdgeLocation normEdge = location.getNormalizedLocation();
        HexLocation normHexLocation = normEdge.getHexLoc();
//        int currentPlayer = CatanFacade.getModel().getTurnTracker().getCurrentTurn();
        
     //check if connected and determine connected verticies
        boolean connected = false;
        ArrayList<VertexLocation> connectedVerticies = new ArrayList<VertexLocation>();

            // check around North edge 
            if (normEdge.getDir() == EdgeDirection.North) {

                HexLocation northeastNeighbor = normHexLocation.getNeighborLoc(EdgeDirection.NorthEast);
                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        northeastNeighbor, EdgeDirection.NorthWest)) && CatanFacade.getModel().isValidPortEdge(normEdge)) {
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(normEdge.getHexLoc(), VertexDirection.NorthEast);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }

                HexLocation northwestNeighbor = normHexLocation.getNeighborLoc(EdgeDirection.NorthWest);
                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        northwestNeighbor, EdgeDirection.NorthEast)) && CatanFacade.getModel().isValidPortEdge(normEdge)) {
         
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(normEdge.getHexLoc(), VertexDirection.NorthWest);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }

                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        normEdge.getHexLoc(), EdgeDirection.NorthEast)) && CatanFacade.getModel().isValidPortEdge(normEdge)) {
  
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(normEdge.getHexLoc(), VertexDirection.NorthEast);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }
                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        normEdge.getHexLoc(), EdgeDirection.NorthWest))  && CatanFacade.getModel().isValidPortEdge(normEdge)) {

                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(normEdge.getHexLoc(), VertexDirection.NorthWest);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }
            }
            //Check arround the NorthWest edge
            if (normEdge.getDir() == EdgeDirection.NorthWest) {
                HexLocation northwestNeighbor = normEdge.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest);
                HexLocation southwestNeighbor = normEdge.getHexLoc().getNeighborLoc(EdgeDirection.SouthWest);

                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        northwestNeighbor, EdgeDirection.NorthEast))  && CatanFacade.getModel().isValidPortEdge(normEdge)) {
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(normEdge.getHexLoc(), VertexDirection.NorthWest);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }

                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        southwestNeighbor, EdgeDirection.NorthEast)) && CatanFacade.getModel().isValidPortEdge(normEdge)) {

                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(southwestNeighbor, VertexDirection.NorthEast);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }
                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        southwestNeighbor, EdgeDirection.North))  && CatanFacade.getModel().isValidPortEdge(normEdge)) {
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(southwestNeighbor, VertexDirection.NorthEast);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }
                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        normEdge.getHexLoc(), EdgeDirection.North))  && CatanFacade.getModel().isValidPortEdge(normEdge)) {
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(normEdge.getHexLoc(), VertexDirection.NorthWest);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }
            }
            //Check arround the NorthEast edge
            if (normEdge.getDir() == EdgeDirection.NorthEast) {
                HexLocation northeastNeighbor = normEdge.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast);
                HexLocation southeastNeighbor = normEdge.getHexLoc().getNeighborLoc(EdgeDirection.SouthEast);

                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        northeastNeighbor, EdgeDirection.NorthWest)) && CatanFacade.getModel().isValidPortEdge(normEdge)) {
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(normEdge.getHexLoc(), VertexDirection.NorthEast);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }
                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        southeastNeighbor, EdgeDirection.NorthWest))  && CatanFacade.getModel().isValidPortEdge(normEdge)) {
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(southeastNeighbor, VertexDirection.NorthWest);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }
                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        southeastNeighbor, EdgeDirection.North))  && CatanFacade.getModel().isValidPortEdge(normEdge)) {
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(southeastNeighbor, VertexDirection.NorthEast);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }
                if (firstEdgeLocation.getNormalizedLocation().equals(new EdgeLocation(
                        normEdge.getHexLoc(), EdgeDirection.North)) && CatanFacade.getModel().isValidPortEdge(normEdge)) {
                    connected = true;
                    VertexLocation suspectVertex = new VertexLocation(normEdge.getHexLoc(), VertexDirection.NorthEast);
                    if (!connectedVerticies.contains(suspectVertex)) {
                        connectedVerticies.add(suspectVertex);
                    }
                }
            }
        

        //check connected verticies for own or buildings
        if (connected && !isBuiltThroughOpponent(connectedVerticies, myPlayerIndex)) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isBuiltThroughOpponent(ArrayList<VertexLocation> suspectVerticies, int myPlayerIndex) {
        int numOpponentsBuiltThrough = 0;
        for (VertexLocation loc : suspectVerticies) {
            for (VertexObject vObj : CatanFacade.getModel().getMap().getCitiesAndSettlements()) {
                if (vObj.getLocation().getNormalizedLocation().equals(loc)) {
                    if (vObj.getOwner() != myPlayerIndex) {
                        numOpponentsBuiltThrough++;
                    } else {
                        //found valid connected vertex with own building
                        return false;
                    }
                }
            }
        }
	    if (numOpponentsBuiltThrough == suspectVerticies.size()) {
            //every suspect vertex is through an opponent
            return true;
        } else {
            //connected vertex with no building
            return false;
        }
    }
}

