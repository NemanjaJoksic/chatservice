/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor.message;

import java.io.Serializable;

/**
 *
 * @author joksin
 */
public class ActorStopped implements Serializable {
    
    private String id;
    
    public ActorStopped() {
        
    }
    
    public ActorStopped(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
