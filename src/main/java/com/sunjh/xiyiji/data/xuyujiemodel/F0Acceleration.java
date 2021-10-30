package com.sunjh.xiyiji.data.xuyujiemodel;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/24 5:11 下午
 */
@Data
@Entity
@Table(name = "f0_acceleration")
public class F0Acceleration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "data_1")
    private String dataOne;

    @Column(name = "data_2")
    private String dataTwo;

    @Column(name = "data_3")
    private String dataThree;

    @Column(name = "data_4")
    private String dataFour;

    @Column(name = "data_5")
    private String dataFive;

    @Column(name = "data_6")
    private String dataSix;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "ext_info")
    private String extInfo;

    @Column(name = "gmt_create")
    private Timestamp gmtCreate;

    @Column(name = "gmt_modified")
    private Timestamp gmtModified;
}
