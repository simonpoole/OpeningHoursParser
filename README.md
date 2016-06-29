# OpeningHoursParser

This is a very simplistic parser for string values according to the OSM opening hours specification see http://wiki.openstreetmap.org/wiki/Key:opening_hours/specification

It parses 143'070 (89%) of 161'268 test strings when parsing case insensitive, allowing 3 letter weekday abbreviations and fixing up extended time values that are off by 24h. The remaining 18'198 are likely valid errors, spot checking shows that they have obvious issues. In strict mode further 11'480 fail.



