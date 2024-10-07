package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.controller.Parser;
import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.TicketmasterPage;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.secrets.Secrets;
import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationSet;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class TicketmasterService {
    private final Secrets secrets = new Secrets();
    @Autowired
    private Parser parser;
    @Autowired
    private RestClient client;

    private final LoadingCache<LocationSet, List<Event>> eventCache;

    public TicketmasterService(Parser parser, RestClient client) {
        this.parser = parser;
        this.client = client;

        CacheLoader<LocationSet, List<Event>> loader = new CacheLoader<>() {
            @Override
            public List<Event> load(LocationSet key) throws Exception {
                return getEventByGeoHash(key);
            }
        };
        this.eventCache = CacheBuilder
                .newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .build(loader);

        Timer cacheCleaner = new Timer(true);
        cacheCleaner.scheduleAtFixedRate(new CacheCleaner(), 300000, 60000);
        Timer cacheInvalidater = new Timer(true);
        cacheInvalidater.scheduleAtFixedRate(new CacheInvalidater(), 3000000, 3000000);
    }

    public List<Event> getEventFromCache(LocationSet location) throws ExecutionException {
        return eventCache.get(location);
    }

    public List<Event> getEventByGeoHash(LocationSet location) throws ParseException {
        String geoHash = location.getGeoHash();

        StringBuilder uriBuilder = new StringBuilder(".json?locale=en-gb&size=200");
        uriBuilder.append("&geoPoint=").append(geoHash);
        uriBuilder.append("&radius=").append(location.getRadius());
        uriBuilder.append("&apikey=").append(secrets.getTicketmasterKey());

        TicketmasterPage result = client.get()
                .uri(uriBuilder.toString())
                .retrieve()
                .body(TicketmasterPage.class);

        if (result == null || result._embedded() == null || result._embedded().events() == null) throw new NoResultsFoundException("No results for query!");

        if (result.page().totalPages() < 2) {
            return parser.parsePage(result);
        } else {
            List<Event> events = new ArrayList<>(parser.parsePage(result));
            int pages = Math.min(result.page().totalPages(), 5);
            for (int i = 1; i < pages; i++) {
                result = client.get()
                        .uri(uriBuilder.toString() + "&page=" + i)
                        .retrieve()
                        .body(TicketmasterPage.class);

                events.addAll(parser.parsePage(result));
            }
            return events;
        }
    }

    @Configuration
    private static class TicketmasterServiceConfig {
        private final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/events";

        @Bean
        public RestClient restClient(RestClient.Builder builder) {
            return builder.build();
        }

        @Bean
        public RestClientCustomizer restClientCustomizer(){
            return restClientBuilder -> restClientBuilder
                    .requestFactory(new JdkClientHttpRequestFactory())
                    .messageConverters(converters -> converters.add(new MappingJackson2HttpMessageConverter()))
                    .baseUrl(BASE_URL)
                    .build();
        }

        public TicketmasterServiceConfig() {
        }
    }

    public class CacheCleaner extends TimerTask {

        @Override
        public void run() {
            eventCache.cleanUp();
        }
    }
    public class CacheInvalidater extends TimerTask {

        @Override
        public void run() {
            eventCache.invalidateAll();
        }
    }
}
