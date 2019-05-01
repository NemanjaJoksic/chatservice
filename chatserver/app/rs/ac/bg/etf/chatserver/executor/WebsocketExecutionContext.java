/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.executor;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;
import static rs.ac.bg.etf.chatserver.Consts.WEBSOCKET_DISPATCHER;

/**
 *
 * @author joksin
 */
public class WebsocketExecutionContext extends CustomExecutionContext {
    
    public WebsocketExecutionContext(ActorSystem system) {
        super(system, WEBSOCKET_DISPATCHER);
    }
    
}
