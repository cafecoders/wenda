package com.nowcoder.controller;

import com.nowcoder.aspect.LogAspect;
import com.nowcoder.model.*;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.FollowService;
import com.nowcoder.service.QuestionService;
import com.nowcoder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FollowService followService;
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;


    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET})
    public String userIndex(Model model,@PathVariable("userId") int userId){
        List<ViewObject> vos = getQuestions(userId, 0, 10);
        model.addAttribute("vos", vos);

        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        vo.set("commentCount", commentService.getUserCommentCount(userId));
        if(hostHolder.getUser() != null){
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
        }else{
            vo.set("followed", false);
        }
        model.addAttribute("profileUser", vo);
        return "profile";
    }

/*
    @RequestMapping(path = {"/question/{qid}"}, method = {RequestMethod.GET})
    public String questionDetail(Model model,@PathVariable("qid") int qid){
        Question question = questionService.getQusetion(qid);
        User user = userService.getUser(question.getUserId());
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        return "detail";
    }
*/

    @RequestMapping(path = {"/"}, method = {RequestMethod.GET})
    public String home(Model model){
        List<ViewObject> vos = getQuestions(0, 0, 10);
        model.addAttribute("vos", vos);
        return "index";
    }

    private List<ViewObject> getQuestions(int userId, int offset, int limit){
        List<Question> questionList =  questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for(Question question : questionList){
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }

        return vos;
    }

  /*  @RequestMapping(path = {"/reg"}, method = {RequestMethod.POST})
    public String register(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password){
        Map<String, String> map = userService.register(username, password);
        try {
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
            return "redirect:/";
        }catch(Exception e){
            logger.error("注册异常" + e.getMessage());
            return "login";
        }

    }

    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String reg(Model model){
        return "login";
    }*/



}
