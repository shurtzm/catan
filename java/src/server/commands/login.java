/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.commands;

/**
 *
 * @author Scott
 */
public class login extends Command{

    @Override
    public String execute(String json, String gameID) {
        super.execute(json, gameID); //To change body of generated methods, choose Tools | Templates.
        return "LOGIN";
    }
    
}
