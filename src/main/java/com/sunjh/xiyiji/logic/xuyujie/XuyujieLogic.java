package com.sunjh.xiyiji.logic.xuyujie;

import com.sunjh.xiyiji.data.xuyujie.BaseVoiceEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 8:33 下午
 */
public interface XuyujieLogic {
    String saveFile(MultipartFile file);

    List<BaseVoiceEntity> analyseFile(String fileName);
}
