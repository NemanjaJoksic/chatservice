/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.executor;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.STORAGE_DISPATCHER;

/**
 *
 * @author joksin
 */
public class StorageExecutionContext extends CustomExecutionContext {
    
    public StorageExecutionContext(ActorSystem system) {
        super(system, STORAGE_DISPATCHER);
    }
    
}
