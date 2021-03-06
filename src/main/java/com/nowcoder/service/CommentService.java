package com.nowcoder.service;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by missinghigh on 2017/6/11.
 */
@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    @Autowired
    SensitiveService sensitiveService;

    public List<Comment> getCommentsByEntity(int entityId, int entityType){
        return commentDAO.selectComment(entityId, entityType);
    }

    public int addComment(Comment comment){
        comment.setContent(sensitiveService.filter(comment.getContent()));
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        return commentDAO.addComment(comment) > 0 ? comment.getId() : 0;
    }

    public int getCommentCount(int entityId, int entityType){
        return commentDAO.getCommentCount(entityId, entityType);
    }

    public boolean updateStatus(int id){
        return commentDAO.updateStatus(id, 1) > 0;
    }

    public Comment getCommentById(int id){
        return commentDAO.getCommentById(id);
    }

    public int getUserCommentCount(int userId){
        return commentDAO.getUserCommentCount(userId);
    }
}
