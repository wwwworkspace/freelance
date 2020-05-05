package com.freelance.www.base.vo;

import lombok.Data;

@Data
public class LoginResultVO {
    private String id;

    private String userName;

    private String token;

    private String sysId;

    private int level;

    private Object session;
}
