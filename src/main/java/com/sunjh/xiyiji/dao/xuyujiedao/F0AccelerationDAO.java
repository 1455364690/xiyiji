package com.sunjh.xiyiji.dao.xuyujiedao;

import com.sunjh.xiyiji.data.xuyujiemodel.F0Acceleration;
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
 * @date 2021/10/31 1:11 上午
 */
public interface F0AccelerationDAO  extends CrudRepository<F0Acceleration, Long> {
    @Override
    <S extends F0Acceleration> S save(S s);

    @Override
    Optional<F0Acceleration> findById(Long id);

    List<F0Acceleration> findByUserName(String userName);

    List<F0Acceleration> findByNameAndTypeAndUserName(String name, String type, String username);

    @Override
    <S extends F0Acceleration> Iterable<S> saveAll(Iterable<S> iterable);

    @Query(value = "select d from F0Acceleration d where d.userName=:userName")
    Page<F0Acceleration> findByUserNamePageable(@Param("userName") String userName, Pageable pageable);

    long countByUserName(String userName);
}
