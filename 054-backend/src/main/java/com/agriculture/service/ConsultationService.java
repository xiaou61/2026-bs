package com.agriculture.service;

import com.agriculture.entity.Consultation;
import com.agriculture.entity.ConsultationAnswer;
import com.agriculture.mapper.ConsultationAnswerMapper;
import com.agriculture.mapper.ConsultationMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConsultationService extends ServiceImpl<ConsultationMapper, Consultation> {

    @Autowired
    private ConsultationAnswerMapper answerMapper;

    public Page<Consultation> getPage(Integer pageNum, Integer pageSize, String title, Integer status) {
        Page<Consultation> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Consultation> wrapper = new LambdaQueryWrapper<>();
        if (title != null && !title.isEmpty()) {
            wrapper.like(Consultation::getTitle, title);
        }
        if (status != null) {
            wrapper.eq(Consultation::getStatus, status);
        }
        wrapper.orderByDesc(Consultation::getCreateTime);
        return this.page(page, wrapper);
    }

    public void incrementView(Long id) {
        this.lambdaUpdate().eq(Consultation::getId, id).setSql("view_count = view_count + 1").update();
    }

    public List<ConsultationAnswer> getAnswers(Long consultationId) {
        return answerMapper.selectList(new LambdaQueryWrapper<ConsultationAnswer>()
                .eq(ConsultationAnswer::getConsultationId, consultationId)
                .orderByDesc(ConsultationAnswer::getIsAdopted)
                .orderByDesc(ConsultationAnswer::getCreateTime));
    }

    public void addAnswer(ConsultationAnswer answer) {
        answerMapper.insert(answer);
        this.lambdaUpdate().eq(Consultation::getId, answer.getConsultationId()).set(Consultation::getStatus, 1).update();
    }

    public void adoptAnswer(Long answerId) {
        ConsultationAnswer answer = answerMapper.selectById(answerId);
        answerMapper.update(null, new LambdaQueryWrapper<ConsultationAnswer>()
                .eq(ConsultationAnswer::getConsultationId, answer.getConsultationId())
                .set(ConsultationAnswer::getIsAdopted, 0));
        answer.setIsAdopted(1);
        answerMapper.updateById(answer);
        this.lambdaUpdate().eq(Consultation::getId, answer.getConsultationId()).set(Consultation::getStatus, 2).update();
    }
}
