package com.xiaou.confession.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.confession.entity.SensitiveWord;
import com.xiaou.confession.mapper.SensitiveWordMapper;
import com.xiaou.confession.util.SensitiveWordFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SensitiveWordConfig implements CommandLineRunner {
    
    private final SensitiveWordMapper sensitiveWordMapper;
    private final SensitiveWordFilter sensitiveWordFilter;
    
    @Override
    public void run(String... args) {
        LambdaQueryWrapper<SensitiveWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SensitiveWord::getIsEnabled, 1);
        
        List<SensitiveWord> sensitiveWords = sensitiveWordMapper.selectList(wrapper);
        List<String> words = sensitiveWords.stream()
                .map(SensitiveWord::getWord)
                .collect(Collectors.toList());
        
        sensitiveWordFilter.addSensitiveWords(words);
        
        System.out.println("✅ 敏感词库加载完成，共加载 " + words.size() + " 个敏感词");
    }
}

