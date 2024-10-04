package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.controller.Parser;
import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.ApiPage;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.secrets.Secrets;
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
import java.util.concurrent.TimeUnit;

@Service
public class TicketmasterService {
    private final Secrets secrets = new Secrets();
    @Autowired
    private Parser parser;
    @Autowired
    private RestClient client;

    private final LoadingCache<String, List<Event>> eventCache;

    public TicketmasterService(Parser parser, RestClient client) {
        this.parser = parser;
        this.client = client;

        CacheLoader<String, List<Event>> loader = new CacheLoader<String, List<Event>>() {
            @Override
            public List<Event> load(String key) throws Exception {
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

    public List<Event> getEventByGeoHash(String geoHash) throws ParseException {
        if (geoHash.length() <= 3 || !geoHash.matches("^[a-zA-Z\\d]+$")) throw new IllegalArgumentException();

        if  (geoHash.length() > 9) {
            geoHash = geoHash.substring(0, 9);
        }

        StringBuilder uriBuilder = new StringBuilder(".json?radius=5&locale=en-gb&size=200");
        uriBuilder.append("&geoPoint=").append(geoHash);
        uriBuilder.append("&apikey=").append(secrets.getTicketmasterKey());

        ApiPage result = client.get()
                .uri(uriBuilder.toString())
                .retrieve()
                .body(ApiPage.class);

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
                        .body(ApiPage.class);

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
