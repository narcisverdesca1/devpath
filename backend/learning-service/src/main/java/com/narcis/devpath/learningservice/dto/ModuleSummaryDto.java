package com.narcis.devpath.learningservice.dto;

import lombok.Builder;

@Builder
public record ModuleSummaryDto(
        Long id,
        String title
) {
}
