package com.sunjh.xiyiji.data.xuyujie;

import lombok.Data;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 11:51 下午
 */
@Data
public class XuyujieQueryCondition {
    private String dataFileType;
    private int pageNum;
    private int pageSize;
    private String userName;
}
