package com.Jeneric_Java.calendarappapi.service.location.utilities;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

import org.elasticsearch.geometry.utils.Geohash;

public class Location {

    private final long locationId;
    private final String latitude;
    private final String longitude;
    private final String geoHash;
    private final String closestCity;
    private LocationSet locationSet;

    private final static AtomicInteger atomicInteger = new AtomicInteger(1);

    public Location(String latitude, String longitude) {

        this.latitude = latitude;
        this.longitude = longitude;

        Supplier<String> geoHash = () -> {
            double longitudeAsDouble = Double.parseDouble(this.longitude);
            double latitudeAsDouble = Double.parseDouble(this.latitude);
            return Geohash.stringEncode(longitudeAsDouble, latitudeAsDouble);
        };

        this.geoHash = geoHash.get();

        this.closestCity = assignCityByGeoHash.apply(this.geoHash);

        this.locationId = atomicInteger.getAndIncrement();
    }

    public Location(String geoHash) {

        this.geoHash = geoHash;
        this.latitude = String.valueOf(Geohash.decodeLatitude(geoHash));
        this.longitude = String.valueOf(Geohash.decodeLongitude(geoHash));

        this.closestCity = "Manchester";

        this.locationId = atomicInteger.getAndIncrement();
    }


    public Location(LocationSet location) {

        this.geoHash = location.getGeoHash();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.closestCity = location.getCity();

        this.locationSet = location;

        this.locationId = atomicInteger.getAndIncrement();
    }

    private static final Function<String,String> assignCityByGeoHash = g -> {
        LocationUtil locationUtil = new LocationUtil();
        List<Location> locations = locationUtil.filterByLocations(locationUtil.setLocations(), new UserLocation(g), 100);

        return locations.getFirst().getClosestCity();
    };

    public long getLocationId() {
        return locationId;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public String getClosestCity() {
        return closestCity;
    }

    public LocationSet getLocationSet() {
        return locationSet;
    }
}