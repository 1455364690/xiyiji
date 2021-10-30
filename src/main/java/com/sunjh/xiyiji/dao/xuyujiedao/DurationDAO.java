package com.sunjh.xiyiji.dao.xuyujiedao;

import com.sunjh.xiyiji.data.xuyujiemodel.Duration;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/25 11:27 下午
 */
public interface DurationDAO extends CrudRepository<Duration, Long> {
    @Override
    <S extends Duration> S save(S s);

    @Override
    Optional<Duration> findById(Long id);

    List<Duration> findByUserName(String userName);

    List<Duration> findByNameAndTypeAndUserName(String name, String type, String username);

    @Override
    <S extends Duration> Iterable<S> saveAll(Iterable<S> iterable);
}
