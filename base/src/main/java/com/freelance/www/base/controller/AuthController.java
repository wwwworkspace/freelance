package com.freelance.www.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.freelance.www.base.service.impl.AuthServiceImpl;
import com.freelance.www.base.vo.HavePermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping({"/authCtrl"})
public class AuthController {
    @Autowired
    private AuthServiceImpl authServiceImpl;


    @RequestMapping(value = {"/login"})
    public void login(HttpServletRequest request, HttpServletResponse response, @RequestBody HavePermissionVO incoming) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(this.authServiceImpl.login(incoming)));
    }

}
