package com.sunjh.xiyiji.dao;

import com.sunjh.xiyiji.data.model.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/11/4 3:25 下午
 */
public interface TestDao extends CrudRepository<Test, Long> {
    Test findOneById(Long id);
}
