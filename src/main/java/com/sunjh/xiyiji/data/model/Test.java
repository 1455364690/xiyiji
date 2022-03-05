package com.sunjh.xiyiji.data.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author sunjh
 * @version 1.0
 * @date 2020/10/16 2:39 下午
 */
@Data
@Entity
@Table(name = "test.txt")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String  name;

    @Column(name="value")
    private String  value;
}
