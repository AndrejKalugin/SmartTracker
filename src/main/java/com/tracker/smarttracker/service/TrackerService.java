package com.tracker.smarttracker.service;

import com.tracker.smarttracker.dto.RaceAnalyticsDto;
import com.tracker.smarttracker.dto.RaceDto;
import com.tracker.smarttracker.repo.RaceRepository;
import com.tracker.smarttracker.util.ConverterHelper;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrackerService {

    RaceRepository raceRepository;
    EntityManagerFactory entityManagerFactory;
    ConverterHelper converterHelper;
    static final LocalDateTime DEFAULT_TIME_MIN = LocalDateTime.now().minusYears(10);
    static final LocalDateTime DEFAULT_TIME_MAX = LocalDateTime.now().plusYears(10);

    public List<RaceDto> getAllUserRace(long userId, LocalDateTime fromDatetime, LocalDateTime toDatetime) {
        var raceList = raceRepository.findByUserIdAndStartDatetimeAfterAndFinishDatetimeBefore(userId,
                Optional.ofNullable(fromDatetime).orElse(DEFAULT_TIME_MIN),
                Optional.ofNullable(toDatetime).orElse(DEFAULT_TIME_MAX));

        return converterHelper.convertToDtoList(raceList, RaceDto.class);
    }

    public RaceAnalyticsDto getUserRaceAnalytics(long userId, LocalDateTime fromDatetime, LocalDateTime toDatetime) {
        long finishedRaceCount = 0;
        BigDecimal sumDistance = BigDecimal.ZERO;
        BigDecimal userSumSpeedPerPeriod = BigDecimal.ZERO;

        for (var userRace : getAllUserRace(userId, fromDatetime, toDatetime)) {
            finishedRaceCount++;
            sumDistance = sumDistance.add(userRace.getDistance());
            userSumSpeedPerPeriod = userSumSpeedPerPeriod.add(userRace.getAverageSpeed());
        }
        BigDecimal userAverageSpeedPerPeriod = userSumSpeedPerPeriod.divide(BigDecimal.valueOf(finishedRaceCount));

        return RaceAnalyticsDto.builder()
                               .userId(userId)
                               .sumDistance(sumDistance)
                               .finishedRaceCount(finishedRaceCount)
                               .userAverageSpeedPerPeriod(userAverageSpeedPerPeriod)
                               .build();
    }
}
