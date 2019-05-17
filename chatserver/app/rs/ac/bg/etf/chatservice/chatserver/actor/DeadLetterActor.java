/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor;

import akka.actor.AbstractActor;
import akka.actor.DeadLetter;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.ac.bg.etf.chatservice.chatserver.actor.storage.MessageHandlerActorStorage;
import rs.ac.bg.etf.chatservice.chatserver.model.Messages;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.ACTOR_DISPATCHER;

/**
 *
 * @author joksin
 */
public class DeadLetterActor extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterActor.class);
    
    public static Props props(MessageHandlerActorStorage actorStorage) {
        return Props.create(DeadLetterActor.class, actorStorage)
                .withDispatcher(ACTOR_DISPATCHER);
    }
    
    private final MessageHandlerActorStorage actorStorage;
    
    public DeadLetterActor(MessageHandlerActorStorage actorStorage) {
        this.actorStorage = actorStorage;
    }
    
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DeadLetter.class, this::deadLetter).build();
    }

    private void deadLetter(DeadLetter deadLetter) {
        Object message  = deadLetter.message();
        
        if(message instanceof Messages.ChatMessage) {
            String receiver = ((Messages.ChatMessage) message).getReceiver();
            actorStorage.remove(receiver);
            logger.info("User [{}] are already disconnected. Storing message  to DB", receiver);
        }
    }
}
