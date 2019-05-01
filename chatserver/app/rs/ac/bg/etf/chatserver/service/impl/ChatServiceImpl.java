/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.service.impl;

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
import rs.ac.bg.etf.chatserver.Config;
import static rs.ac.bg.etf.chatserver.Consts.*;
import rs.ac.bg.etf.chatserver.actor.PersonalChatMessageHandlerActor;
import rs.ac.bg.etf.chatserver.actor.serializer.MessageSerializer;
import rs.ac.bg.etf.chatserver.actor.serializer.OutputSerializer;
import rs.ac.bg.etf.chatserver.actor.storage.MessageHandlerActorStorage;
import rs.ac.bg.etf.chatserver.executor.WebsocketExecutionContext;
import rs.ac.bg.etf.chatserver.model.TokenDetails;
import rs.ac.bg.etf.chatserver.service.ChatService;
import rs.ac.bg.etf.chatserver.actor.service.NotificationService;
import rs.ac.bg.etf.chatserver.exception.ChatServerException;
import rs.ac.bg.etf.chatserver.exception.ExceptionData;
import rs.ac.bg.etf.chatserver.token.auth.TokenAuthenticator;

/**
 *
 * @author joksin
 */
@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

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

    @Autowired
    private NotificationService notificationService;

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

    @Override
    public CompletableFuture openConnection(String dataType, String messageType, String ticket) {
        return CompletableFuture.supplyAsync(() -> {

            try {
                TokenDetails details = tokenAuthenticator.validateToken(ticket);
                MessageSerializer messageSerializer = factory.getBean(messageType, MessageSerializer.class);
                OutputSerializer outputSerializer = factory.getBean(dataType, OutputSerializer.class);

                Function<ActorRef, Props> func = (actor)
                        -> PersonalChatMessageHandlerActor.props(actor, actorSystem,
                                config, actorStorage, messageSerializer, outputSerializer,
                                notificationService, details.getChannel());

                return F.Either.Right(ActorFlow.actorRef(
                        func,
                        actorSystem,
                        materializer));
            } catch (BeansException ex) {
                throw ChatServerException.generateException(ExceptionData.INVALID_MESSAGE_TYPE,
                        Arrays.asList(messageType));
            }
        }, executionContext);
    }

}
