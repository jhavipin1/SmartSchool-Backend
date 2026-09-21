package com.smartSchool.dtos.library;

import com.smartSchool.enums.LibraryCardStatus;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class LibraryCardUpdateRequestDto {
    private String libraryCardNo;
    private LibraryCardStatus status;
}

