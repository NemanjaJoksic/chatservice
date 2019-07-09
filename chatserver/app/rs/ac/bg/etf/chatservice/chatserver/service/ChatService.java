/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.ActorMaterializer;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Materializer;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.libs.F;
import play.libs.streams.ActorFlow;
import rs.ac.bg.etf.chatservice.chatserver.Config;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.*;
import rs.ac.bg.etf.chatservice.chatserver.actor.PersonalChatMessageHandlerActor;
import rs.ac.bg.etf.chatservice.chatserver.actor.serializer.MessageSerializer;
import rs.ac.bg.etf.chatservice.chatserver.actor.serializer.OutputSerializer;
import rs.ac.bg.etf.chatservice.chatserver.actor.storage.MessageHandlerActorStorage;
import rs.ac.bg.etf.chatservice.chatserver.executor.WebsocketExecutionContext;
import rs.ac.bg.etf.chatservice.chatserver.model.TokenDetails;
import rs.ac.bg.etf.chatservice.chatserver.token.auth.TokenAuthenticator;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;
import rs.ac.bg.etf.chatservice.shared.exception.ExceptionData;

/**
 *
 * @author joksin
 */
@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private BeanFactory factory;

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private Config config;

    @Autowired
    private TokenAuthenticator tokenAuthenticator;

    @Autowired
    private MessageHandlerActorStorage actorStorage;

    private Materializer materializer;
    private WebsocketExecutionContext executionContext;

    @PostConstruct
    public void init() {
        this.materializer = ActorMaterializer.create(
                ActorMaterializerSettings.apply(actorSystem).withDispatcher(WEBSOCKET_DISPATCHER),
                actorSystem
        );
        this.executionContext = new WebsocketExecutionContext(actorSystem);
    }

    public CompletableFuture openConnection(String dataType, String messageType, String ticket) {
        return CompletableFuture.supplyAsync(() -> {

            try {
                TokenDetails details = tokenAuthenticator.validateToken(ticket);
                MessageSerializer messageSerializer = factory.getBean(messageType, MessageSerializer.class);
                OutputSerializer outputSerializer = factory.getBean(dataType, OutputSerializer.class);

                Function<ActorRef, Props> func = (actor)
                        -> PersonalChatMessageHandlerActor.props(actor, actorSystem,
                                config, actorStorage, messageSerializer, outputSerializer,
                                details.getChannel());

                return F.Either.Right(ActorFlow.actorRef(
                        func,
                        actorSystem,
                        materializer));
            } catch (BeansException ex) {
                throw ChatServiceException.generateException(ExceptionData.INVALID_MESSAGE_TYPE, messageType);
            }
        }, executionContext);
    }

}
