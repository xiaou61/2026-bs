package com.phishingtraining.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.MailSendRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailSendRecordMapper extends BaseMapper<MailSendRecord> {
}
