package com.tracker.smarttracker.config;

import com.tracker.smarttracker.dto.RaceRecordDto;
import com.tracker.smarttracker.model.Race;
import com.tracker.smarttracker.util.ConverterHelper;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                   .setFieldMatchingEnabled(true)
                   .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                   .setMatchingStrategy(MatchingStrategies.LOOSE);

        var propertyMapperRaceRecord = modelMapper.createTypeMap(RaceRecordDto.class, Race.class);
        Condition<RaceRecordDto, Race> condition = ConverterHelper::getCondition;
        propertyMapperRaceRecord.addMappings(mapper -> mapper.when(condition).map(RaceRecordDto::getLatitude, Race::setStartLatitude));
        propertyMapperRaceRecord.addMappings(mapper -> mapper.when(condition).map(RaceRecordDto::getLongitude, Race::setStartLongitude));
        propertyMapperRaceRecord.addMappings(mapper -> mapper.when(condition).map(RaceRecordDto::getDatetime, Race::setStartDatetime));
        propertyMapperRaceRecord.addMappings(mapper -> mapper.when(condition).map(RaceRecordDto::getLatitude, Race::setFinishLatitude));
        propertyMapperRaceRecord.addMappings(mapper -> mapper.when(condition).map(RaceRecordDto::getLongitude, Race::setFinishLongitude));
        propertyMapperRaceRecord.addMappings(mapper -> mapper.when(condition).map(RaceRecordDto::getDatetime, Race::setFinishDatetime));

        return modelMapper;
    }
}
