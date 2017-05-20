REST API Template (Java+Netty+Camel)
=====================================

## How do run it?

You will need [gradle](https://gradle.org/) run and build the template. 

Mode | How | How in debug?
--- | --- | ---
Development | `gradle run` | `gradle run --debug-jvm`
Development | run `com.travelstart.api.Boot` from IDE | run `com.travelstart.api.Boot` in debug mode in IDE
Externally | `gradle installDist`, start script in `build/install/java-rest-template/bin/java-rest-template` | ? 
Docker | TODO | TODO

## Things to know
- REST Routes are defined in `com.travelstart.api.RestRoutes`
- Errors are handled in `com.travelstart.api.handler.ErrorHandler`
- Request/Response content handler for logging `com.travelstart.api.handler.LoggingHandler`
- Runs on port 8890 - see `com.travelstart.api.Boot`
- Uses CPU Core * 2 workers - see `com.travelstart.api.Boot`

