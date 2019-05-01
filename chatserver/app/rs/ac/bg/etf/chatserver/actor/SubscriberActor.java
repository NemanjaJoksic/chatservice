/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.ac.bg.etf.chatserver.actor.message.ActorStarted;
import rs.ac.bg.etf.chatserver.actor.message.ActorStopped;
import rs.ac.bg.etf.chatserver.actor.storage.MessageHandlerActorStorage;
import static rs.ac.bg.etf.chatserver.Consts.ACTOR_DISPATCHER;

/**
 *
 * @author joksin
 */
public class SubscriberActor extends AbstractActor {
    
    private static final Logger logger = LoggerFactory.getLogger(SubscriberActor.class);

    public static Props props(MessageHandlerActorStorage actorStorage) {
        return Props.create(SubscriberActor.class, actorStorage)
                .withDispatcher(ACTOR_DISPATCHER);
    }
    
    private final MessageHandlerActorStorage actorStorage;
    
    public SubscriberActor(MessageHandlerActorStorage actorStorage) {
        this.actorStorage = actorStorage;
    }
    
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ActorStarted.class, this::actorStarted)
                .match(ActorStopped.class, this::actorStopped)
                .build();
    }
    
    private void actorStarted(ActorStarted message) {
        logger.info("Actor [{}] with address [{}] are added to storage",
                message.getId(), message.getAddress());
        actorStorage.put(message.getId(), message.getAddress());
    }
    
    private void actorStopped(ActorStopped message) {
        logger.info("Actor [{}] are removed from storage", message.getId());
        actorStorage.remove(message.getId());
    }
    
}
