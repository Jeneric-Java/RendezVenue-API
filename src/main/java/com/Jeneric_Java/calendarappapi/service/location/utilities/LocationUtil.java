package com.Jeneric_Java.calendarappapi.service.location.utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class LocationUtil {

    private final static Function<String,Double> toRadians = s -> Double.parseDouble(s) * Math.PI / 180.0;

    public List<Location> filterByLocations(List<Location> locations, UserLocation userLocation, double radius) {

        HashMap<Long, Double> mapDistancesByLocation = new HashMap<>();

        for (Location location : locations) {
            double dist = calcHaversine(userLocation, location);
            mapDistancesByLocation.put(location.getLocationId(), dist);
        }

        Predicate<Location> filterByAbsDistanceFromUser = l -> mapDistancesByLocation.get(l.getLocationId()) < radius;

        Comparator<Location> orderByProximity = (l1, l2) -> (int) (  (mapDistancesByLocation.get(l1.getLocationId()) - mapDistancesByLocation.get(l2.getLocationId())) * 100 );

        return locations.stream()
                .filter(filterByAbsDistanceFromUser)
                .sorted(orderByProximity)
                .limit(3)
                .toList();
    }

    private double calcHaversine(Location origin, Location destination) {

        double lat1 = toRadians.apply(origin.getLatitude());
        double lat2 = toRadians.apply(destination.getLatitude());
        double long1 = toRadians.apply(origin.getLongitude());
        double long2 = toRadians.apply(destination.getLongitude());

        double theta = long1 - long2;

        double dist = Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(theta);

        dist = Math.acos(dist);

        dist *= 6371 / 1.609344;

        return dist;
    }

    public List<Location> setLocations() {

        List<Location> locationList = new ArrayList<>();

        LocationSet[] locations = LocationSet.class.getEnumConstants();
        for (LocationSet loc : locations) {
            Location location = new Location(loc);
            locationList.add(location);
        }

        return locationList;
    }
}
