package com.tracker.smarttracker.dto;

import com.tracker.smarttracker.model.Sex;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Sex sex;
}