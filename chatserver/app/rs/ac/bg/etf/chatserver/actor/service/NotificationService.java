/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor.service;

/**
 *
 * @author joksin
 */
public interface NotificationService {
    
    public void actorStarted(String id, String address);
    
    public void actorStopped(String id);
    
}
