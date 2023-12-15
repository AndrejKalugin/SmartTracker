package com.tracker.smarttracker.controller;

import com.tracker.smarttracker.dto.RaceDto;
import com.tracker.smarttracker.dto.RaceRecordDto;
import com.tracker.smarttracker.service.RaceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/races")
@AllArgsConstructor
public class RaceController {

    RaceService raceService;

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public void startRace(@RequestBody @Valid RaceRecordDto raceRecordDto) {
        raceService.startRace(raceRecordDto);
    }

    @PostMapping("/finish")
    @ResponseStatus(HttpStatus.OK)
    public void finishRace(@RequestBody @Valid RaceRecordDto raceRecordDto) {
        raceService.finishRace(raceRecordDto);
    }

    @GetMapping
    public List<RaceDto> getAllRace() {
        return raceService.findAll();
    }

}
