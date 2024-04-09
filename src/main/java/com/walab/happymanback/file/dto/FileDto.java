package com.walab.happymanback.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDto {
    private String originFileName;
    private String storedFilePath;
}
