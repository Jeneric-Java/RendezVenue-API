package com.Jeneric_Java.calendarappapi.service.location.locationparser;

import com.Jeneric_Java.calendarappapi.service.location.utilities.Location;
import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationUtil;
import com.Jeneric_Java.calendarappapi.service.location.utilities.UserLocation;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class LocationParser {

    private static final String algorithm = "AES/CBC/PKCS5Padding";

    public static List<Location> getCoverageByUserLocation(String cipherText)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, IOException, InvalidKeyException {

        String geoHash = parseEncrypted(cipherText);

        // we now have decrypted geohash for user's location

        // create a Location object from the user's now-decrypted geohash
        UserLocation usl = new UserLocation(geoHash);

        // contains all the tools for location logic
        LocationUtil locationUtil = new LocationUtil();

        // collect established major cities of type Location into list
        List<Location> nodeLoc = locationUtil.setLocations();

        return locationUtil.filterByLocations(nodeLoc, usl, 100);
    }

    static String parseEncrypted(String cipherText)
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException, IOException {

        SecretKey key = AESUtil.getKeyFromPassword();
        IvParameterSpec iv = AESUtil.getIv();

        return AESUtil.decrypt(algorithm, cipherText, key, iv);
    }
}