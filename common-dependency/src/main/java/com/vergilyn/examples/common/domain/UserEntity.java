package com.vergilyn.examples.common.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/16
 */
@Data
@ToString
public class UserEntity {
    private Long id;
    private String username;
}
