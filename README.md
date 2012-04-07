### magic-config-service
This project contains the source code used to create a deployable war for use in a java container (Apache Tomcat/Jetty etc) that manages a set of properties that can be accessed remotely via REST over HTTP. The basic implementation of a property repository utilizes a lightweight datastore. This keeps the footprint small and extremely portable.

## Documentation
1) To run the current example, do the following command:
<br>mvn jetty:run

2) Then in your browser go to the following URI's:
<br>http://localhost:8080/property/some/prop
<br>http://localhost:8080/property/some/prop?tags=development
<br>http://localhost:8080/property/some/prop?tags=production
    
3)  Write a JUnit Test in a different application that hits these URI's while it's still running and view the results.

4)  If you would like, change out the HashMap implementation with the FilePropertyService.  You
will need to create a properties file on your local disk at the URI of your choosing.  Please note
that this service can pick up changes to the properties file on the fly, and also allows the use
of tags in the properties file.

## Downloads
TBD

## Contributing
[Pull requests](http://help.github.com/send-pull-requests) are welcome!

## Staying in touch
Follow [@bluemagicsource](http://twitter.com/bluemagicsource)

## License
magic-config-service is released under version 2.0 of the
[Apache License](http://www.apache.org/licenses/LICENSE-2.0).
