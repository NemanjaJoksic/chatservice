/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security.model.authentication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 *
 * @author joksin
 */
@JsonDeserialize(as = SimpleAuthority.class)
public interface Authority {

    public String getAuthority();

}
