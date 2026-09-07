package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("favorite")
public class Favorite {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Integer type; // 1-剧本 2-店铺
    private Long targetId;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}
