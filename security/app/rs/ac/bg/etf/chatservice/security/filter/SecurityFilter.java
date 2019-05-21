/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.filter;

import akka.stream.Materializer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import play.mvc.Http;
import play.mvc.Result;
import rs.ac.bg.etf.chatservice.security.context.SecurityContext;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
import rs.ac.bg.etf.chatservice.security.provider.AuthenticationProviderManager;
import rs.ac.bg.etf.chatservice.security.provider.AuthorizationProviderManager;
import rs.ac.bg.etf.chatservice.shared.exception.ExceptionToResultConverter;

/**
 *
 * @author joksin
 */
@DependsOn({"authenticationProviderManager"})
public class SecurityFilter extends play.mvc.Filter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    
    @Autowired
    private AuthenticationProviderManager authenticationProviderManager;

    @Autowired
    private AuthorizationProviderManager authorizationProviderManager;

    public SecurityFilter(Materializer mtrlzr) {
        super(mtrlzr);
    }

    @Override
    public CompletionStage<Result> apply(Function<Http.RequestHeader, CompletionStage<Result>> nextFilter, Http.RequestHeader requestHeader) {

        logger.info("Setting security context");

        try {
            Authentication authentication = authenticationProviderManager.authenticate(requestHeader);
            authentication = authorizationProviderManager.authorize(authentication);
            SecurityContext.setAuthentication(authentication);
            return nextFilter.apply(requestHeader);
        } catch (Exception ex) {
            return CompletableFuture.completedFuture(
                    ExceptionToResultConverter.convert(ex)
            );
        }
    }

}
