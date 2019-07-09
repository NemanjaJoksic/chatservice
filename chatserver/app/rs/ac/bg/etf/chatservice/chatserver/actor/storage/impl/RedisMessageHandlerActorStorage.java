/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatserver.actor.storage.impl;

import akka.actor.ActorSystem;
import java.util.Optional;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import rs.ac.bg.etf.chatservice.chatserver.Config;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.ACTOR_STORAGE_TYPE;
import static rs.ac.bg.etf.chatservice.chatserver.Consts.REDIS_ACTOR_STORAGE;
import rs.ac.bg.etf.chatservice.chatserver.actor.storage.MessageHandlerActorStorage;
import rs.ac.bg.etf.chatservice.chatserver.executor.StorageExecutionContext;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;

/**
 *
 * @author joksin
 */
@Component
@ConditionalOnProperty(name = ACTOR_STORAGE_TYPE, havingValue = REDIS_ACTOR_STORAGE)
public class RedisMessageHandlerActorStorage implements MessageHandlerActorStorage {

    private static final String HASH_SET_NAME = "actors";

    private final static Logger logger = LoggerFactory.getLogger(RedisMessageHandlerActorStorage.class);

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private Config config;

    private JedisPool pool;
    private StorageExecutionContext executionContext;

    @PostConstruct
    public void init() {
        this.executionContext = new StorageExecutionContext(actorSystem);

        String host = config.getRedisHost();
        Integer port = config.getRedisPort();
        logger.info("Redis connection parameters (host,port) -> ({},{})", host, port);
        pool = new JedisPool(new JedisPoolConfig(), host, port);
    }

    @Override
    public CompletionStage<Void> put(String id, String actor) {
        return runAsync(() -> {
            try (Jedis jedis = pool.getResource()) {
                jedis.hset(HASH_SET_NAME, id, actor);
                logger.info("Actor [{}] with address [{}] are added to storage", id, actor);
            } catch (JedisConnectionException ex) {
                throw ChatServiceException.generateException(ex);
            }
        }, executionContext);
    }

    @Override
    public CompletionStage<Optional<String>> get(String id) {
        return supplyAsync(() -> {
            try (Jedis jedis = pool.getResource()) {
                return Optional.ofNullable(jedis.hget(HASH_SET_NAME, id));
            } catch (JedisConnectionException ex) {
                throw ChatServiceException.generateException(ex);
            }
        }, executionContext);
    }

    @Override
    public CompletionStage<Void> remove(String id) {
        return runAsync(() -> {
            try (Jedis jedis = pool.getResource()) {
                jedis.hdel(HASH_SET_NAME, id);
                logger.info("Actor [{}] are removed from storage", id);
            } catch (JedisConnectionException ex) {
                throw ChatServiceException.generateException(ex);
            }
        }, executionContext);
    }

}
