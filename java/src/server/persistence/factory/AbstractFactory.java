package server.persistence.factory;

import server.persistence.DAO.IGamesDAO;
import server.persistence.DAO.IUsersDAO;

public abstract class AbstractFactory {
	public abstract IGamesDAO getGamesDAO();
	
	public abstract IUsersDAO getUsersDAO();
}
