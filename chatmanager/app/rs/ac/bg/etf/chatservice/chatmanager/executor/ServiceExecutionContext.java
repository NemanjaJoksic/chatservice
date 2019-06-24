/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.executor;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

/**
 *
 * @author joksin
 */
public class ServiceExecutionContext extends CustomExecutionContext {
    
    public ServiceExecutionContext(ActorSystem system) {
        super(system, "akka.actor.service-dispatcher");
    }
    
}
