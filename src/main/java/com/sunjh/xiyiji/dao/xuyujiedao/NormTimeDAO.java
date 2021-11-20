package com.sunjh.xiyiji.dao.xuyujiedao;

import com.sunjh.xiyiji.data.xuyujiemodel.NormTime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/11/20 2:55 下午
 */
public interface NormTimeDAO extends CrudRepository<NormTime, Long> {
    @Override
    <S extends NormTime> S save(S entity);

    @Override
    Iterable<NormTime> findAll();

    @Override
    Optional<NormTime> findById(Long id);

    List<NormTime> findAllByType(String type);
}
