package tpm;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.camel.CamelContext;
import org.apache.camel.component.netty4.http.DefaultNettySharedHttpServer;
import org.apache.camel.component.netty4.http.NettySharedHttpServerBootstrapConfiguration;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("tpm")
public class Boot {
    private Logger log = LoggerFactory.getLogger(Boot.class);

    @Autowired
    private CamelContext context;

    @PostConstruct
    public void init() {
        log.info("start.");

    }

    @PreDestroy
    public void down() {}

    @Bean("configuration")
    public NettySharedHttpServerBootstrapConfiguration nettyConfig() {
        NettySharedHttpServerBootstrapConfiguration config =
                new NettySharedHttpServerBootstrapConfiguration();

        config.setHost("0.0.0.0");
        config.setPort(8890);
        config.setCompression(true);
        config.setWorkerCount(32);
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
    public CamelContext buildCamelContext() {
        final SpringCamelContext camelContext = new SpringCamelContext();
        camelContext.setMessageHistory(false); // true on non-prod
        camelContext.setStreamCaching(true);
        //camelContext.disableJMX();
        return camelContext;
    }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Boot.class);

        //        final ClassPathXmlApplicationContext context =
        //                new ClassPathXmlApplicationContext(new String[] {"classpath:spring-context.xml"});
        context.registerShutdownHook();
        context.start();
    }

}
