/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import play.mvc.Http;
import play.mvc.Result;
import rs.ac.bg.etf.chatservice.commons.exception.ExceptionToResultConverter;
import rs.ac.bg.etf.chatservice.security.context.SecurityContext;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
import rs.ac.bg.etf.chatservice.security.provider.AuthenticationProviderManager;
import rs.ac.bg.etf.chatservice.security.provider.AuthorizationProviderManager;

/**
 *
 * @author joksin
 */
public class SecureAction extends play.mvc.Action.Simple {

    private static final Logger logger = LoggerFactory.getLogger(SecureAction.class);
    
    @Autowired
    private AuthenticationProviderManager authenticationProviderManager;

    @Autowired
    private AuthorizationProviderManager authorizationProviderManager;
    
    @Override
    public CompletionStage<Result> call(Http.Context context) {
        logger.info("Setting security context");

        try {
            Authentication authentication = authenticationProviderManager.authenticate(context.request().getHeaders().get("Authorization"));
            authentication = authorizationProviderManager.authorize(authentication);
            SecurityContext.setAuthentication(authentication);
            return delegate.call(context);
        } catch (Exception ex) {
            return CompletableFuture.completedFuture(
                    ExceptionToResultConverter.convert(ex)
            );
        }
        
    }
    
}
