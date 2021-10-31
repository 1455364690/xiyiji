package com.sunjh.xiyiji.data.xuyujie;

import com.sunjh.xiyiji.data.xuyujie.vo.XuyujieUploadVO;
import lombok.Data;

import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/31 7:39 下午
 */
@Data
public class DownloadSelectedDataRequest {
    private List<XuyujieUploadVO> dataList;
    private String dataType;
}
