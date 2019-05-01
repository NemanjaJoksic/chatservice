/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor.service.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static rs.ac.bg.etf.chatserver.Consts.TOPIC_ACTOR;
import rs.ac.bg.etf.chatserver.actor.message.ActorStarted;
import rs.ac.bg.etf.chatserver.actor.message.ActorStopped;
import rs.ac.bg.etf.chatserver.actor.service.NotificationService;

/**
 *
 * @author joksin
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private ActorSystem actorSystem;

    private ActorRef mediator;
    private ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        mediator = DistributedPubSub.get(actorSystem).mediator();
    }

    @Override
    public void actorStarted(String id, String address) {
        mediator.tell(new DistributedPubSubMediator.Publish(TOPIC_ACTOR,
                new ActorStarted(id, address)), ActorRef.noSender());
    }

    @Override
    public void actorStopped(String id) {
        mediator.tell(new DistributedPubSubMediator.Publish(TOPIC_ACTOR,
                new ActorStopped(id)), ActorRef.noSender());
    }

}
