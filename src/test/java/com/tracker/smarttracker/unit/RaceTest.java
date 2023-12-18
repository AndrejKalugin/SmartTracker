package com.tracker.smarttracker.unit;

import com.tracker.smarttracker.service.RaceService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RaceTest extends BaseUnitTest {

    @Mock
    RaceService raceService;

    @Test
    void testGetAverageSpeedByRace_thenCorrectSpeed() {
        when(raceService.getAverageSpeedByRace(any())).thenCallRealMethod();
        var averageSpeedByRace = raceService.getAverageSpeedByRace(race);
        var result = new BigDecimal("0.0006944444444444444444444444444444444");

        assertThat(averageSpeedByRace, Matchers.comparesEqualTo(result));
    }
}
