package com.sunjh.xiyiji.dao.xuyujiedao;

import com.sunjh.xiyiji.data.xuyujiemodel.Duration;
import com.sunjh.xiyiji.data.xuyujiemodel.ExcursionSize;
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
public interface ExcursionSizeDAO extends CrudRepository<ExcursionSize, Long> {
    @Override
    <S extends ExcursionSize> S save(S s);

    @Override
    Optional<ExcursionSize> findById(Long id);

    List<ExcursionSize> findByUserName(String userName);

    List<ExcursionSize> findByNameAndTypeAndUserName(String name, String type, String username);

    @Override
    <S extends ExcursionSize> Iterable<S> saveAll(Iterable<S> iterable);

    @Query(value = "select d from ExcursionSize d where d.userName=:userName")
    Page<ExcursionSize> findByUserNamePageable(@Param("userName") String userName, Pageable pageable);

    long countByUserName(String userName);
}
