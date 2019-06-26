/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.service;

import akka.actor.ActorSystem;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import rs.ac.bg.etf.chatservice.chatmanager.dao.UserDao;
import rs.ac.bg.etf.chatservice.chatmanager.executor.ServiceExecutionContext;
import rs.ac.bg.etf.chatservice.chatmanager.model.Register;
import rs.ac.bg.etf.chatservice.security.model.user.User;
import rs.ac.bg.etf.chatservice.security.model.user.UserDetails;
import rs.ac.bg.etf.chatservice.security.service.UserDetailsService;

/**
 *
 * @author joksin
 */
@Service
@Primary
public class UserService implements UserDetailsService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private UserDao userDao;
    
    private ServiceExecutionContext executionContext;
    
    @PostConstruct
    public void init() {
        executionContext = new ServiceExecutionContext(actorSystem);
    }
    
    public CompletionStage<Register> register(User user) {
        return CompletableFuture.supplyAsync(() -> {
            userDao.createUser(user);
            logger.info("New user registered [username -> {}]", user.getUsername());
            return new Register(user.getUsername(), Register.SUCCESS);
        }, executionContext);
    }

    @Override
    public Optional<UserDetails> getUserByUsername(String username) {
        return Optional.ofNullable(userDao.getUser(username));
    }
    
}
