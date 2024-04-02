package com.walab.happymanback.program.dto;

import com.walab.happymanback.file.dto.FileDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProgramFileDto {
    private String originFileName;
    private String storedFileName;

    public static ProgramFileDto from(FileDto dto) {
        return ProgramFileDto.builder()
                .originFileName(dto.getOriginFileName())
                .storedFileName(dto.getStoredFileName())
                .build();
    }
}
