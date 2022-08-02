/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.data.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author joksin
 */
public interface RowMapper<T> {
    
    public T mapRow(int index, ResultSet rs) throws SQLException;
    
}
