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
public class room {
    @Id
    private Integer roomCode;
    @Column
    private Boolean roomPrivate;
    @Column
    private Boolean roomInterference;
    @Column
    private Integer roomMaxNum;
    
}
