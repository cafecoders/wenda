package com.nowcoder.service;

import com.nowcoder.dao.FeedDAO;
import com.nowcoder.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 */
@Service
public class FeedService {
    @Autowired
    FeedDAO feedDAO;

    public int addFeed(Feed feed){
        return feedDAO.addFeed(feed);
    }

    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count){
        return feedDAO.getUserFeeds(maxId, userIds, count);
    }

    public Feed getById(int id){
        return feedDAO.getById(id);
    }
}
