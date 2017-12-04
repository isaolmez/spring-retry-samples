package com.isa.spring.retry.declarative.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@PropertySource("application.properties")
@ComponentScan("com.isa.spring.retry.declarative")
public class RetryConfiguration {

    @Bean
    public RetryListener retryListener() {
        return new LoggingRetryListener();
    }

    private static class LoggingRetryListener implements RetryListener {

        private static final Logger LOG = LoggerFactory.getLogger(LoggingRetryListener.class);

        @Override
        public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
            LOG.info("Inside open()");
            return true;
        }

        @Override
        public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
            LOG.info("Inside close()");
        }

        @Override
        public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
            LOG.error("Inside onError()");
        }
    }
}
