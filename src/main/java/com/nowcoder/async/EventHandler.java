package com.nowcoder.async;

import java.util.List;

/**
 * Created by missinghigh on 2017/6/27.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
