package com.tracker.smarttracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.smarttracker.model.Race;
import com.tracker.smarttracker.model.Sex;
import com.tracker.smarttracker.model.User;
import com.tracker.smarttracker.repo.RaceRepository;
import com.tracker.smarttracker.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class SmartTrackControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetRaceRequestForValidUser_thenCorrectResponse() throws Exception {
        var savedUser = userRepository.save(User.builder()
                .birthDate(LocalDate.now())
                .firstName("Joe")
                .lastName("Doe")
                .sex(Sex.MALE)
                .build());

        var savedRace = raceRepository.save(Race.builder()
                .distance(BigDecimal.valueOf(100))
                .startDatetime(LocalDateTime.parse("2023-12-13T23:47:23.22046"))
                .finishDatetime(LocalDateTime.parse("2023-12-14T23:47:23.22046"))
                .averageSpeed(BigDecimal.valueOf(100))
                .startLatitude(BigDecimal.valueOf(45))
                .finishLatitude(BigDecimal.valueOf(89))
                .startLongitude(BigDecimal.valueOf(90))
                .finishLongitude(BigDecimal.valueOf(179))
                .user(savedUser)
                .isActive(false)
                .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tracker/users/{id}/races", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(result.getResponse().getContentAsString(),
                        is("[{\"id\":" + savedRace.getId() + ",\"startLatitude\":45.0000,\"finishLatitude\":89.0000," +
                                "\"startLongitude\":90.0000,\"finishLongitude\":179.0000," +
                                "\"startDatetime\":\"2023-12-13T23:47:23.22046\"," +
                                "\"finishDatetime\":\"2023-12-14T23:47:23.22046\",\"distance\":100.0000," +
                                "\"averageSpeed\":100.0000,\"active\":false}]")));
    }
}
