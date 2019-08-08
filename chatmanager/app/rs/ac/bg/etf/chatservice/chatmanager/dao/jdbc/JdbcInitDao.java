/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.chatmanager.dao.jdbc;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.chatservice.data.jdbc.AbstractJdbcDao;

/**
 *
 * @author joksin
 */
@Repository
@ConditionalOnExpression("${app.db.init.enabled}")
public class JdbcInitDao extends AbstractJdbcDao {
    
    @PostConstruct
    public void init() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("data.sql");
        String sqlScript = IOUtils.toString(is, "UTF-8");
        this.jdbcTemplate.update(sqlScript);
    }
    
}
