package com.narcis.devpath.noteservice.dto;

import lombok.Builder;

@Builder
public record ModuleSummaryDto(
        Long id,
        String title
) {
}
