package com.nowcoder.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by missinghigh on 2017/6/1.
 */
@Service
public class SensitiveService implements InitializingBean{
    private final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(read);
            String lineTxt;
            while((lineTxt = bufferReader.readLine()) != null){
                addWord(lineTxt.trim());
            }
            bufferReader.close();
            read.close();
        }catch(Exception e){
            logger.info("读取敏感词失败！" + e.getMessage());
        }
    }

    private  TireNode rootNode = new TireNode();

    public void addWord(String lineTxt){
        TireNode tmpNode = rootNode;

        for(int i = 0; i < lineTxt.length(); ++i){
            Character c = lineTxt.charAt(i);
            TireNode node = rootNode.getSubNodes(c);
            if(node == null){
                node = new TireNode();
                tmpNode.setSubNodes(c, node);
            }
            tmpNode = node;

            if(i == lineTxt.length() - 1 )
                tmpNode.setEnd(true);
        }

    }

    public boolean isSymbol(char c){
        int ic = (int) c;
        return !CharUtils.isAsciiAlphanumeric(c) && ic < 0x2E80 || ic > 0x9FFF;
    }

    public class TireNode{
       // char val;
       private  boolean end = false;
       private  Map<Character, TireNode> subNodes = new HashMap<Character, TireNode>();

        public void setSubNodes(Character key, TireNode node){
            subNodes.put(key, node);
        }

        public TireNode getSubNodes(Character key){
            return subNodes.get(key);
        }

        public boolean isWordEnd(){
           return end;
        }

        public void setEnd(boolean end){
            this.end = end;
        }
    }

    public String filter(String lineTxt){
        if(StringUtils.isBlank(lineTxt)){
            return lineTxt;
        }
        int begin = 0;
        int position = 0;
        TireNode tmpNode = rootNode;
        String replacement = "**";
        StringBuilder sb = new StringBuilder();

        while(position < lineTxt.length()){
            char c = lineTxt.charAt(position);
            if(isSymbol(c)){
                position++;
               continue;
            }
            tmpNode = tmpNode.getSubNodes(c);
            if(tmpNode == null){
                sb.append(lineTxt.charAt(begin));
                position = begin + 1;
                begin = position;
                tmpNode = rootNode;
            }else if(tmpNode.isWordEnd()){
                sb.append(replacement);
                position = position + 1;
                begin = position;
                tmpNode = rootNode;
            }else{
                position++;
            }
        }
        sb.append(lineTxt.substring(begin));
        return sb.toString();

    }

   /* public static void main(String[] args){
        SensitiveService s = new SensitiveService();
        s.addWord("习近平");
        s.addWord("中共");
        System.out.print(s.filter("   w  w习`ω´近 平在这次活动中共捐款5000元"));
    }*/
}
