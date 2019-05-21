/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.shared.exception;

import com.typesafe.config.Config;
import play.*;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.mvc.Http.*;
import play.mvc.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author joksin
 */
//@Component
public class ErrorHandler extends DefaultHttpErrorHandler {

//    @Autowired
    public ErrorHandler(Config config, Environment environment,
                        OptionalSourceMapper sourceMapper, Provider<Router> routes) {
        super(config, environment, sourceMapper, routes);
    }

    @Override
    public CompletionStage<Result> onServerError(RequestHeader rh, Throwable t) {
        return CompletableFuture.completedFuture(
                ExceptionToResultConverter.convert(t)
        );
    }
    
}
