package tpm;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestRoutes {
    @Autowired
    private CamelContext camelContext;

    @Autowired
    private SayHandler sayHandler;

    @Autowired
    private NewRelicHandler newRelicHandler;

    @PostConstruct
    public void initRoutes() throws Exception {
            final RouteBuilder routeBuilder = new RouteBuilder() {
                @Override
                public void configure() {
                    // @formatter:off
                    
                    interceptFrom("*")
                        .to("log:headers?level=INFO&showHeaders=true&multiline=true")
                        .bean(newRelicHandler)
                    ;
                    
                    restConfiguration("netty4-http")
                        .bindingMode(RestBindingMode.json)
                        .endpointProperty("nettySharedHttpServer", "#sharedNettyHttpServer")
                    ;
                    
                    
                    rest().path("/say")
                        .get("/hello")
                        .produces("application/json")
                        .route().bean(sayHandler)
                    ;
                    
                    rest().path("/api")
                        .get("/ping.json")
                        .produces("application/json")
                        .bindingMode(RestBindingMode.off)
                        .route().transform(constant("{\"ok\":true}"))
                    ;
                    
                    
                    // @formatter:on   
                }
            };
            routeBuilder.addRoutesToCamelContext(camelContext);
    }
    

}
