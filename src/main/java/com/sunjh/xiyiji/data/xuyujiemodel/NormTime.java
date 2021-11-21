package com.sunjh.xiyiji.data.xuyujiemodel;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/11/20 1:54 下午
 */
@Data
@Entity
@Table(name = "norm_time")
public class NormTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "unique_name")
    private String uniqueName;

    @Column(name = "data")
    private String data;

    @Column(name = "ext_info")
    private String extInfo;

    @Column(name = "gmt_create")
    private Timestamp gmtCreate;

    @Column(name = "gmt_modify")
    private Timestamp gmtModify;
}
