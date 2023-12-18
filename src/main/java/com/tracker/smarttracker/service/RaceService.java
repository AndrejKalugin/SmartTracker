package com.tracker.smarttracker.service;

import ch.obermuhlner.math.big.DefaultBigDecimalMath;
import com.tracker.smarttracker.dto.RaceDto;
import com.tracker.smarttracker.dto.RaceRecordDto;
import com.tracker.smarttracker.exception.ArgumentNotValidException;
import com.tracker.smarttracker.model.Race;
import com.tracker.smarttracker.repo.RaceRepository;
import com.tracker.smarttracker.repo.UserRepository;
import com.tracker.smarttracker.util.BeanCopyUtils;
import com.tracker.smarttracker.util.ConverterHelper;
import com.tracker.smarttracker.util.MathUtility;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RaceService {

    private static final String START = "start";
    private static final String FINISH = "finish";
    private static final BigDecimal SECONDS_TO_MINUTES = BigDecimal.valueOf(60);

    RaceRepository raceRepository;
    UserRepository userRepository;
    ConverterHelper converterHelper;

    public List<RaceDto> findAll() {
        var races = raceRepository.findAll();
        return converterHelper.convertToDtoList(races, RaceDto.class);
    }

    public void startRace(RaceRecordDto raceRecordDto) {
        var race = raceRepository.findByIsActiveTrueAndUserId(raceRecordDto.getUserId());
        if (Objects.nonNull(race)) throw new ArgumentNotValidException("the current user has another active races");
        if (Optional.ofNullable(raceRecordDto.getDistance()).isPresent()) {
            throw new ArgumentNotValidException("distance field should be empty");
        }
        ConverterHelper.setMapperArguments(START);
        race = converterHelper.convertToEntity(raceRecordDto, Race.class);
        race.setIsActive(true);
        raceRepository.save(race);
    }

    public void finishRace(RaceRecordDto raceRecordDto) {
        var race = raceRepository.findByIsActiveTrueAndUserId(raceRecordDto.getUserId());
        if (Objects.isNull(race)) throw new EntityNotFoundException("the current user has no active races");
        if (Optional.ofNullable(raceRecordDto.getDistance()).isEmpty()) {
            var distance = MathUtility.getDistance(race.getStartLatitude(), raceRecordDto.getLatitude(),
                    race.getStartLongitude(), raceRecordDto.getLongitude());
            raceRecordDto.setDistance(distance);
        }
        ConverterHelper.setMapperArguments(FINISH);
        var resultRace = converterHelper.convertToEntity(raceRecordDto, Race.class);
        BeanCopyUtils.copyNonNullProperties(race, resultRace);
        resultRace.setIsActive(false);
        resultRace.setAverageSpeed(getAverageSpeedByRace(resultRace));
        raceRepository.save(resultRace);
    }

    public BigDecimal getAverageSpeedByRace(Race race) {
        var durationInSeconds = Duration.between(race.getStartDatetime(), race.getFinishDatetime()).getSeconds();
        var durationInMinutes = BigDecimal.valueOf(durationInSeconds).divide(SECONDS_TO_MINUTES,
                DefaultBigDecimalMath.currentMathContext());

        return race.getDistance().divide(durationInMinutes, DefaultBigDecimalMath.currentMathContext());
    }
}
