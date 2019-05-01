/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor.message;

import java.io.Serializable;

/**
 *
 * @author joksin
 */
public class ActorStarted implements Serializable {
    
    private String id;
    private String address;

    public ActorStarted() {
        
    }

    public ActorStarted(String id, String address) {
        this.id = id;
        this.address = address;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
