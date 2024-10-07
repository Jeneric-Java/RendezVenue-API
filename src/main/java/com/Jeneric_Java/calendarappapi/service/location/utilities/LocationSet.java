package com.Jeneric_Java.calendarappapi.service.location.utilities;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.elasticsearch.geometry.utils.Geohash;

public enum LocationSet {
    @JsonAlias("Manchester")
    MANCHESTER(1L, "Manchester", "gcw2hzwzj", "20"),

    @JsonAlias("London")
    LONDON(2L, "London", "gcpvj3kc8", "20"),

    @JsonAlias("Liverpool")
    LIVERPOOL(3L, "Liverpool", "gcmzu4j1n", "20"),

    @JsonAlias("Birmingham")
    BIRMINGHAM(4L, "Birmingham", "gcqdsf542", "20"),

    @JsonAlias("Edinburgh")
    EDINBURGH(5L, "Edinburgh", "gcvwr3qvs", "20"),

    @JsonAlias("Glasgow")
    GLASGOW(6L, "Glasgow", "gcuvz1h5m", "20"),

    @JsonAlias({"Newcastle-Upon-Tyne", "Newcastle"})
    NEWCASTLE_UPON_TYNE(7L, "Newcastle-Upon-Tyne", "gcyberyjh", "20"),

    @JsonAlias("Dublin")
    DUBLIN(8L, "Dublin", "gc7x3xy0e", "20"),

    @JsonAlias("Belfast")
    BELFAST(9L, "Belfast", "gcey953bh", "20"),

    @JsonAlias("Leeds")
    LEEDS(10L, "Leeds", "gcwfhccmj", "20"),

    @JsonAlias("Bristol")
    BRISTOL(11L, "Bristol", "gcnhtnbsm", "20"),

    @JsonAlias("Swansea")
    SWANSEA(12L, "Swansea", "gcjjwkukz", "20"),

    @JsonAlias("Cardiff")
    CARDIFF(13L, "Cardiff", "gcjszewmk", "20"),

    @JsonAlias("Southampton")
    SOUTHAMPTON(14L, "Southampton", "gcp184348", "20"),

    @JsonAlias("Gloucester")
    GLOUCESTER(15L, "Gloucester", "gcnrj11sn", "20"),

    @JsonAlias("Norwich")
    NORWICH(16L, "Norwich", "u12gmkw12", "20"),

    @JsonAlias("Hull")
    HULL(17L, "Hull", "gcxcb210d", "20"),

    @JsonAlias("Inverness")
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