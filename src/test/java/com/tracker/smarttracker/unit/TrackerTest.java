package com.tracker.smarttracker.unit;

import com.tracker.smarttracker.dto.RaceAnalyticsDto;
import com.tracker.smarttracker.dto.RaceDto;
import com.tracker.smarttracker.repo.RaceRepository;
import com.tracker.smarttracker.service.TrackerService;
import com.tracker.smarttracker.util.ConverterHelper;
import com.tracker.smarttracker.util.MathUtility;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackerTest extends BaseUnitTest{

    @Mock
    private RaceRepository raceRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private ConverterHelper converterHelper = new ConverterHelper(modelMapper);

    @InjectMocks
    private TrackerService trackerService;

    @Test
    void testMathDistanceModule_thenCorrectDistance() {
        var result = new BigDecimal("5003771.6990051431805613752487184274476000");

        var distance = MathUtility.getDistance(BigDecimal.valueOf(45), BigDecimal.valueOf(90),
                BigDecimal.valueOf(89), BigDecimal.valueOf(179));

        assertThat(distance, Matchers.comparesEqualTo(result));
    }

    @Test
    void testGetAllUserRace_thenCorrectRaceDto() {

        var trackerService = new TrackerService(raceRepository, new ConverterHelper(modelMapper));
        when(raceRepository.findByUserIdAndStartDatetimeAfterAndFinishDatetimeBefore(anyLong(),any(),any()))
                           .thenReturn(List.of(race));
        var result = trackerService.getAllUserRace(1, START, FINISH);

        assertThat(result.get(0), is(modelMapper.map(race, RaceDto.class)));
    }

    @Test
    void testGetUserRaceAnalytics_thenCorrectRaceAnalyticsDto() {

        var raceAnalyticsDto = RaceAnalyticsDto.builder()
                                                                     .userId(1)
                                                                     .finishedRaceCount(1)
                                                                     .userAverageSpeedPerPeriod(new BigDecimal("10.00"))
                                                                     .sumDistance(BigDecimal.ONE)
                                                                     .build();

        when(raceRepository.findByUserIdAndStartDatetimeAfterAndFinishDatetimeBefore(anyLong(),any(),any()))
                .thenReturn(List.of(race));
        var result = trackerService.getUserRaceAnalytics(1, START, FINISH);

        assertThat(result, is(raceAnalyticsDto));
    }

}
