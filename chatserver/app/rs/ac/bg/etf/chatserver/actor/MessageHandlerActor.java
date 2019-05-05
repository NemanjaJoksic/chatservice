/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Terminated;
import akka.serialization.Serialization;
import akka.util.ByteString;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.ac.bg.etf.chatserver.Config;
import rs.ac.bg.etf.chatserver.actor.serializer.MessageSerializer;
import rs.ac.bg.etf.chatserver.actor.serializer.OutputSerializer;
import rs.ac.bg.etf.chatserver.actor.storage.MessageHandlerActorStorage;
import rs.ac.bg.etf.chatserver.model.Messages;
import rs.ac.bg.etf.chatserver.executor.ActorHandlerExecutionContext;

/**
 *
 * @author joksin
 */
public abstract class MessageHandlerActor extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerActor.class);

    protected final ActorSystem actorSystem;
    protected final Config config;
    protected final MessageHandlerActorStorage actorStorage;
    protected final MessageSerializer messageSerializer;
    protected final OutputSerializer outputSerializer;
    protected final String id;
    protected final String akkaActorRemoteAddress;
    protected final ActorHandlerExecutionContext executionContext;

    public MessageHandlerActor(ActorSystem actorSystem, Config config,
            MessageHandlerActorStorage actorStorage, MessageSerializer messageSerializer,
            OutputSerializer outputSerializer, String id)
            throws UnknownHostException {
        this.actorSystem = actorSystem;
        this.config = config;
        this.actorStorage = actorStorage;
        this.messageSerializer = messageSerializer;
        this.outputSerializer = outputSerializer;
        this.id = id;
        this.akkaActorRemoteAddress = Serialization.serializedActorPath(getSelf());
        this.executionContext = new ActorHandlerExecutionContext(actorSystem);
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        actorStorage.put(id, akkaActorRemoteAddress);
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        disconnect(null);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, this::dispatchTextMessage)
                .match(ByteString.class, this::dispatchBinarytMessage)
                .match(Messages.ChatMessage.class, this::messageReceived)
                .match(Terminated.class, this::disconnect)
                .build();
    }
    
    private void dispatchTextMessage(String message) {
        dispatchMessage(message.getBytes());
    }
    
    private void dispatchBinarytMessage(ByteString message) {
        dispatchMessage(message.toByteBuffer().array());
    }

    protected abstract void dispatchMessage(byte[] message);

    protected abstract void messageReceived(Messages.ChatMessage chatMessage);

    protected abstract void disconnect(Terminated message);
    
}
