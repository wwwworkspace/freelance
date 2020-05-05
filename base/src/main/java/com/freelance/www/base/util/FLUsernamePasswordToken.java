package com.freelance.www.base.util;

import com.freelance.www.base.vo.FLPrincipalVO;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FLUsernamePasswordToken extends UsernamePasswordToken {
    private Date timestamp;

    public FLUsernamePasswordToken() {}

    public FLUsernamePasswordToken(String token) {
        try {
            EncryptUtil des = new EncryptUtil("9ba45bfd500642328ec03ad8ef1b6e75");
            String decryptData = des.decode(token);
            String[] incomingInfo = decryptData.split(",")[0].split("\\|-\\|");
            setUsername(incomingInfo[0]);
            setPassword(incomingInfo[1].toCharArray());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            setTimestamp(df.parse(decryptData.split(",")[2]));
        } catch (Exception e) {
            //throw new AuthorityServerModuleException(e);
        }
    }

    public FLUsernamePasswordToken(String username, char[] password) {
        this(username, password, null, false, null);
    }

    public FLUsernamePasswordToken(String username, String password) {
        this(username, (password != null) ? password.toCharArray() : null, null, false, null);
    }

    public FLUsernamePasswordToken(String username, String password, String sysId) {
        this(username, (password != null) ? password.toCharArray() : null, sysId, false, null);
    }

    public FLUsernamePasswordToken(String username, char[] password, String sysId, String host) {
        this(username, password, sysId, false, host);
    }

    public FLUsernamePasswordToken(String username, char[] password, String sysId, boolean rememberMe) {
        this(username, password, sysId, rememberMe, null);
    }

    public FLUsernamePasswordToken(String username, String password, String sysId, boolean rememberMe) {
        this(username, (password != null) ? password.toCharArray() : null, sysId, rememberMe, null);
    }

    public FLUsernamePasswordToken(String username, char[] password, String sysId, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.timestamp = new Date();
    }

    public FLUsernamePasswordToken(String username, String password, String sysId, boolean rememberMe, String host) {
        this(username, (password != null) ? password.toCharArray() : null, sysId, rememberMe, host);
    }


    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public Object getPrincipal() {
        return new FLPrincipalVO(getUsername());
    }
    @Override
    public void clear() {
        super.clear();
        this.timestamp = null;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUsername());
        sb.append("|-|");
        sb.append(getPassword());
        sb.append(", rememberMe=").append(isRememberMe());
        if (getHost() != null)
            sb.append(" (").append(getHost()).append(")");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sb.append(", " + ((this.timestamp == null) ? df.format(new Date()) : df.format(this.timestamp)));
        return sb.toString();
    }

    public String encryptToken() {
        try {
            EncryptUtil des = new EncryptUtil("9ba45bfd500642328ec03ad8ef1b6e75");
            String result = des.encode(toString());
            return FLStringUtils.replcaceCRLF(result);
        } catch (Exception e) {
            e.printStackTrace();
            String result = null;
            return result;
        }
    }
}
