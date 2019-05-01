/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.actor.storage.impl;

import akka.actor.ActorSystem;
import java.util.Map;
import java.util.Optional;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import static rs.ac.bg.etf.chatserver.Consts.IN_MEMORY_ACTOR_STORAGE;
import rs.ac.bg.etf.chatserver.actor.storage.MessageHandlerActorStorage;
import rs.ac.bg.etf.chatserver.executor.StorageExecutionContext;

/**
 *
 * @author joksin
 */
@Component
@ConditionalOnProperty(name = "app.akka.actor.storage.type", havingValue = IN_MEMORY_ACTOR_STORAGE)
public class InMemoryConnectionHolderActorStorage implements MessageHandlerActorStorage {

    @Autowired
    private ActorSystem actorSystem;

    private Map<String, String> map;
    private StorageExecutionContext executionContext;

    @PostConstruct
    public void init() {
        this.map = new ConcurrentHashMap<>();
        this.executionContext = new StorageExecutionContext(actorSystem);
    }

    @Override
    public CompletionStage<Void> put(String id, String actor) {
        return runAsync(() -> {
            map.put(id, actor);
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
            map.remove(id);
        }, executionContext);
    }

}
