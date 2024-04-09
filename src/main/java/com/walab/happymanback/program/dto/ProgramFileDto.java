package com.walab.happymanback.program.dto;

import com.walab.happymanback.file.dto.FileDto;
import com.walab.happymanback.program.entity.ProgramFile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class ProgramFileDto {
    private String originFileName;
    private String storedFilePath;

    public static ProgramFileDto from(FileDto dto) {
        return ProgramFileDto.builder()
                .originFileName(dto.getOriginFileName())
                .storedFilePath(dto.getStoredFilePath())
                .build();
    }

    public static ProgramFileDto from(ProgramFile programFile) {
        return ProgramFileDto.builder()
                .originFileName(programFile.getOriginFileName())
                .storedFilePath(programFile.getStoredFilePath())
                .build();
    }
}