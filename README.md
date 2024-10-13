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

## Third-party Services & Caching

 to a default quota of 5000 API calls per day, rate limited to 5 requests per second, and limited to 200 instances of Event per response.
To keep compliant with the Discovery API's Terms of Service, for which there were clear stipulations on data retention, we made use of Google Guava for automatic cache management. This gave us the ability to store Ticketmaster's proprietary data, giving multiple users, within a set geographic area, the ability to retrieve content instantaneously and tailored to meet their particular search criteria. The cache is automatically cleaned when data is deemed to have grown stale; that is, where it has either gone unused for a specific length of time, or where it has existed in the cache for more than a maximum amount of time, whichever comes first. 

Guava was an easy choice for this case, as it is extremely well documented and was quick to integrate into our program - a key consideration given the acute time constraints under which we were operating. 

 Similarly, we had to keep compliant to their terms on data retention. This forced an immediate revision and a partial overhaul of our design for the backend. Yet we were adamant to keep using Ticketmaster. The solution: delegating automated cached management to Google Guava. It allows us to track data usage and flush stale data periodically, all the while enhancing response times on the frontend and limiting third-party requests. It felt too good to be true, but it works flawlessly; the proverbial two birds with one stone. 

## Table of Contents
- [Usage](#Usage)
    - [JSON Structure](#JSON-Structure)
    - [GET Requests](#GET-Requests)
    - [POST Requests](#POST-Requests)
    - [PUT Requests](#PUT-Requests)
    - [DELETE Requests](#DELETE-Requests)
- [Bugs and Contributions](#Bugs-and-Contributions)


## Usage
### JSON Structure
Your requests should use the event JSON structure, an event looks like this:
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
A GET made to the `/api/events` endpoint will retrieve a list of all events in the nearest supported city to the given location.
Locations are given as geo-hashes which are encrypted with AES Symmetric Encryption. This means that the front and backend need to be initialized with the same information for requests to be accepted. This is to ensure the security of user location information.

Example usage: `/api/events?geoHashEnc=<ENCRYPTED HASH>`. This will return a list of event objects and a 200 status if successful, or a 404 if no events can be found for the search term.

A GET made to the `/health` endpoint will give you information on the status of the program.

### POST Requests

POSTs are accepted on the `/api/events` endpoint. You must send, at minimum, an event with a `title`, `location`, `type`, `closestCity`, and `startDate` as a JSON object in the request body or your request will be rejected.

If successful, you the server will return the created object and a 201 status. If you've given invalid input in the body, it will return a 400 status.

### PUT Requests

PUTs are made on the `/api/events/{id}` endpoint, where `{id}` is the id of the entry you would like to modify. The body of the request should include a JSON object with a complete version of the updated object.

If successfully modifying an entry, the server will return the updated object with a 200 status.

### DELETE Requests

DELETEs are requested on the `/records/{id}` endpoint, where `{id}` is the id of the entry you want to delete.

If your request is successfully processed, the server will return a 200 status with a message describing the action taken. If the event is not found, a 404 will be returned.


## Bugs and Contributions

If you find any bugs, please create an issue on the issues page of this repo.

As this project was made as part of a bootcamp, we don't plan on accepting any contributions to the code at this time.
