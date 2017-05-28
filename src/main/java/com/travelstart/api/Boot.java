package com.travelstart.api;

import org.apache.camel.component.netty4.http.DefaultNettySharedHttpServer;
import org.apache.camel.component.netty4.http.NettySharedHttpServerBootstrapConfiguration;
import org.apache.camel.spring.CamelContextFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.travelstart.api")
public class Boot {
    private Logger log = LoggerFactory.getLogger(Boot.class);

    public static int PORT = new Integer(System.getProperty("server.port", "8890"));

    // bind address
    public static String HOST = System.getProperty("server.host", "localhost");

    public static int WORKER_COUNT = new Integer(
            System.getProperty("server.workers", "" + (Runtime.getRuntime().availableProcessors() * 2)));

    @Bean("configuration")
    public NettySharedHttpServerBootstrapConfiguration nettyConfig() {
        NettySharedHttpServerBootstrapConfiguration config =
                new NettySharedHttpServerBootstrapConfiguration();

        config.setHost(HOST);
        config.setPort(PORT);
        config.setCompression(true);
        config.setWorkerCount(WORKER_COUNT);
        return config;
    }


    @Bean(name = "sharedNettyHttpServer", initMethod = "start", destroyMethod = "stop")
    public DefaultNettySharedHttpServer server(NettySharedHttpServerBootstrapConfiguration config) {
        DefaultNettySharedHttpServer server =
                new DefaultNettySharedHttpServer();
        server.setNettyServerBootstrapConfiguration(config);
        return server;
    }

    @Bean
    CamelContextFactoryBean camelContextFactory() {
        final CamelContextFactoryBean context = new CamelContextFactoryBean();
        context.setId("camel");
        context.setMessageHistory(Boolean.FALSE.toString());
        context.setStreamCache(Boolean.TRUE.toString());
        return context;
    }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Boot.class);
        context.registerShutdownHook();
        context.start();
    }

}
