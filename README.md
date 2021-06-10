[![build status](https://github.com/simonpoole/OpeningHoursParser/actions/workflows/javalib.yml/badge.svg)](https://github.com/simonpoole/OpeningHoursParser/actions) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OpeningHoursParser&metric=alert_status)](https://sonarcloud.io/dashboard?id=OpeningHoursParser) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=OpeningHoursParser&metric=coverage)](https://sonarcloud.io/dashboard?id=OpeningHoursParser) [![sonarcloud bugs](https://sonarcloud.io/api/project_badges/measure?project=OpeningHoursParser&metric=bugs)](https://sonarcloud.io/component_measures?id=OpeningHoursParser&metric=bugs) [![sonarcould maintainability](https://sonarcloud.io/api/project_badges/measure?project=OpeningHoursParser&metric=sqale_rating)](https://sonarcloud.io/component_measures?id=OpeningHoursParser&metric=Maintainability) [![sonarcloud security](https://sonarcloud.io/api/project_badges/measure?project=OpeningHoursParser&metric=security_rating)](https://sonarcloud.io/component_measures?id=OpeningHoursParser&metric=Security) [![sonarcloud reliability](https://sonarcloud.io/api/project_badges/measure?project=OpeningHoursParser&metric=reliability_rating)](https://sonarcloud.io/component_measures?id=OpeningHoursParser&metric=Reliability)

# OpeningHoursParser

This is a very simplistic parser for string values according to the [OSM opening hours specification][opening-hours-specification]. It is used in a number of OpenStreetMap projects, for example
in [Vespucci](https://github.com/MarcusWolschon/osmeditor4android). As the opening hours specification is currently reasonably stable you shouldn't expect lots of activity in this repository.

It parses 147'002 (91%) of 161'268 unique test strings in non-strict mode. The remaining 14'266 are likely valid errors, spot checking shows that they have obvious issues. In strict mode further 15'807 fail (total 30'073).

Deviations from the grammar as of [this version of the opening hours specification][opening-hours-grammar-specification] in all modes:

 * case-insensitive
 * leading 0s in times optional
 * unicode EN DASH (U+2013) EM DASH (U+2014) characters are allowed for hyphen
 * various unicode whitespace characters are ignored

In non-strict mode the following further differences are allowed:

 * three-character weekday abbreviations
 * German two-letter weekday abbreviations
 * times extending in to the next day that are missing the extra 24 hours are corrected
 * single 0 for minutes
 * minutes in times optional
 * ignore spaces and more than one leading zeros in minutes
 * "." and "h" as minutes separators
 * AM and PM time specifications are allowed (plus A.M. and P.M.) 
 * holidays in weekday range
 * superfluous ":" after weekday range
 * 24/7 rules with preceding selectors are corrected to 00:00-24:00 time spans
 * list of month days after months ( Jan 1,4,5 )
 * " to " in lieu of a hyphen for ranges
 * date ranges that do not have the month day specified are corrected (Jan - Feb 15 -> Jan 1 - Feb 15)

Converting the data structures generated by parsing back to strings will result in correct data according to the specification.

## Usage

``` java
try {
	OpeningHoursParser parser = new OpeningHoursParser(
		new ByteArrayInputStream(line.getBytes()));
	List<Rule> rules = parser.rules(strict);
	// ...
} catch(OpeningHoursParseException e) {
	// ...
	// e.getExceptions() will return a List<OpeningHoursParseException> 
	// containing more than one Exception if more than one issue was found 
}
```

Detailed documentation can be found in the [JavaDoc](http://www.javadoc.io/doc/ch.poole/OpeningHoursParser/0.23.3).


## Including in your project

We publish releases to [Maven Central](https://repo1.maven.org/maven2/ch/poole/OpeningHoursParser/).
The following snippets for `build.gradle` will make OpeningHoursParser available in your Gradle project:

``` groovy
repositories {
    mavenCentral()
}
```

``` groovy
dependencies {
    compile "ch.poole:OpeningHoursParser:0.23.3"
}
```


[opening-hours-specification]: http://wiki.openstreetmap.org/wiki/Key:opening_hours/specification
[opening-hours-grammar-specification]: http://wiki.openstreetmap.org/w/index.php?title=Key:opening_hours/specification&oldid=1075290

## Tests

Besides some unit tests, we run the parser on ~160'000 and compare with previous output to detect any changes in behaviour. If you are running these on windows, you may need to add
``org.gradle.jvmargs=-Dfile.encoding=UTF-8``
to your gradle.properties to force correct use of the UTF-8 encoded test input. 

## Building

The project uses [gradle](https://gradle.org/) for building. Standard gradle tasks for the java plugin can be found here [https://docs.gradle.org/current/userguide/java_plugin.html](https://docs.gradle.org/current/userguide/java_plugin.html). They can be invoked on the command line by running ``gradlew`` or ``gradlew.bat`` with the name of the task, for example
``gradlew jar`` to create the jar archive. 

Note: the project has no runtime dependencies, and the jar file resulting from the build process is self-sufficient.

## Contributing

Pull requests are welcome. 

As this library is used in a number of Android projects please restrict the use of Java 8 features and APIs to the Android supported subset see https://developer.android.com/studio/write/java8-support. Check that any APIs used are supported on all Android platform API versions 10 and later, this is mainly an issue for APIs that were introduced with Java 7. This restriction will likely be relaxed for future versions. 

Currently, the library is self-contained and doesn't have any runtime dependencies outside of basic Java support, it would be nice if we can keep it like that.

## Translation

The error messages produced by the library are translated on [transifex](https://transifex.com/), see [https://www.transifex.com/openstreetmap/openinghoursparser/](https://www.transifex.com/openstreetmap/openinghoursparser/).
