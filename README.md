REST API Template (Java+Netty+Camel)
=====================================

A nice, simple REST API template written in Java using [Camel](http://camel.apache.org/) with [Netty](https://github.com/netty/netty) which includs an ErrorHandler, [NewRelic integration](https://newrelic.com/) and a few sample requests.

## How do you build it ?

You will need [gradle](https://gradle.org/) build (and "deploy"). 

Mode | How | How in debug?
--- | --- | ---
Development option 1| `gradle run` | `gradle run --debug-jvm`
Development option 2| run `com.travelstart.api.Boot` from IDE | run `com.travelstart.api.Boot` in debug mode in IDE
Externally | `gradle installDist`, start script in `build/install/java-rest-template/bin/java-rest-template` | N/A
Docker | TODO | TODO

## Things to know
- REST Routes are defined in `com.travelstart.api.RestRoutes`
- Errors are handled in `com.travelstart.api.handler.ErrorHandler`
- Request/Response content handler for logging is in `com.travelstart.api.handler.LoggingHandler`
- Runs on port 8890 - see `com.travelstart.api.Boot`
- Uses CPU Core * 2 workers - see `com.travelstart.api.Boot`

