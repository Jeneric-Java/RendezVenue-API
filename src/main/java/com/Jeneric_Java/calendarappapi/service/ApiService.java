package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.model.Event;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface ApiService {

        List<Event> getEventsByLocation(String geoHashEnc) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, IOException, InvalidKeyException;
        Event getEventByID (Long id);
        String deleteEventById(Long id);
        Event insertEvent(Event event);
        Event updateEventById(Long id, Event event);
}
