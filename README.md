# UBX Test Project

## High level requirements
* Accept HTTP requests from vendor.
* Each request must contain Request ID, Audience Group Name and File Name (raw field names: id, name, filename).
* File must contain list of emails.
* App should store incoming requests in Redis.
* App should build Bloom Filter for each Audience Group using it's Name and corresponding list of emails (Request ID is not used).
* App should store Bloom Filters in Redis.
* App should store list of processed Requests in Redis.
* App should utilize multi-threading approach.

## Issues

* App does not tolerate failures. If file processing fails in the middle than information about whole Request will be lost. This is supposed to be fixed on next iteration.
