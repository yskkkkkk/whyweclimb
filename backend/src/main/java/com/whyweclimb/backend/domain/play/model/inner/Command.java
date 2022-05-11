package com.whyweclimb.backend.domain.play.model.inner;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Command {
    private int id;
    private Boolean space;
    private Boolean left;
    private Boolean right;
}
