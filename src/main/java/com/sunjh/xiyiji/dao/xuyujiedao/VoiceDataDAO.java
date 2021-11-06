package com.sunjh.xiyiji.dao.xuyujiedao;

import com.sunjh.xiyiji.data.xuyujiemodel.VoiceData;
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
 * @date 2021/11/6 2:33 下午
 */
public interface VoiceDataDAO extends CrudRepository<VoiceData, Long> {
    @Override
    <S extends VoiceData> S save(S s);

    @Override
    Optional<VoiceData> findById(Long id);

    List<VoiceData> findByUserName(String userName);

    List<VoiceData> findByFileTypeAndUserName(String fileType, String userName);

    List<VoiceData> findByNameAndTypeAndUserName(String name, String type, String username);

    /**
     * @param iterable list
     * @param <S>      list
     * @return list
     */
    @Override
    <S extends VoiceData> Iterable<S> saveAll(Iterable<S> iterable);

    @Query(value = "select d from VoiceData d where d.userName=:userName")
    Page<VoiceData> findByUserNamePageable(@Param("userName") String userName, Pageable pageable);

    @Query(value = "select d from VoiceData d where d.fileType=:fileType and d.userName=:userName")
    Page<VoiceData> findByFileTypeAndUserNamePageable(@Param("fileType") String fileType, @Param("userName") String userName, Pageable pageable);

    @Query(value = "select d from VoiceData d where 1=1")
    Page<VoiceData> findPageable(Pageable pageable);

    @Query(value = "select d from VoiceData d where d.fileType=:fileType")
    Page<VoiceData> findByFileTypePageable(@Param("fileType") String fileType, Pageable pageable);

    @Override
    Iterable<VoiceData> findAll();

    long countByUserName(String userName);

    long countAllByUserNameNotNull();

    long countAllByFileTypeAndUserNameNotNull(String fileType);

    long countAllByFileTypeAndOwner(String fileType, String owner);

    long countAllByFileTypeAndUserName(String fileType, String userName);

    long countAllByFileType(String fileType);
}
