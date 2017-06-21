package com.nowcoder.controller;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.model.*;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.LikeService;
import com.nowcoder.service.QuestionService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.WendaUtil;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by missinghigh on 2017/6/1.
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final static int ANONYMOUS_ID = 3;
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content){
        try{
            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            if(hostHolder == null){
                question.setUserId(ANONYMOUS_ID);
            }else{
                question.setUserId((hostHolder.getUser().getId()));
            }

            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            if(questionService.addQuestion(question) > 0){
                return WendaUtil.getJSONString(0);
            }
        }catch (Exception e){
            logger.info("增加题目失败！" + e.getMessage());
        }

        return WendaUtil.getJSONString(1, "failed!");
    }

    @RequestMapping(path = {"/question/{qid}"}, method = {RequestMethod.GET})
    public String questionDetail(Model model,@PathVariable("qid") int qid){
        Question question = questionService.getQusetion(qid);
       // User user = userService.getUser(question.getUserId());
        model.addAttribute("question", question);
       // model.addAttribute("user", user);

        List<Comment> commentList = commentService.getCommentByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<ViewObject>();

        for(Comment comment : commentList){
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            vo.set("user", userService.getUser(comment.getUserId()));
            if(hostHolder.getUser() == null){
                vo.set("liked", 0);
            }else{
                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }
            vo.set("likeCount", likeService.getLikeCount( EntityType.ENTITY_COMMENT, comment.getId()));
            comments.add(vo);
        }

        model.addAttribute("comments", comments);

        return "detail";
    }

}
