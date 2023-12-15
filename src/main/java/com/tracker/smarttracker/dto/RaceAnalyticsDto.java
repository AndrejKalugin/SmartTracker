package com.tracker.smarttracker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RaceAnalyticsDto {

    private long userId;

    private long finishedRaceCount;

    private BigDecimal sumDistance;

    private BigDecimal userAverageSpeedPerPeriod;
}
