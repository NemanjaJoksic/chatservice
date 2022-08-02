/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.security;

import play.mvc.Controller;
import play.mvc.With;

/**
 *
 * @author joksin
 */
@With(SecureAction.class)
public abstract class SecureController extends Controller {
    
}
