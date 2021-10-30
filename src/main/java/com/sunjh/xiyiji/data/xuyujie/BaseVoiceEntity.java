package com.sunjh.xiyiji.data.xuyujie;

import lombok.Data;

import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 8:35 下午
 */
@Data
public class BaseVoiceEntity {
    private String fileName;
    private String id;
    private String name;
    private String type;
    private List<String> data;
}
