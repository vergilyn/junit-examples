package com.vergilyn.examples.junit.domain;

/**
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/16
 */
public class TestUser {
    private Long id;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "TestUser{" + "id=" + id + ", username='" + username + '\'' + '}';
    }
}
