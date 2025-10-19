package com.xiaou.confession.util;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SensitiveWordFilter {
    
    private final Map<String, Map<String, Object>> sensitiveWordMap = new HashMap<>();
    
    private static final String END_FLAG = "isEnd";
    
    public void addSensitiveWords(List<String> words) {
        for (String word : words) {
            addWord(word);
        }
    }
    
    private void addWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            return;
        }
        
        Map<String, Object> currentMap = sensitiveWordMap;
        
        for (int i = 0; i < word.length(); i++) {
            String c = String.valueOf(word.charAt(i));
            
            @SuppressWarnings("unchecked")
            Map<String, Object> nextMap = (Map<String, Object>) currentMap.get(c);
            
            if (nextMap == null) {
                nextMap = new HashMap<>();
                currentMap.put(c, nextMap);
            }
            
            currentMap = nextMap;
            
            if (i == word.length() - 1) {
                currentMap.put(END_FLAG, true);
            }
        }
    }
    
    public boolean containsSensitiveWord(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < text.length(); i++) {
            int length = checkSensitiveWord(text, i);
            if (length > 0) {
                return true;
            }
        }
        return false;
    }
    
    public String filterSensitiveWord(String text, String replaceChar) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }
        
        StringBuilder result = new StringBuilder(text);
        
        for (int i = 0; i < result.length(); i++) {
            int length = checkSensitiveWord(result.toString(), i);
            if (length > 0) {
                for (int j = 0; j < length; j++) {
                    result.setCharAt(i + j, replaceChar.charAt(0));
                }
            }
        }
        
        return result.toString();
    }
    
    public List<String> getSensitiveWords(String text) {
        List<String> words = new ArrayList<>();
        if (text == null || text.trim().isEmpty()) {
            return words;
        }
        
        for (int i = 0; i < text.length(); i++) {
            int length = checkSensitiveWord(text, i);
            if (length > 0) {
                String word = text.substring(i, i + length);
                words.add(word);
            }
        }
        
        return words;
    }
    
    private int checkSensitiveWord(String text, int beginIndex) {
        boolean flag = false;
        int matchLength = 0;
        
        Map<String, Object> currentMap = sensitiveWordMap;
        
        for (int i = beginIndex; i < text.length(); i++) {
            String c = String.valueOf(text.charAt(i));
            
            @SuppressWarnings("unchecked")
            Map<String, Object> nextMap = (Map<String, Object>) currentMap.get(c);
            
            if (nextMap != null) {
                matchLength++;
                
                if (nextMap.get(END_FLAG) != null && (Boolean) nextMap.get(END_FLAG)) {
                    flag = true;
                }
                
                currentMap = nextMap;
            } else {
                break;
            }
        }
        
        if (!flag) {
            matchLength = 0;
        }
        
        return matchLength;
    }
}

