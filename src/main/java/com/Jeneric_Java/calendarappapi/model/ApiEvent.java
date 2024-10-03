package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiEvent(String name, String url, Dates dates, Classifications[] classifications, Embedded _embedded) {
    public record Dates(Date start){
      public record Date(String localTime, String localDate){
            public boolean hasNullFields() {
                return this.localTime() == null || this.localDate() == null;
            }
        }
    }

    public record Classifications(Segment segment){
        public record Segment(String name){}
    }

    public record Embedded(Venue[] venues){
        public record Venue(String name, String postalCode){}
    }

    public boolean hasNullFields() {
        return this.name() == null
                || this.url() == null
                || this.dates() == null
                || this.classifications() == null
                || this.classifications().length == 0
                || this.classifications()[0] == null
                || this.classifications()[0].segment() == null
                || this.classifications()[0].segment().name() == null
                || this._embedded() == null || this._embedded().venues() == null
                || this._embedded().venues().length == 0
                || this._embedded().venues()[0] == null
                || this._embedded().venues()[0].name() == null
                || this._embedded().venues()[0].postalCode() == null;
    }
}
