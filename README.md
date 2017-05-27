```  
████████╗██████╗  █████╗ ██╗   ██╗███████╗██╗     ███████╗████████╗ █████╗ ██████╗ ████████╗
╚══██╔══╝██╔══██╗██╔══██╗██║   ██║██╔════╝██║     ██╔════╝╚══██╔══╝██╔══██╗██╔══██╗╚══██╔══╝
   ██║   ██████╔╝███████║██║   ██║█████╗  ██║     ███████╗   ██║   ███████║██████╔╝   ██║   
   ██║   ██╔══██╗██╔══██║╚██╗ ██╔╝██╔══╝  ██║     ╚════██║   ██║   ██╔══██║██╔══██╗   ██║   
   ██║   ██║  ██║██║  ██║ ╚████╔╝ ███████╗███████╗███████║   ██║   ██║  ██║██║  ██║   ██║   
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚══════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   
                                                                                            
██████╗ ███████╗███████╗████████╗     █████╗ ██████╗ ██╗                                    
██╔══██╗██╔════╝██╔════╝╚══██╔══╝    ██╔══██╗██╔══██╗██║                                    
██████╔╝█████╗  ███████╗   ██║       ███████║██████╔╝██║                                    
██╔══██╗██╔══╝  ╚════██║   ██║       ██╔══██║██╔═══╝ ██║                                    
██║  ██║███████╗███████║   ██║       ██║  ██║██║     ██║                                    
╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝       ╚═╝  ╚═╝╚═╝     ╚═╝
```       
                                                                 
REST API Template (Java+Netty+Camel+Swagger)
=====================================

A nice, simple REST API template written in Java using [Camel](http://camel.apache.org/) with [Netty](https://github.com/netty/netty) which includs an ErrorHandler, [NewRelic integration](https://newrelic.com/) and a few sample requests. Swagger support included as well.

## How do you build / run / develop it ?

You will need [gradle](https://gradle.org/) to build (and "deploy" [optional] ). 

Mode | How | How (in debug)
--- | --- | ---
Development option 1| `gradle run` | `gradle run --debug-jvm`
Development option 2| run `com.travelstart.api.Boot` from IDE | run `com.travelstart.api.Boot` in debug mode in IDE
Externally | `gradle installDist`, run script: `build/install/java-rest-template/bin/java-rest-template` | N/A
Docker | TODO | TODO

## Things to know
- REST Routes are defined in `com.travelstart.api.RestRoutes`
- Errors are handled in `com.travelstart.api.handler.ErrorHandler`
- Request/Response content handler for logging is in `com.travelstart.api.handler.LoggingHandler`
- Runs on port 8890 by default - see `com.travelstart.api.Boot`
- Uses CPU Core * 2 workers - see `com.travelstart.api.Boot`

## Example endpoints:
- `/api/ping.json` - POST/GET that returns a fixed string response
- `/say/hello/{message}` - GET with a parameter called `message`
- `/xxx/yyy` 

## Docker support
TODO

## Swagger support:
`docker create --rm --name swagger-rest-template -p 8081:8080 -e API_URL=http://localhost:8890/api-doc/swagger.json swaggerapi/swagger-ui:v2.2.9`

goto: `http://localhost:8081/` and enter `http://localhost:8890/api-doc/swagger.json` in the box
