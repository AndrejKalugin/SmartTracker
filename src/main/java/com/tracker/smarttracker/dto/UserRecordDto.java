package com.tracker.smarttracker.dto;

import com.tracker.smarttracker.model.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRecordDto {
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String firstName;

    @NotBlank(message = "Lastname is mandatory")
    private String lastName;

    @Past(message = "Enter correct birthdate")
    @NotNull(message = "Birthdate should be not null")
    private LocalDate birthDate;

    @NotNull(message = "Enter the sex")
    private Sex sex;
}
