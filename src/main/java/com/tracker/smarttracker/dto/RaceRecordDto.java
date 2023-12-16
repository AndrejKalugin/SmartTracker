package com.tracker.smarttracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaceRecordDto {
    @NotNull(message = "user Id shouldn't be null value")
    private Long userId;

    @Min(message = "latitude should be greater -90 degree", value = -90)
    @Max(message = "latitude shouldn't be greater 90 degree", value = 90)
    private BigDecimal latitude;

    @Min(message = "longitude should be greater -180 degree", value = -180)
    @Max(message = "longitude shouldn't be greater 180 degree", value = 180)
    private BigDecimal longitude;

    @PastOrPresent(message = "Enter correct start datetime")
    private LocalDateTime datetime;

    private BigDecimal distance;
}
