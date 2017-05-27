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
                                                                 
                                                                 
REST API Template (Java+Netty+Camel)
=====================================

## How do you run it?

You will need [gradle](https://gradle.org/) run and build the template. 

Mode | How | How in debug?
--- | --- | ---
Development option 1| `gradle run` | `gradle run --debug-jvm`
Development option 2| run `com.travelstart.api.Boot` from IDE | run `com.travelstart.api.Boot` in debug mode in IDE
Externally | `gradle installDist`, start script in `build/install/java-rest-template/bin/java-rest-template` | ? 
Docker | TODO | TODO

## Things to know
- REST Routes are defined in `com.travelstart.api.RestRoutes`
- Errors are handled in `com.travelstart.api.handler.ErrorHandler`
- Request/Response content handler for logging `com.travelstart.api.handler.LoggingHandler`
- Runs on port 8890 by default - see `com.travelstart.api.Boot`
- Uses CPU Core * 2 workers - see `com.travelstart.api.Boot`

Swagger support:
`docker create --rm --name swagger-rest-template -p 8081:8080 -e API_URL=http://localhost:8890/api-doc/swagger.json swaggerapi/swagger-ui:v2.2.9`
