package com.freelance.www.base.vo;

import lombok.Data;

import java.util.List;

@Data
public class HavePermissionVO {
    private String userName;//用户名
    private String password;//密码
    private String newPassword;//新密码
    private String token;
    private List<String> permissionId;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HavePermissionVO))
            return false;
        HavePermissionVO other = (HavePermissionVO) o;
        Object this$userName = getUserName(), other$userName = other.getUserName();
        if ((this$userName == null) ? (other$userName != null) : !this$userName.equals(other$userName))
            return false;
        Object this$password = getPassword(), other$password = other.getPassword();
        if ((this$password == null) ? (other$password != null) : !this$password.equals(other$password))
            return false;
        Object this$newPassword = getNewPassword(), other$newPassword = other.getNewPassword();
        if ((this$newPassword == null) ? (other$newPassword != null) : !this$newPassword.equals(other$newPassword))
            return false;
        Object this$token = getToken(), other$token = other.getToken();
        return  !((this$token == null) ? (other$token != null) : !this$token.equals(other$token));
    }
}
