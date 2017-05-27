package com.nowcoder.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
@Service
public class WendaService {
    public String getMessage(int userId){
        return "Hello Message: " + String.valueOf(userId);
    }
}
