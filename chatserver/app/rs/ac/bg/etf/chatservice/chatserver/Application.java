/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.cluster.Cluster;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import rs.ac.bg.etf.chatservice.chatserver.actor.DeadLetterActor;
import rs.ac.bg.etf.chatservice.chatserver.actor.storage.MessageHandlerActorStorage;

/**
 *
 * @author joksin
 */
@ComponentScan
//@ComponentScan(basePackages = "rs.ac.bg.etf.chatservice")
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
    
}
