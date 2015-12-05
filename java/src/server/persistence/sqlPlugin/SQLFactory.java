/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.sqlPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import server.persistence.DAO.IGamesDAO;
import server.persistence.DAO.IUsersDAO;
import server.persistence.factory.IFactory;

/**
 *
 */
public class SQLFactory implements IFactory{

    private SQLConnectionUtility connUtility;
    
    public SQLFactory(){
        connUtility = new SQLConnectionUtility();
    }
    
    @Override
    public IGamesDAO getGameDAO() {
        return new SQLGamesDAO( connUtility );
    }

    @Override
    public IUsersDAO getUserDAO() {
        return new SQLUsersDAO( connUtility );
    }
    
}