package com.tracker.smarttracker.util;

import com.tracker.smarttracker.dto.RaceRecordDto;
import com.tracker.smarttracker.model.Race;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
@AllArgsConstructor
public class ConverterHelper {

    ModelMapper modelMapper;

    private static String mapperArguments;

    public static void setMapperArguments(String mapperArguments) {
        ConverterHelper.mapperArguments = mapperArguments;
    }

    public static boolean getCondition(MappingContext<RaceRecordDto, Race> ctx) {
        return ctx != null && ctx.getMapping().getPath().contains(mapperArguments);
    }

    public <T,Dto> Dto convertToDto(T object, Class<Dto> clazz) {
        return modelMapper.map(object, clazz);
    }

    public <T,Dto> T convertToEntity(Dto dto, Class<T> clazz) {
        return modelMapper.map(dto, clazz);
    }

    public <T, Dto> List<Dto> convertToDtoList(Iterable<T> iterable, Class<Dto> clazz) {
        return StreamSupport.stream(iterable.spliterator(), false)
                            .map(entity -> convertToDto(entity, clazz))
                            .toList();
    }
}