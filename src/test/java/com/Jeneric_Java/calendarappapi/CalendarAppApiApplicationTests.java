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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;

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
				mockMvc.perform(get("/api/events"));

	}




		@Test
		void contextLoads() {
		}

	}


}
