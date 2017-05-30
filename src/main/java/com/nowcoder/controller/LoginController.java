package com.nowcoder.controller;

import com.nowcoder.service.UserService;
import org.apache.zookeeper.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/29 0029.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/reg"}, method = {RequestMethod.POST})
    public String register(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           HttpServletResponse response){
        Map<String, String> map = userService.register(username, password);
        try {
            if (map.containsKey("ticket")) {
               Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        }catch(Exception e){
            logger.error("注册异常" + e.getMessage());
            return "login";
        }

    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.POST})
    public String login(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           HttpServletResponse response){
        Map<String, String> map = userService.login(username, password);
        try {
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        }catch(Exception e){
            logger.error("登录异常" + e.getMessage());
            return "login";
        }

    }

    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String reg(Model model){
        return "login";
    }
}
