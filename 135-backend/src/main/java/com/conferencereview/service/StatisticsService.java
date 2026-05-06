package com.conferencereview.service;

import com.conferencereview.mapper.ConferenceInfoMapper;
import com.conferencereview.mapper.CallForPaperMapper;
import com.conferencereview.mapper.AuthorProfileMapper;
import com.conferencereview.mapper.PaperSubmissionMapper;
import com.conferencereview.mapper.ReviewerProfileMapper;
import com.conferencereview.mapper.ReviewAssignmentMapper;
import com.conferencereview.mapper.BlindReviewMapper;
import com.conferencereview.mapper.AcceptanceNoticeMapper;
import com.conferencereview.mapper.GuestRegistrationMapper;
import com.conferencereview.mapper.VenueRoomMapper;
import com.conferencereview.mapper.AgendaScheduleMapper;
import com.conferencereview.mapper.CheckinRecordMapper;
import com.conferencereview.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ConferenceInfoMapper consumableCatalogMapper;
    private final CallForPaperMapper supplierProfileMapper;
    private final AuthorProfileMapper labRoomMapper;
    private final PaperSubmissionMapper stockItemMapper;
    private final ReviewerProfileMapper purchaseRequestMapper;
    private final ReviewAssignmentMapper purchaseApprovalMapper;
    private final BlindReviewMapper purchaseOrderMapper;
    private final AcceptanceNoticeMapper inboundRecordMapper;
    private final GuestRegistrationMapper outboundRecordMapper;
    private final VenueRoomMapper inventoryCheckMapper;
    private final AgendaScheduleMapper warningRuleMapper;
    private final CheckinRecordMapper stockWarningMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("conferenceCount", consumableCatalogMapper.selectCount(null));
        data.put("paperCount", stockItemMapper.selectCount(null));
        data.put("reviewCount", purchaseRequestMapper.selectCount(null));
        data.put("agendaCount", stockWarningMapper.selectCount(null));
        data.put("submissionTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("agendaPie", Arrays.asList(map("主论坛", 35), map("分论坛", 31), map("工作坊", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}


