/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.shareddb.exception;

import rs.ac.bg.etf.chatservice.shared.exception.GeneralException;

/**
 *
 * @author joksin
 */
public class DbException extends GeneralException {
    
    public DbException(Throwable t) {
        super(t);
    }
    
}
