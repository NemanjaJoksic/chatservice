/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.ac.bg.etf.chatserver.Config;
import rs.ac.bg.etf.chatserver.actor.serializer.MessageSerializer;
import rs.ac.bg.etf.chatserver.actor.serializer.OutputSerializer;
import rs.ac.bg.etf.chatserver.actor.storage.MessageHandlerActorStorage;
import rs.ac.bg.etf.chatserver.model.Messages;
import static rs.ac.bg.etf.chatserver.Consts.ACTOR_DISPATCHER;

/**
 *
 * @author joksin
 */
public class PersonalChatMessageHandlerActor extends MessageHandlerActor {

    public static Props props(ActorRef out, ActorSystem actorSystem, Config config,
            MessageHandlerActorStorage actorStorage, MessageSerializer messageSerializer,
            OutputSerializer outputSerializer, String channel) {
        return Props.create(PersonalChatMessageHandlerActor.class, out, actorSystem, config,
                actorStorage, messageSerializer, outputSerializer, channel).withDispatcher(ACTOR_DISPATCHER);
    }

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerActor.class);

    private final ActorRef out;

    public PersonalChatMessageHandlerActor(ActorRef out, ActorSystem actorSystem, Config config,
            MessageHandlerActorStorage actorStorage, MessageSerializer messageSerializer,
            OutputSerializer outputSerializer, String channel) throws UnknownHostException {
        super(actorSystem, config, actorStorage, messageSerializer, outputSerializer, channel);

        this.out = out;

        logger.info("User [{}] has connected", channel);
        logger.info("Actor - username: {}, remote address: {}", channel, akkaActorRemoteAddress);

    }

    @Override
    protected void dispatchMessage(byte[] message) {
        try {
            Messages.ChatMessage chatMessage = messageSerializer.deserialize(message);
            chatMessage.setSender(id);

            logger.info("[{}] Sending message to [{}]: {}",
                    id,
                    chatMessage.getReceiver(),
                    chatMessage.getMessage());

            actorStorage.get(chatMessage.getReceiver()).thenAcceptAsync(actorPathOptional -> {
                if (actorPathOptional.isPresent()) {
                    ActorSelection actor = actorSystem.actorSelection(actorPathOptional.get());
                    actor.tell(chatMessage, getSelf());
                } else {
                    logger.info("User [{}] is offline", chatMessage.getReceiver());
                }
            }, executionContext);
            
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void messageReceived(Messages.ChatMessage chatMessage) {
        try {
            logger.info("[{}] Message received from [{}]: {}",
                    id,
                    chatMessage.getReceiver(),
                    chatMessage.getMessage());
            byte[] bytes = messageSerializer.serialize(chatMessage);
            out.tell(outputSerializer.serialize(bytes), getSelf());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void disconnect(Terminated message) {
        actorStorage.remove(id);
        logger.info("User [{}] has disconnected", id);
    }
}
