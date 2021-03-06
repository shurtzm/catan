/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared.communication.params.moves;

/**
 * SendChat { type (sendChat), playerIndex (integer): Who's sending this chat
 * message, content (string) }
 */
public class SendChatRequest extends MoveRequest {
    private String content;

    public SendChatRequest(String content) {
            this.content = content;
    }
    
    public SendChatRequest(int player, String content) {
        this.playerIndex=player;
    	this.content = content;
    	this.setType("sendChat");
}
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
