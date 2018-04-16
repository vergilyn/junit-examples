package com.vergilyn.examples.junit.service;

import java.util.List;

import com.vergilyn.examples.junit.domain.TestUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/16
 */
@Service
public class TestService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int save(String username){
        return jdbcTemplate.update("insert into test_user(username) values(?) ", username);
    }

    public int delete(String username){
        return jdbcTemplate.update("delete from test_user where username = ?", username);
    }

    public List<TestUser> get(String username){
        return jdbcTemplate.query("select * from test_user where username = ?", new Object[]{username}, BeanPropertyRowMapper.newInstance(TestUser.class));
    }
}
