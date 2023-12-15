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
    @NotNull
    private Long userId;

    @Min(-90)
    @Max(90)
    private BigDecimal latitude;

    @Min(-180)
    @Max(180)
    private BigDecimal longitude;

    @PastOrPresent(message = "Enter correct start datetime")
    private LocalDateTime datetime;

    private BigDecimal distance;
}
