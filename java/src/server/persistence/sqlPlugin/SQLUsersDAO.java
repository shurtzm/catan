/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.persistence.sqlPlugin;

import java.sql.Connection;

import server.gameinfocontainer.UserInfoBank;
import server.persistence.DAO.IUsersDAO;

/**
 *
 */
public class SQLUsersDAO implements IUsersDAO {
    private Connection conn;
    private  SQLConnectionUtility connectionUtility;
    
    public SQLUsersDAO( SQLConnectionUtility connectionUtility ) {
        this.connectionUtility = connectionUtility;
        this.conn = connectionUtility.getConnection();
    }

    @Override
    public void addUser() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clearUsers() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserInfoBank getUsers() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}