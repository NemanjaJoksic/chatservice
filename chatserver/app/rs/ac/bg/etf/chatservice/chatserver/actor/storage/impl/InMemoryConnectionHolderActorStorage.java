/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor.storage.impl;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import java.util.Map;
import java.util.Optional;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.ACTOR_DISPATCHER;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.ACTOR_STORAGE_TYPE;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.IN_MEMORY_ACTOR_STORAGE;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.TOPIC_ACTOR;
import rs.ac.bg.etf.chatservice.chatserver.actor.message.ActorStarted;
import rs.ac.bg.etf.chatservice.chatserver.actor.message.ActorStopped;
import rs.ac.bg.etf.chatservice.chatserver.actor.storage.MessageHandlerActorStorage;
import rs.ac.bg.etf.chatservice.chatserver.executor.StorageExecutionContext;

/**
 *
 * @author joksin
 */
@Component
@ConditionalOnProperty(name = ACTOR_STORAGE_TYPE, havingValue = IN_MEMORY_ACTOR_STORAGE)
public class InMemoryConnectionHolderActorStorage implements MessageHandlerActorStorage {

    private static class SubscriberActor extends AbstractActor {

        private static final Logger logger = LoggerFactory.getLogger(SubscriberActor.class);

        public static Props props(InMemoryConnectionHolderActorStorage actorStorage) {
            return Props.create(SubscriberActor.class, actorStorage)
                    .withDispatcher(ACTOR_DISPATCHER);
        }

        private final InMemoryConnectionHolderActorStorage actorStorage;

        public SubscriberActor(InMemoryConnectionHolderActorStorage actorStorage) {
            this.actorStorage = actorStorage;
        }

        @Override
        public AbstractActor.Receive createReceive() {
            return receiveBuilder()
                    .match(ActorStarted.class, this::actorStarted)
                    .match(ActorStopped.class, this::actorStopped)
                    .build();
        }

        private void actorStarted(ActorStarted message) {
            logger.info("Actor [{}] with address [{}] are added to storage",
                    message.getId(), message.getAddress());
            actorStorage.map.put(message.getId(), message.getAddress());
        }

        private void actorStopped(ActorStopped message) {
            logger.info("Actor [{}] are removed from storage", message.getId());
            actorStorage.map.remove(message.getId());
        }

    }

    private static class NotificationService {

        private final ActorSystem actorSystem;
        private final ActorRef mediator;

        public NotificationService(ActorSystem actorSystem) {
            this.actorSystem = actorSystem;
            this.mediator = DistributedPubSub.get(actorSystem).mediator();
        }

        public void actorStarted(String id, String address) {
            mediator.tell(new DistributedPubSubMediator.Publish(TOPIC_ACTOR,
                    new ActorStarted(id, address)), ActorRef.noSender());
        }

        public void actorStopped(String id) {
            mediator.tell(new DistributedPubSubMediator.Publish(TOPIC_ACTOR,
                    new ActorStopped(id)), ActorRef.noSender());
        }

    }

    private final Logger logger = LoggerFactory.getLogger(InMemoryConnectionHolderActorStorage.class);

    @Autowired
    private ActorSystem actorSystem;

    private Map<String, String> map;
    private StorageExecutionContext executionContext;
    private NotificationService notificationService;

    @PostConstruct
    public void init() {
        this.map = new ConcurrentHashMap<>();
        this.executionContext = new StorageExecutionContext(actorSystem);
        this.notificationService = new NotificationService(actorSystem);
        createSubscriberActor();
    }

    private ActorRef createSubscriberActor() {
        ActorRef subscriber = actorSystem.actorOf(SubscriberActor.props(this));
        ActorRef mediator = DistributedPubSub.get(actorSystem).mediator();
        mediator.tell(new DistributedPubSubMediator.Subscribe(TOPIC_ACTOR, subscriber),
                subscriber);
        logger.info("Subscriber created");
        return subscriber;

    }

    @Override
    public CompletionStage<Void> put(String id, String actor) {
        return runAsync(() -> {
             notificationService.actorStarted(id, actor);
        }, executionContext);
    }

    @Override
    public CompletionStage<Optional<String>> get(String id) {
        return supplyAsync(() -> {
            return Optional.ofNullable(map.get(id));
        }, executionContext);
    }

    @Override
    public CompletionStage<Void> remove(String id) {
        return runAsync(() -> {
            notificationService.actorStopped(id);
        }, executionContext);
    }

}
