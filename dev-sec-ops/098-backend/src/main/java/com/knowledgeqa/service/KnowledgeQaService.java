package com.knowledgeqa.service;

import com.knowledgeqa.common.BusinessException;
import com.knowledgeqa.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class KnowledgeQaService {

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private CrudService crudService;

    public void publishDocument(Long id) {
        crudService.updateField("document", id, "status", 1);
    }

    public void archiveDocument(Long id) {
        crudService.updateField("document", id, "status", 2);
    }

    public void indexChunk(Long id) {
        crudService.updateField("chunk", id, "vectorStatus", 1);
    }

    public void closeSession(Long id) {
        crudService.updateField("session", id, "status", 1);
        crudService.updateField("session", id, "closeTime", LocalDateTime.now());
    }

    public Map<String, Object> ask(Long userId, Long sessionId, String question) {
        if (sessionId == null || !StringUtils.hasText(question)) {
            throw new BusinessException(400, "会话和问题不能为空");
        }
        Map<String, Object> session = commonMapper.findSessionById(sessionId);
        if (session == null) {
            throw new BusinessException(400, "问答会话不存在");
        }
        Long spaceId = Long.valueOf(String.valueOf(session.get("spaceId")));
        List<Map<String, Object>> chunks = commonMapper.findIndexedChunksBySpace(spaceId);
        if (chunks.isEmpty()) {
            throw new BusinessException(400, "当前空间暂无已索引分段");
        }
        String sourceIds = chunks.stream().map(item -> String.valueOf(item.get("id"))).collect(Collectors.joining(","));
        String answer = buildAnswer(question, chunks);
        BigDecimal confidence = new BigDecimal(82 + Math.abs(question.hashCode()) % 15);
        Map<String, Object> data = new HashMap<>();
        data.put("sessionId", sessionId);
        data.put("question", question);
        data.put("answer", answer);
        data.put("sourceChunkIds", sourceIds);
        data.put("confidence", confidence);
        data.put("resolved", 0);
        data.put("status", 1);
        data.put("createTime", LocalDateTime.now());
        data.put("updateTime", LocalDateTime.now());
        commonMapper.insert("qa_record", data);
        return data;
    }

    public void resolve(Long id, Integer resolved) {
        crudService.updateField("record", id, "resolved", resolved == null ? 1 : resolved);
    }

    public void replyFeedback(Long id, Integer status, String replyContent) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("status", status == null ? 1 : status);
        data.put("replyContent", replyContent);
        data.put("updateTime", LocalDateTime.now());
        commonMapper.update("qa_feedback", data);
    }

    public Map<String, Object> dashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("spaceCount", crudService.count("space"));
        result.put("documentCount", crudService.count("document"));
        result.put("chunkCount", crudService.count("chunk"));
        result.put("sessionCount", crudService.count("session"));
        result.put("recordCount", crudService.count("record"));
        result.put("pendingFeedback", crudService.countWhere("feedback", "status", 0));
        result.put("publishedDocuments", crudService.countWhere("document", "status", 1));
        result.put("indexedChunks", crudService.countWhere("chunk", "vectorStatus", 1));
        result.put("averageConfidence", commonMapper.averageConfidence());
        return result;
    }

    public String nextSessionNo() {
        return "QA" + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(100, 999);
    }

    private String buildAnswer(String question, List<Map<String, Object>> chunks) {
        String summary = chunks.stream()
                .map(item -> String.valueOf(item.get("chunkTitle")) + "：" + String.valueOf(item.get("chunkContent")))
                .collect(Collectors.joining("；"));
        return "根据企业知识库命中内容，问题“" + question + "”的参考答案为：" + summary;
    }
}
