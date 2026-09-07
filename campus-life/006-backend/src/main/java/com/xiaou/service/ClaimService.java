package com.xiaou.service;

import com.xiaou.entity.Claim;
import com.xiaou.entity.FoundItem;
import com.xiaou.entity.LostItem;
import com.xiaou.entity.Notification;
import com.xiaou.mapper.ClaimMapper;
import com.xiaou.mapper.FoundItemMapper;
import com.xiaou.mapper.LostItemMapper;
import com.xiaou.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClaimService {

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private LostItemMapper lostItemMapper;

    @Autowired
    private FoundItemMapper foundItemMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    public List<Claim> getAllClaims() {
        return claimMapper.findAll();
    }

    public Claim getClaimById(Long id) {
        return claimMapper.findById(id);
    }

    public List<Claim> getUserSentClaims(Long userId) {
        return claimMapper.findByClaimerId(userId);
    }

    public List<Claim> getUserReceivedClaims(Long userId) {
        return claimMapper.findByOwnerId(userId);
    }

    @Transactional
    public void submitClaim(Claim claim) {
        claim.setStatus(0);
        claimMapper.insert(claim);

        Notification notification = new Notification();
        notification.setUserId(claim.getOwnerId());
        notification.setType("claim_apply");
        notification.setTitle("收到新的认领申请");
        
        String itemTitle = "";
        if ("lost".equals(claim.getItemType())) {
            LostItem item = lostItemMapper.findById(claim.getItemId());
            itemTitle = item.getTitle();
        } else {
            FoundItem item = foundItemMapper.findById(claim.getItemId());
            itemTitle = item.getTitle();
        }
        
        notification.setContent("您的物品【" + itemTitle + "】收到新的认领申请");
        notification.setLinkType("claim");
        notification.setLinkId(claim.getId());
        notification.setIsRead(0);
        notificationMapper.insert(notification);
    }

    @Transactional
    public void reviewClaim(Long id, Integer status, String reply) {
        Claim claim = claimMapper.findById(id);
        claim.setStatus(status);
        claim.setReply(reply);
        claimMapper.update(claim);

        Notification notification = new Notification();
        notification.setUserId(claim.getClaimerId());
        notification.setType("claim_result");
        notification.setTitle("认领申请审核结果");
        
        String itemTitle = "";
        if ("lost".equals(claim.getItemType())) {
            LostItem item = lostItemMapper.findById(claim.getItemId());
            itemTitle = item.getTitle();
        } else {
            FoundItem item = foundItemMapper.findById(claim.getItemId());
            itemTitle = item.getTitle();
        }
        
        String result = status == 1 ? "已通过" : "已拒绝";
        notification.setContent("您对物品【" + itemTitle + "】的认领申请" + result);
        notification.setLinkType("claim");
        notification.setLinkId(claim.getId());
        notification.setIsRead(0);
        notificationMapper.insert(notification);
    }

    public void deleteClaim(Long id) {
        claimMapper.deleteById(id);
    }
}

