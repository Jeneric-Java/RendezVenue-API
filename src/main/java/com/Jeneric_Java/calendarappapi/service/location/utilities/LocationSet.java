package com.Jeneric_Java.calendarappapi.service.location.utilities;

import org.elasticsearch.geometry.utils.Geohash;

public enum LocationSet {
    MANCHESTER(1L, "Manchester", "gcw2hzwzj", "20"),
    LONDON(2L, "London", "gcpvj3kc8", "20"),
    LIVERPOOL(3L, "Liverpool", "gcmzu4j1n", "20"),
    BIRMINGHAM(4L, "Birmingham", "gcqdsf542", "20"),
    EDINBURGH(5L, "Edinburgh", "gcvwr3qvs", "20"),
    GLASGOW(6L, "Glasgow", "gcuvz1h5m", "20"),
    NEWCASTLE_UPON_TYNE(7L, "Newcastle-Upon-Tyne", "gcyberyjh", "20"),
    DUBLIN(8L, "Dublin", "gc7x3xy0e", "20"),
    BELFAST(9L, "Belfast", "gcey953bh", "20"),
    LEEDS(10L, "Leeds", "gcwfhccmj", "20"),
    BRISTOL(11L, "Bristol", "gcnhtnbsm", "20"),
    SWANSEA(12L, "Swansea", "gcjjwkukz", "20"),
    CARDIFF(13L, "Cardiff", "gcjszewmk", "20"),
    SOUTHAMPTON(14L, "Southampton", "gcp184348", "20"),
    GLOUCESTER(15L, "Gloucester", "gcnrj11sn", "20"),
    NORWICH(16L, "Norwich", "u12gmkw12", "20"),
    HULL(17L, "Hull", "gcxcb210d", "20"),
    INVERNESS(18L, "Inverness", "gfhyzzu03", "20");

    private final Long id;
    private final String geoHash;
    private final String latitude;
    private final String longitude;
    private final String city;
    private final String radius;

    LocationSet(Long id, String city, String geoHash, String radius) {
        this.id = id;
        this.geoHash = geoHash;
        this.latitude = String.valueOf(Geohash.decodeLatitude(geoHash));
        this.longitude = String.valueOf(Geohash.decodeLongitude(geoHash));
        this.city = city;
        this.radius = radius;
    }

    public Long getId() {
        return id;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getRadius() {
        return radius;
    }
}
