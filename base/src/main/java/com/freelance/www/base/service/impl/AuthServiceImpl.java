package com.freelance.www.base.service.impl;

import com.freelance.www.base.service.AuthService;
import com.freelance.www.base.util.AuthorityServerModuleException;
import com.freelance.www.base.util.FLUsernamePasswordToken;
import com.freelance.www.base.vo.AuthorizationInfoVO;
import com.freelance.www.base.vo.HavePermissionVO;
import com.freelance.www.base.vo.LoginResultVO;
import com.freelance.www.base.vo.RestApiResultVO;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


@Service
public class AuthServiceImpl implements AuthService {


    public RestApiResultVO login(HavePermissionVO incoming) {
        FLUsernamePasswordToken token;
        RestApiResultVO result = new RestApiResultVO();
        Subject currentUser = SecurityUtils.getSubject();
        if (StringUtils.isEmpty(incoming.getToken())) {
            String userName = incoming.getUserName().trim();
            token = new FLUsernamePasswordToken(userName, incoming.getPassword());
        } else {
            try {
                token = new FLUsernamePasswordToken(incoming.getToken());
                if (checkExpired(token)) {
                    result.setStatusCode("API-AUTH-ERR-TOKEN-EXPIRED");
                    return result;
                }
            } catch (AuthorityServerModuleException e) {
                result.setStatusCode("API-AUTH-ERR-TOKEN");
                return result;
            }
        }
        try {
            currentUser.login((AuthenticationToken)token);
            PrincipalCollection principals = currentUser.getPrincipals();
            AuthorizationInfoVO userInfo = (AuthorizationInfoVO)principals.getPrimaryPrincipal();
            /*if (userInfo.getLockType() > 0) { TODO 可能需要此业务
                currentUser.logout();
                result.setStatusCode("API-AUTH-ERR-USER-INACTIVE");
                return result;
            }
            */
            LoginResultVO loginResult = new LoginResultVO();
            loginResult.setId(userInfo.getUid());
            loginResult.setUserName(userInfo.getUsername());
            loginResult.setToken(token.encryptToken());
            loginResult.setLevel(userInfo.getLevel());

            if (StringUtils.isEmpty(incoming.getToken())){
                //TODO  未处理未登陆情况
                //this.authExportService.sendLogMessage(incoming.getSysId(), "sys", "login", ", incoming.getUserName(), loginResult);
            }
            result.setSuccess();
            result.setContent(loginResult);
        } catch (IncorrectCredentialsException e) {
            result.setStatusCode("API-AUTH-ERR-ERROR-PASSWORD");
        } catch (AuthenticationException e) {
            result.setStatusCode("API-AUTH-ERR-USER-NOT-EXIST");
        } catch (InvalidSessionException e) {
            result.setStatusCode("API-AUTH-ERR-ERROR-PASSWORD");
        } catch (Exception e) {
            result.setContent(null);
            result.setStatusCode("API-AUTH-ERR-ERROR");
        }
        return result;
    }
    private boolean checkExpired(FLUsernamePasswordToken token) {
        if (!"systemTask".equals(token.getUsername())) {
            //int expiredTime = Integer.parseInt(ConfigManager.getPropertyById("expiredTime").toString());
            int expiredTime = 1;
            Date timeLimit = new Date(token.getTimestamp().getTime() + (expiredTime * 60 * 60) * 1000L);
            if (timeLimit.before(new Date()))
                return true;
        }
        return false;
    }
}
