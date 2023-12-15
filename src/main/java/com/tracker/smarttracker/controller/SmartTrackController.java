package com.tracker.smarttracker.controller;

import com.tracker.smarttracker.dto.RaceAnalyticsDto;
import com.tracker.smarttracker.dto.RaceDto;
import com.tracker.smarttracker.service.TrackerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/tracker")
@AllArgsConstructor
public class SmartTrackController {

    TrackerService trackerService;

    @GetMapping("/users/{id}/races")
    public List<RaceDto> getAllUserRace(@PathVariable(name = "id") long userId,
                                        @RequestParam(value = "from_datetime", required = false) LocalDateTime fromDatetime,
                                        @RequestParam(value = "to_datetime", required = false) LocalDateTime toDatetime) {
        return trackerService.getAllUserRace(userId, fromDatetime, toDatetime);
    }

    @GetMapping("/users/{id}/races/analytics")
    public RaceAnalyticsDto getUserRaceAnalytics(@PathVariable(name = "id") long userId,
                                                       @RequestParam(value = "from_datetime", required = false) LocalDateTime fromDatetime,
                                                       @RequestParam(value = "to_datetime", required = false) LocalDateTime toDatetime) {
        return trackerService.getUserRaceAnalytics(userId, fromDatetime, toDatetime);
    }
}
