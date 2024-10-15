![xxxhdpi-logobanner@4x (1)](https://github.com/user-attachments/assets/b37adab3-b856-41fe-a1e5-6a421ade2742)

# RendezVenue® API by Jenerics Software
This is your new go-to app for retrieving, collecting, and curating events on the fly within an Android environment. RendezVenue simplifies the hassle of scouring event content with a targeted search functionality to get the events you want, where and when you need them.

Do you prefer working with Outlook, TimeTree, or maybe even simply Google Calendar? No problem, we’ve got you covered. Effortlessly synchronised to a local calendar app of your choosing, RendezVenue takes a holistic approach with full system integration.

> [!NOTE]  
> This repository, and any of the files contained within, is part of a larger project.
>
> This API is made in tandem with an Android frontend, providing the data and services upon which it relies.
> 
> The RendezVenue Android application exists in a separate repository which you can find [here](https://github.com/Jeneric-Java/RendezVenue-App-Android).

## Overview

RendezVenue boasts a slick UI experience, with automatic background location filtering kicking in from the get-go, granting instant access to events as far as the eye can see. Entrusting response caching and computationally-demanding processes 
to the backend keeps the user experience uninterrupted. The frontend team have worked hard on embellishing the visuals with snappy graphics, elegantly designed, laid out, and all carefully chosen to complement the overarching theme.

## Table of Contents
- [Usage](#usage)
    - [JSON Structure](#json-structure)
    - [GET Requests](#get-requests)
    - [POST Requests](#post-requests)
    - [PUT Requests](#put-requests)
    - [DELETE Requests](#delete-requests)
- [Third-party Services & Caching](#third-party-services--caching)
    - [Background](#background)
    - [Specifics](#specifics)
- [Filtering by Location](#filtering-by-location)
    - [Background](#background)
    - [The Problem](#the-problem)
    - [Generating Keys](#generating-keys)
- [Future Considerations](#future-considerations)
- [FAQs](#faqs)
- [Bugs and Contributions](#bugs-and-contributions)

## Usage
### JSON Structure
Your requests should use the event JSON structure. An event looks like this:
```
{
    "id": integer,
    "title": string,
    "description": string,
    "location": string,
    "url": string,
    "type": enum (categories of event),
    "closestCity": enum (supported cities),
    "startTime": string (formatted HH:MM:SS),
    "startDate": string (formatted YYYY-MM-DD),
    "endTime": string (formatted HH:MM:SS),
    "endDate": string (formatted YYYY-MM-DD),
    "imageUrl": string
}
```
Values for `type`: `[ Arts & Theatre, Miscellaneous, Sports, Music, Film ]`

Values for `closestCity`: `[ MANCHESTER, LONDON, LIVERPOOL, BIRMINGHAM, EDINBURGH, GLASGOW, NEWCASTLE_UPON_TYNE, DUBLIN, BELFAST, LEEDS, BRISTOL, SWANSEA, CARDIFF, SOUTHAMPTON, GLOUCESTER, NORWICH, HULL, INVERNESS ]`

### GET Requests
A GET request made to the `/api/events` endpoint will retrieve a list of all events in the nearest supported city to the given location.
It requires a `geoHashEnc` request parameter, which corresponds to an AES-256 encrypted GeoHash representation of the user's location. This means that the front and backend need to be initialized with the same information for requests to be accepted. This is to ensure the security of user location data.

Example usage: `/api/events?geoHashEnc=<ENCRYPTED HASH>`. This returns a list of event objects and a 200 status if successful, or a 404 if no events can be found for the search term.

As part of Spring Boot Actuator, GET requests made to the `api/health` endpoint return live information on the status of the program.

### POST Requests

POST requests are accepted on the `/api/events` endpoint. You must send, at minimum, an event with a `title`, `location`, `type`, `closestCity`, and `startDate` as a JSON object in the request body or your request will be rejected.

If successful, the server returns the created object and a 201 status. If your request body does not follow the prescribed structure, the server instead returns a 400 status.

### PUT Requests

PUT requests are made on the `/api/events/{id}` endpoint, where `{id}` is the id of the entry you would like to modify. 

The body of the request should include a JSON object with a complete version of the updated object.

If successfully updated, the server returns the updated object with a 200 status.

### DELETE Requests

DELETE requests are made to the `/api/events/{id}` endpoint, where `{id}` is the id of the entry you want to delete.

If your request is successfully processed, the server returns a 200 status with a message describing the action taken. If the event is not found, the server instead returns a 404.

## Third-party Services & Caching

### Background

The RendezVenue API acts on behalf of the Android frontend to collect, filter through, and make readily available, any and all event content that it needs. This is its primary purpose, and while the user can freely access other user-created events, or even create their own, we are relying on Ticketmaster's Discovery API to furnish the 'For You' page with a more diverse and comprehensive list of events. 

In November 2022, Associate Professor of Economics Florian Ederer at Yale University's School of Management published an article estimating that Ticketmaster held a greater than 70% market share for ticketing and live events, marking it an unrivalled market leader. In addition to their market coverage, the Discovery API is extremely well-documented and powerful in the sense that it accepts highly-customised queries. Our decision could not have been made any easier; Ticketmaster it was to be.

In its current form, this is the only external API on which RendezVenue is reliant. There are a couple of caveats, however. 

Firstly, there were clear stipulations on data retention in the Terms of Service. We were not to "store any Event Content other than for reasonable periods in order to provide the service you are providing." Secondly, we were working to a default quota of 5000 API calls per day, rate-limited to 5 requests per second, and limited to 200 instances of Event per response. 

The solution: delegating automated cached management to Google Guava. It allows us to track data usage and flush stale data periodically, all the while enhancing response times on the frontend and limiting third-party requests. It felt too good to be true, but it works flawlessly; the proverbial two birds with one stone. The cache is automatically cleaned when data is deemed to have grown stale; that is, where it has either gone unused for a specific length of time, or where it has existed in the cache for more than a maximum amount of time, whichever comes first. Either way, this continual process of reviewing and resetting the cache keeps RendezVenue invariably compliant with Ticketmaster's Terms of Service.

### Specifics

Cache management utilities are largely confined to the TicketmasterService class. Within it, we leverage Spring's RestClient to handle HTTP requests and responses between the Discovery and RendezVenue APIs. It is configured to work with Jackson's ObjectMapper for converting JSON to Plain Old Java Objects (POJOs). We use a StringBuilder class to structure the URI, and thus form the parametrised query that Ticketmaster expects, and that which also satisfies user preferences at the frontend. It is this query that we pass on to RestClient. Once deserialised to a TicketmasterPage record, the response body is read by a parser class and events sorted to the nearest geographic centre. 

Instantiation of the TicketmasterService class not only sets the client and parser members, but also calls upon Guava's CacheLoader, CacheBuilder, and LoadingCache classes to set a cache and write to it new event content. Two inner classes inherit from the TimerTask utility class; they are called upon within the constructor to the TicketmasterService class, and are called again at fixed intervals to separately clean and invalidate the cache. 

These inner "maintenance" classes are called by an instance of the Timer class. To ensure that the scheduling of cache maintenance activities is ongoing for as long as the application is running, but that this does not prolong the application lifetime unnecessarily, we are running the Timer as a Daemon. In its current state, RendezVenue API executes cache cleaning after five minutes, and periodically at minute intervals thereafter. Invalidation of all cached content occurs at 50 minute intervals to ensure that frequent user activity and requests do not persist cached data for an unreasonable length of time. This further guards against overloading of cache memory.  


## Filtering by Location

### Background

To filter by proximity to the user, our approach relies on repeated applications of the Haversine formula to calculate the arc distance along a geodesic given the point latitudes and longitudes. We evaluate these distances relative to the user's location, and do so for each other location in turn. These operations, nevertheless, are computationally resource-intensive; for each new user, and as we look to expand our list of supported locations, these calculations grow exponentially in number. For benefits in stability and scalability going forward, and so not to intentionally overburden the frontend, location filtering is entirely confined to the backend.

_NB:_ All locations are GeoHash encoded, which is made necessary by the very fact that Ticketmaster are adopting GeoHash representations over the traditional, but soon-to-be-deprecated, latitude and longitudinal coordinate pairings. We have made use of the _ElasticSearch Geometry_ module for converting between the GeoHash and GCS tuple formats.

### The Problem

To retrieve location-specific event content, RendezVenue API at the very least needs access to the user's location for reference. Now, to provide some context, the team at Jenerics Software puts the utmost priority on preserving the confidentiality and integrity of user-sourced credentials. So the idea of transmitting sensitive user data across potentially unprotected networks was a real cause for concern. 

The solution: end-to-end 256-bit AES encryption. General consensus has it that AES-256 is the absolute gold standard for encryption, so it seemed a natural choice. Fortunately, the `javax.crypto` package has a lot to offer in that regard. When a GET request is made to `/api/events` endpoint, RendezVenue API takes the location parameter and passes it to a parser class. Here the ciphertext is transformed back to its original plaintext GeoHash, and this should point to the user's location at the time of request. 

The means to collect, sort, and filter, objects of type Location by their associated GeoHash encoding is provided to the parser by a LocationUtils class. This resulting list informs the structure of requests made to Ticketmaster's Discovery API, on one count, and our own PostgreSQL database instance storing user-created content on the other. 

### Generating Keys 

RendezVenue handles encryption with 256-bit Secure Keys.

As it currently stands, the keys are password-generated. We had hoped to implement CSPRNGs to render salts less predictable, but given the time constraints, we are provisionally working from a set of precomputed values. The same can be said for the Initialisation Vector which is random yet precomputed. These sensitive tokens are abstracted away to secure files which are, unsurprisingly, not made publically accessible.

> [!IMPORTANT]  
> This is an early exploration of AES encryption fundamentals, and its integration into RendezVenue is foremost a tentative start.
>
> This unusually open discussion on security simply serves to document our approach. It will be heavily revised in future versions.

## Future Considerations
- Automatic filtering of user-created events deemed controversial, overtly provocative, politically-insensitive, or in direct contravention of laws within their local jurisdiction.
- Establishing a new Event Manager role with elevated permissions and separate from the default user profile.
- Third-party authentication to guard against unauthorised access or modifications to other user-created content,
- Expanding coverage of edge cases in testing.
- Refining search functionalities for snappier response times and better targeting of content specific to the user's location.
- Greater variability on endpoint parameters for flexibility in filtering event content by type, genre, date, artist etc.

## FAQs

**On which external APIs does RendezVenue depend?**

Our ethos centres on limiting over-reliance on external APIs. We rely solely on Ticketmaster's Discovery API. 

**Where can I get RendezVenue?**

This is a work in progress. Please be aware that some of the critical files, upon which this app is reliant for AES encryption, will not be made available with this release. 

**Is RendezVenue available in Kotlin?**

Not presently. This project was foremost an exercise in developing a full-stack Android application in Java. 

**How long is a piece of string?**

Great question. For an empty string, about 40 bytes, I believe.

## Bugs and Contributions

If you find any bugs, please create an issue on the issues page of this repository.

As this project is exclusive to the team at Jenerics Software, we don't plan on accepting any external contributions at this time.
