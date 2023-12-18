package com.tracker.smarttracker.unit;

import com.tracker.smarttracker.model.Race;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class BaseUnitTest {

    protected static final LocalDateTime START = LocalDateTime.parse("2023-12-01T19:34:50.63");
    protected static final LocalDateTime FINISH = LocalDateTime.parse("2023-12-02T19:34:50.63");

    protected Race race = Race.builder()
                                .averageSpeed(BigDecimal.TEN)
                                .distance(BigDecimal.ONE)
                                .isActive(false)
                                .user(null)
                                .startDatetime(START)
                                .finishDatetime(FINISH)
                                .startLatitude(BigDecimal.ZERO)
                                .finishLatitude(BigDecimal.ZERO)
                                .startLongitude(BigDecimal.ONE)
                                .finishLongitude(BigDecimal.ONE)
                                .build();
}
