# 🎟️ rendezvenue-api 📆

## An API for our event management app. Made as part of the Northcoders Java bootcamp.

`rendezvenue-api` is an api which collates data from our events database and the Ticketmaster Discovery API for our Android app. The app lets you find events  

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