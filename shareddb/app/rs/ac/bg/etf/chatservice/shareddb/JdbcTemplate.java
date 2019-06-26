/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatservice.shareddb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.api.db.Database;
import rs.ac.bg.etf.chatservice.shared.exception.ChatServiceException;

/**
 *
 * @author joksin
 */
@Component
public class JdbcTemplate {

    @Autowired
    private Database db;

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws ChatServiceException {
        try {
            ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery();
            if (rs.next()) {
                return rowMapper.mapRow(0, rs);
            } else {
                return null;
            }
        } catch (Exception ex) {
            throw ChatServiceException.generateException(ex);
        }
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... objs) throws ChatServiceException {
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            setParams(ps, objs);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rowMapper.mapRow(0, rs);
            } else {
                return null;
            }
        } catch (Exception ex) {
            throw ChatServiceException.generateException(ex);
        }
    }

    public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) throws ChatServiceException {
        try {
            ResultSet rs = db.getConnection().prepareStatement(sql).executeQuery();
            List<T> retList = new ArrayList<>();
            int i = 0;
            while (rs.next()) {
                retList.add(rowMapper.mapRow(i++, rs));
            }
            return retList;
        } catch (Exception ex) {
            throw ChatServiceException.generateException(ex);
        }
    }

    public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... objs) throws ChatServiceException {
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            setParams(ps, objs);
            ResultSet rs = ps.executeQuery();
            List<T> retList = new ArrayList<>();
            int i = 0;
            while (rs.next()) {
                retList.add(rowMapper.mapRow(i++, rs));
            }
            return retList;
        } catch (Exception ex) {
            throw ChatServiceException.generateException(ex);
        }
    }

    public Integer update(String sql) throws ChatServiceException {
        try {
            return db.getConnection().prepareStatement(sql).executeUpdate();
        } catch (Exception ex) {
            throw ChatServiceException.generateException(ex);
        }
    }

    public Integer update(String sql, Object... objs) throws ChatServiceException {
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            setParams(ps, objs);
            return ps.executeUpdate();
        } catch (Exception ex) {
            throw ChatServiceException.generateException(ex);
        }
    }

    private void setParams(PreparedStatement ps, Object... objs) throws SQLException {
        for (int i = 0; i < objs.length; i++) {
            setNullable(ps, i + 1, objs[i]);
        }
    }

    private void setNullable(PreparedStatement ps, int index, Object data) throws SQLException {
        if (data == null) {
            ps.setObject(index, null);
        } else if (data instanceof String) {
            String string = (String) data;
            ps.setString(index, string);
        } else if (data instanceof Double) {
            Double digit = (Double) data;
            ps.setDouble(index, digit);
        } else if (data instanceof LocalDateTime) {
            LocalDateTime date = (LocalDateTime) data;
            ps.setTimestamp(index, Timestamp.valueOf(date));
        } else if (data instanceof Boolean) {
            Boolean bool = (Boolean) data;
            ps.setBoolean(index, bool);
        } else if (data instanceof Integer) {
            Integer digit = (Integer) data;
            ps.setInt(index, digit);
        } else if (data instanceof Long) {
            Long digit = (Long) data;
            ps.setLong(index, digit);
        } else {
            throw new RuntimeException("Type [ " + data.getClass().getName() + " ] is not supported in mapper");
        }
    }

}
