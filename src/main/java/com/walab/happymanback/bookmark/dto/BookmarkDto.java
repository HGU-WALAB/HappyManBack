package com.walab.happymanback.bookmark.dto;

import com.walab.happymanback.bookmark.entity.Bookmark;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkDto {
    private Long programId;

    public static BookmarkDto from(Bookmark bookmark) {
        return BookmarkDto.builder()
            .programId(bookmark.getProgram().getId())
            .build();
    }
}
