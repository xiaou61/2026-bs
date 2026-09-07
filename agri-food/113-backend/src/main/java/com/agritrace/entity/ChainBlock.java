package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("chain_block")
public class ChainBlock {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String blockNo;
    private String batchNo;
    private String txHash;
    private String dataHash;
    private Integer blockHeight;
    private String chainTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
