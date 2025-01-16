package com.whyweclimb.backend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tb_room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomSeq;
    private String roomCode;
    private Boolean roomPrivate;
    private Boolean roomInterference;
    private Integer roomMaxNum;
    private Boolean roomStart;
}
