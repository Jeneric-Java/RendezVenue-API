package com.Jeneric_Java.calendarappapi;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.Time;
import com.Jeneric_Java.calendarappapi.service.ApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CalendarAppApiApplicationTests {



	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ApiService apiService;

	@Test
	@DisplayName("get all events")
	public void getAllEvents() throws Exception {
				List<Event> mockEvents = Arrays.asList(
						new Event(1L, "title1", "description1", "location1", "url1", "type1", new Time(1L, 2024, 9, 3, 12, 5), null),
						new Event(2L, "title2", "description2", "location2", "url2", "type2", new Time(2L, 2025, 4, 5, 7, 30), null),
						new Event(3L, "title3", "description3", "location3", "url3", "type3", new Time(3L, 2025, 12, 25, 0, 0), null)
				);

				when(apiService.getAllEvents("location1")).thenReturn(mockEvents);
				mockMvc.perform(get("/api/events/all")
								.param("location", "location1"))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$[0].id").value(1))
						.andExpect(jsonPath("$[0].title").value("title1"))
						.andExpect(jsonPath("$[0].description").value("description1"))
						.andExpect(jsonPath("$[0].location").value("location1"))
						.andExpect(jsonPath("$[0].url").value("url1"))
						.andExpect(jsonPath("$[0].type").value("type1"))
						.andExpect(jsonPath("$[0].startTime.hours").value(12))
						.andExpect(jsonPath("$[0].startTime.minutes").value(5))
						.andExpect(jsonPath("$[1].id").value(2))
						.andExpect(jsonPath("$[1].title").value("title2"))
						.andExpect(jsonPath("$[1].description").value("description2"))
						.andExpect(jsonPath("$[1].location").value("location2"))
						.andExpect(jsonPath("$[1].url").value("url2"))
						.andExpect(jsonPath("$[1].type").value("type2"))
						.andExpect(jsonPath("$[1].startTime.hours").value(7))
						.andExpect(jsonPath("$[1].startTime.minutes").value(30))
						.andExpect(jsonPath("$[2].id").value(3))
						.andExpect(jsonPath("$[2].title").value("title3"))
						.andExpect(jsonPath("$[2].description").value("description3"))
						.andExpect(jsonPath("$[2].location").value("location3"))
						.andExpect(jsonPath("$[2].url").value("url3"))
						.andExpect(jsonPath("$[2].type").value("type3"))
						.andExpect(jsonPath("$[2].startTime.hours").value(0))
						.andExpect(jsonPath("$[2].startTime.minutes").value(0));



	}




		@Test
		void contextLoads() {
		}

	}



