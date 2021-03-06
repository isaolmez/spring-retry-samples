package com.isa.spring.retry.declarative;

import com.isa.spring.retry.declarative.client.ServiceClient;
import com.isa.spring.retry.declarative.config.RetryConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(RetryConfiguration.class)) {

            ServiceClient serviceClient = context.getBean(ServiceClient.class);
            try {
                serviceClient.callWithRetry();
            } catch (Exception e) {
                LOG.error("Exception: ", e);
            }
        }
    }
}
