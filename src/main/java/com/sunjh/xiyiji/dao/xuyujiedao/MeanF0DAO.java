package com.sunjh.xiyiji.dao.xuyujiedao;

import com.sunjh.xiyiji.data.xuyujiemodel.Duration;
import com.sunjh.xiyiji.data.xuyujiemodel.ExcursionSize;
import com.sunjh.xiyiji.data.xuyujiemodel.MeanF0;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/25 11:28 下午
 */
public interface MeanF0DAO extends CrudRepository<MeanF0, Long> {
    @Override
    <S extends MeanF0> S save(S s);

    @Override
    Optional<MeanF0> findById(Long id);

    List<MeanF0> findByUserName(String userName);

    List<MeanF0> findByNameAndTypeAndUserName(String name, String type, String username);

    @Override
    <S extends MeanF0> Iterable<S> saveAll(Iterable<S> iterable);

    @Query(value = "select d from MeanF0 d where d.userName=:userName")
    Page<MeanF0> findByUserNamePageable(@Param("userName") String userName, Pageable pageable);

    @Query(value = "select d from MeanF0 d where 1=1")
    Page<MeanF0> findPageable(Pageable pageable);

    long countByUserName(String userName);

    long countAllByUserNameNotNull();
}
