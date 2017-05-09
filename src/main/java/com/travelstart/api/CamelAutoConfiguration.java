package com.travelstart.api;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelAutoConfiguration {
    private Logger log = LoggerFactory.getLogger(CamelAutoConfiguration.class);

    @Autowired(required = false)
    private RoutesBuilder[] routesBuilders;

    @Autowired
    private CamelContext context;

    @PostConstruct
    public void init() throws Exception {
        if (routesBuilders != null) {
            for (RoutesBuilder routesBuilder : routesBuilders) {
                log.info("adding route builder: {}", routesBuilder.getClass().getName());
                context.addRoutes(routesBuilder);
            }
        }
    }

}