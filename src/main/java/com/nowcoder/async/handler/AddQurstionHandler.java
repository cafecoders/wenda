package com.nowcoder.async.handler;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

import com.nowcoder.async.EventHandler;
import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventProducer;
import com.nowcoder.async.EventType;
import com.nowcoder.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AddQurstionHandler implements EventHandler {
    @Autowired
    SearchService searchService;

    @Override
    public void doHandle(EventModel model) {
        try{
            searchService.indexQuestion(model.getEntityId(), model.getExt("question_title"), model.getExt("question_content"));
        }catch(Exception e){
            e.getMessage();
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
