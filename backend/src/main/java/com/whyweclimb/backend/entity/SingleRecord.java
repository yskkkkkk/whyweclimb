package com.whyweclimb.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tb_single_record")
public class SingleRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer singleRecordSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column
    private Integer record;

    @Column
    private LocalDate date;
}
