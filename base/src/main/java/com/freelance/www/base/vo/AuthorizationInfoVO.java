package com.freelance.www.base.vo;

import lombok.Data;

@Data
public class AuthorizationInfoVO {
    private String uid;

    private String username;

    private String password;

    private String salt;

    private int lockType;

    private int level;

    private String limitDate;

    //private List<RoleVo> roleList; TODO 时间关系未完成


}
