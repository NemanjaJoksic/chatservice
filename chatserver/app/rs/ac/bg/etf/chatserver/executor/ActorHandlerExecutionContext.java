/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.executor;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;
import static rs.ac.bg.etf.chatserver.Consts.ACTOR_DISPATCHER;

/**
 *
 * @author joksin
 */
public class ActorHandlerExecutionContext extends CustomExecutionContext {
    
    public ActorHandlerExecutionContext(ActorSystem system) {
        super(system, ACTOR_DISPATCHER);
    }
    
}
