package com.tracker.smarttracker.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class RaceDto {
    private long id;

    private BigDecimal startLatitude;

    private BigDecimal finishLatitude;

    private BigDecimal startLongitude;

    private BigDecimal finishLongitude;

    private LocalDateTime startDatetime;

    private LocalDateTime finishDatetime;

    private BigDecimal distance;

    private BigDecimal averageSpeed;

    private boolean isActive;
}
