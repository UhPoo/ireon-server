package com.uhpoo.ireon.api.service.lost.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditLostCommentDto {

    private Long lostCommentId;
    private String comment;

    @Builder
    public EditLostCommentDto(Long lostCommentId, String comment) {
        this.lostCommentId = lostCommentId;
        this.comment = comment;
    }
}
