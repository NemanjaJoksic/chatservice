/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import play.mvc.Http;
import rs.ac.bg.etf.chatservice.security.exception.AccessForbiddenException;
import rs.ac.bg.etf.chatservice.security.exception.AuthenticationException;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authentication;
import rs.ac.bg.etf.chatservice.security.model.authentication.Authority;
import rs.ac.bg.etf.chatservice.security.model.authentication.SimpleAuthority;

/**
 *
 * @author joksin
 */
@Service
public class RoleBasedAuthorizationProvider implements AuthorizationProvider {

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Authorization {
        
        Pattern pattern;
        String path;
        String method;
        List<Authority> authorities = new LinkedList<>();
        List<String> roles = new LinkedList<>();

        public Authorization() {}
        
        public Authorization(Pattern pattern, String method, List<Authority> authorities) {
            this.pattern = pattern;
            this.method = method;
            this.authorities = authorities;
        }
        
    }
    
    private static final Logger logger = LoggerFactory.getLogger(RoleBasedAuthorizationProvider.class);
    
    private final List<Authorization> authorizations = new LinkedList<>();
    
    @PostConstruct
    public void init() throws Exception {
        logger.info("RoleBasedAuthorizationService is configured");
        
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("security.json");
        if (url == null) {
            logger.info("security.json file not found on classpath. Authorization is disabled");
            return;
        }

        logger.info("Loading authorization setup from security.json");

        InputStream is = classLoader.getResourceAsStream("security.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Authorization> authList = mapper.readValue(is, new TypeReference<List<Authorization>>() {});

        if (authList.isEmpty()) {
            logger.info("security.json file is empty or missing. No specific authorization restrictions are set");
            return;
        }
        
        for(Authorization authorization : authList) {
            Pattern pattern = Pattern.compile(authorization.path.replaceAll("\\*\\*", "\\.\\*"));
            authorization.pattern = pattern;
            setAuthorities(authorization);
            authorizations.add(authorization);
        }
        
    }
    
    private void setAuthorities(Authorization authorization) {
        List<Authority> authorities = new LinkedList<>();
        for(String role : authorization.roles)
            authorities.add(new SimpleAuthority(role));
        authorization.authorities = authorities;
    }
    
    @Override
    public Authentication authorize(Authentication authentication) throws AuthenticationException {
        String url = Http.Context.current().request().path();
        String method = Http.Context.current().request().method();
        logger.debug("Checking authorization {} {}", method, url);
        for(Authorization authorization : authorizations) {
            Matcher matcher = authorization.pattern.matcher(url);
            if(matcher.matches() && method.equals(authorization.method)) {
                if(!CollectionUtils.containsAny(authentication.getAuthorities(), authorization.authorities))
                    throw new AccessForbiddenException();
            }
        }
        return authentication;
    }
    
}
