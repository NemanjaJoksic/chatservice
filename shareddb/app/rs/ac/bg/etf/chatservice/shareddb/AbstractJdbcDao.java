/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.shareddb;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author joksin
 */
public abstract class AbstractJdbcDao {
    
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    
}
