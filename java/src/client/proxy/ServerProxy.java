package client.proxy;

import java.util.List;

import shared.communication.params.*;
import shared.communication.responses.*;
import shared.communication.responses.model.*;
import shared.exceptions.ServerException;

public class ServerProxy implements IServerProxy {

    @Override
    public ModelResponse postCommands() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelResponse getCommands() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAi() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelResponse buildRoad() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void offerTrade() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void acceptTrade() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void maritimeTrade() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buyDevCard() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playYearOfPlenty() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playRoadBuilding() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playSoldier() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playMonopoly() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buildSettlement() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buildCity() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendChat() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rollNumber() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void robPlayer() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void finishTurn() throws ServerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public boolean login(Credentials userCredentials) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(Credentials userCredentials) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<GameResponse> getGamesList() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NewGameResponse createGame(CreateGameRequest gameRequests) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean joinGame(JoinGameRequest joinRequest) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveGame(SaveGameRequest saveRequest) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadGame(LoadGameRequest loadRequest) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ModelResponse getGameModel(int version) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelResponse resetGame() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

}
