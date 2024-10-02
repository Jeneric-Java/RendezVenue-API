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

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class EventsApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ApiService apiService;

	@Test
	@DisplayName("get all events")
	public void getAllEvents() throws Exception {
		List<Event> mockEvents = Arrays.asList(
				new Event(1L, "title1", "description1", "location1", "url1", "type1", new Time(1L, 2024, 9, 3, 12, 5)
				)
		)
	}




	@Test
	void contextLoads() {
	}

}
