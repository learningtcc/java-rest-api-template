```
  ______                           __       __                __ 
 /_  __/_____ ____ _ _   __ ___   / /_____ / /_ ____ _ _____ / /_
  / /  / ___// __ `/| | / // _ \ / // ___// __// __ `// ___// __/
 / /  / /   / /_/ / | |/ //  __// /(__  )/ /_ / /_/ // /   / /_  
/_/  /_/    \__,_/  |___/ \___//_//____/ \__/ \__,_//_/    \__/  
    ____   ______ _____ ______   ___     ____   ____             
   / __ \ / ____// ___//_  __/  /   |   / __ \ /  _/             
  / /_/ // __/   \__ \  / /    / /| |  / /_/ / / /               
 / _, _// /___  ___/ / / /    / ___ | / ____/_/ /                
/_/ |_|/_____/ /____/ /_/    /_/  |_|/_/    /___/                
```       
                                                                 
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
- Runs on port 8890 by default - see `com.travelstart.api.Boot`
- Uses CPU Core * 2 workers - see `com.travelstart.api.Boot`

Swagger support:
`docker create --rm --name swagger-rest-template -p 8081:8080 -e API_URL=http://localhost:8890/api-doc/swagger.json swaggerapi/swagger-ui:v2.2.9`
