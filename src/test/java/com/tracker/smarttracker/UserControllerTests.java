package com.tracker.smarttracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.smarttracker.dto.UserRecordDto;
import com.tracker.smarttracker.model.Sex;
import com.tracker.smarttracker.model.User;
import com.tracker.smarttracker.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testPostRequestToUsersWithValidUser_thenCorrectResponse() throws Exception {
		var user = "{\"firstName\": \"Joe\", \"lastName\": \"Doe\", \"birthDate\": \"2000-12-15\", \"sex\": \"MALE\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
						.content(user)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content()
						.contentType(MediaType.APPLICATION_JSON));
		userRepository.deleteAll();
	}

	@Test
	void testPostRequestToUsersWithNotValidUserName_thenBadRequestResponse() throws Exception {
		var user = "{\"lastName\": \"Doe\", \"birthDate\": \"2000-12-15\", \"sex\": \"MALE\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
						.content(user)
						.contentType(MediaType.APPLICATION_JSON))
				        .andExpect(result -> assertTrue(result.getResolvedException()
								.getMessage().contains("Name is mandatory")));
	}

	@Test
	void testPostRequestToUsersWithNotValidUserLastname_thenBadRequestResponse() throws Exception {
		var user = "{\"firstName\": \"Joe\", \"birthDate\": \"2000-12-15\", \"sex\": \"MALE\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
						.content(user)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException()
						.getMessage().contains("Lastname is mandatory")));
	}

	@Test
	void testPostRequestToUsersWithNotValidUserBirthdate_thenBadRequestResponse() throws Exception {
		var user = "{\"firstName\": \"Joe\", \"lastName\": \"Doe\", \"sex\": \"MALE\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
						.content(user)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException()
						.getMessage().contains("Birthdate should be not null")));
	}

	@Test
	void testPostRequestToUsersWithValidUserGender_thenCorrectResponse() throws Exception {
		var user = "{\"firstName\": \"Joe\", \"lastName\": \"Doe\", \"birthDate\": \"2000-12-15\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
						.content(user)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertTrue(result.getResolvedException()
						.getMessage().contains("Enter the sex")));
	}

	@Test
	void testDeleteRequestToUsersWithValidUser_thenCorrectResponse() throws Exception {
		var user = "{\"firstName\": \"Joe\", \"lastName\": \"Doe\", \"birthDate\": \"2000-12-15\", \"sex\": \"MALE\"}";
		var savedUser = userRepository.save(User.builder()
											 .birthDate(LocalDate.parse("2000-12-15"))
											 .firstName("Joe")
											 .lastName("Doe")
											 .sex(Sex.MALE)
											 .build());

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/v1/users/{id}", savedUser.getId())
						.contentType(MediaType.APPLICATION_JSON)
				        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
	}

	@Test
	void testUpdateRequestToUsersWithValidUser_thenCorrectResponse() throws Exception {
		var savedUser = userRepository.save(User.builder()
				.birthDate(LocalDate.now())
				.firstName("Joe")
				.lastName("Doe")
				.sex(Sex.MALE)
				.build());

		var user = UserRecordDto.builder()
												.id(savedUser.getId())
												.firstName("Jane")
												.lastName("Doe")
												.birthDate(LocalDate.parse("2000-12-15"))
												.sex(Sex.FEMALE)
												.build();

		mockMvc.perform(MockMvcRequestBuilders.put("/v1/users")
						.content(objectMapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(result -> assertThat(result.getResponse().getContentAsString(),
						is("{\"id\":"+savedUser.getId()+",\"firstName\":\"Jane\",\"lastName\":\"Doe\"," +
								"\"birthDate\":\"2000-12-15\",\"sex\":\"FEMALE\"}")));
	}
}