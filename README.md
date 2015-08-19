# OpeningHoursParser

This is a very simplistic parser for string values according to the OSM opening hours specification see http://wiki.openstreetmap.org/wiki/Key:opening_hours/specification

It is currently work in progress and not complete yet, however it does parse 108'455 of 122'100 test strings when parsing case insensitive and allowing 3 letter weekday abbreviations. Of the remaining 13'645, 10'569 are likely valid lexical errors and a large part of the rest has obvious issues.


