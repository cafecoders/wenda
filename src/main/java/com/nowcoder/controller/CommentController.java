package com.nowcoder.controller;

import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventProducer;
import com.nowcoder.async.EventType;
import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by missinghigh on 2017/6/11.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                          @RequestParam("content") String content){
        Comment comment = new Comment();
        comment.setContent(content);
        if(hostHolder.getUser() != null){
            comment.setUserId(hostHolder.getUser().getId());
        }else{
            return "redirect:/reglogin";
        }
        comment.setCreatedDate(new Date());
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        comment.setEntityId(questionId);


        try{
            commentService.addComment(comment);
        }catch(Exception e){
            logger.error("增加评论失败！" + e.getMessage());
        }

        int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
        questionService.updateCommentCount(count, comment.getEntityId());

        eventProducer.fireEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId())
                .setEntityId(questionId));

        return "redirect:/question/" + questionId;
    }

}
