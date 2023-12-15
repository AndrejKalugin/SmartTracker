package com.tracker.smarttracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.smarttracker.dto.RaceRecordDto;
import com.tracker.smarttracker.model.Race;
import com.tracker.smarttracker.model.Sex;
import com.tracker.smarttracker.model.User;
import com.tracker.smarttracker.repo.RaceRepository;
import com.tracker.smarttracker.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RaceControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RaceRepository raceRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testStartRaceRequestWithValidRace_thenCorrectResponse() throws Exception {
		var savedUser = userRepository.save(User.builder()
											.birthDate(LocalDate.now())
											.firstName("Joe")
											.lastName("Doe")
											.sex(Sex.MALE)
											.build());

		var raceRecord = RaceRecordDto.builder()
													.userId(savedUser.getId())
													.datetime(LocalDateTime.now())
													.latitude(BigDecimal.valueOf(45))
													.longitude(BigDecimal.valueOf(90))
													.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/races/start")
						.content(objectMapper.writeValueAsString(raceRecord))
						.contentType(MediaType.APPLICATION_JSON))
				        .andExpect(status().isCreated());
	}

	@Test
	void testStartRaceRequestWithNoValidRace_thenBadRequestResponse() throws Exception {
		var savedUser = userRepository.save(User.builder()
														.birthDate(LocalDate.now())
														.firstName("Joe")
														.lastName("Doe")
														.sex(Sex.MALE)
														.build());

		var race = raceRepository.save(Race.builder()
													.averageSpeed(BigDecimal.ZERO)
													.distance(BigDecimal.ZERO)
													.user(savedUser)
													.finishLatitude(BigDecimal.TEN)
													.startLatitude(BigDecimal.ONE)
													.finishLongitude(BigDecimal.ZERO)
													.startLongitude(BigDecimal.ONE)
													.isActive(true)
													.startDatetime(LocalDateTime.now())
													.finishDatetime(LocalDateTime.now().plusMinutes(1L))
													.build());

		var raceRecord = RaceRecordDto.builder()
													.userId(savedUser.getId())
													.datetime(LocalDateTime.now())
													.latitude(BigDecimal.valueOf(45))
													.longitude(BigDecimal.valueOf(90))
													.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/races/start")
						.content(objectMapper.writeValueAsString(raceRecord))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException()
						.getMessage().contains("the current user has another active races")));
	}

	@Test
	void testFinishRaceRequestWithNoValidRace_thenBadRequestResponse() throws Exception {
		var savedUser = userRepository.save(User.builder()
				.birthDate(LocalDate.now())
				.firstName("Joe")
				.lastName("Doe")
				.sex(Sex.MALE)
				.build());

		var raceRecord = RaceRecordDto.builder()
				.userId(savedUser.getId())
				.datetime(LocalDateTime.now())
				.latitude(BigDecimal.valueOf(45))
				.longitude(BigDecimal.valueOf(90))
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/races/finish")
						.content(objectMapper.writeValueAsString(raceRecord))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException()
						.getMessage().contains("the current user has no active races")));
	}
}