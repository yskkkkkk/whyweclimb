package com.whyweclimb.backend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userSeq;
    @Column(length = 100)
    private String userId;
    @Column
    private String userPassword;
    @Column
    private Integer backgroundSound;
    @Column
    private Integer effectSound;
    
}
