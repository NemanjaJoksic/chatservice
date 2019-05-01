/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.cluster.Cluster;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import static rs.ac.bg.etf.chatserver.Consts.TOPIC_ACTOR;
import rs.ac.bg.etf.chatserver.actor.SubscriberActor;
import rs.ac.bg.etf.chatserver.actor.DeadLetterActor;
import rs.ac.bg.etf.chatserver.actor.storage.MessageHandlerActorStorage;

/**
 *
 * @author joksin
 */
@ComponentScan
@Configuration
@PropertySource("classpath:application.conf")
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);
    
    @Autowired
    private ActorSystem actorSystem;
    
    @Autowired
    private MessageHandlerActorStorage actorStorage;

    @PostConstruct
    public void init() {
        joinSeedNodes();
        createDeadLetterActor();
        createSubscriberActor();
    }
    
    private void createDeadLetterActor() {
        ActorRef deadLetterActor = actorSystem.actorOf(DeadLetterActor.props(actorStorage));
        actorSystem.eventStream().subscribe(deadLetterActor, DeadLetter.class);
        logger.info("DeadLetterActor created");
    }

    private void joinSeedNodes() {
        Cluster cluster = Cluster.get(actorSystem);
//        cluster.joinSeedNodes(Arrays.asList(actorSystem.provider().getDefaultAddress()));
    }

    private void createSubscriberActor() {
        ActorRef subscriber = actorSystem.actorOf(SubscriberActor.props(actorStorage));
        ActorRef mediator = DistributedPubSub.get(actorSystem).mediator();
        mediator.tell(new DistributedPubSubMediator.Subscribe(TOPIC_ACTOR, subscriber),
                subscriber);
        logger.info("Subscriber created");
        
    }
}
