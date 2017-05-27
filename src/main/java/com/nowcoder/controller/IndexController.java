package com.nowcoder.controller;

import com.nowcoder.aspect.LogAspect;
import com.nowcoder.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
//@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Autowired
    WendaService wendaSerice;

    @RequestMapping(path = {"/","/index"})
    @ResponseBody
    public String index(){
        logger.info("VISIT HOME");
        return "Hello " + wendaSerice.getMessage(1);
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"}, method = {RequestMethod.GET})
    @ResponseBody
    public String profile(
            @PathVariable("groupId") String groupId,
            @PathVariable("userId") int userId,
            @RequestParam(value = "type", defaultValue = "0") int type,
            @RequestParam(value = "key", defaultValue = "null") String key){
        return String.format("group: %s, user: %d, type: %d, key: %s", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/vm"}, method = {RequestMethod.GET})
        public String template(Model model){
            model.addAttribute("value1", "123");
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for(int i = 0; i < 4; i++){
                map.put(i,i*i);
            }
            model.addAttribute("map", map);
            return "vm";
        }
}
