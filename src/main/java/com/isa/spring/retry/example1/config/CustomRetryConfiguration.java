package com.isa.spring.retry.example1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@PropertySource("application.properties")
@ComponentScan("com.isa.spring.retry")
public class CustomRetryConfiguration {
    private final RetryProperties retryProperties;

    @Autowired
    public CustomRetryConfiguration(RetryProperties retryProperties) {
        this.retryProperties = retryProperties;
    }

    @Bean
    public RetryTemplate retryTemplate(BackOffPolicy backOffPolicy, RetryPolicy retryPolicy, RetryListener retryListener) {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setThrowLastExceptionOnExhausted(false);
        retryTemplate.setListeners(new RetryListener[]{retryListener});
        return retryTemplate;
    }

    @Bean
    public BackOffPolicy backOffPolicy() {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(retryProperties.getBackOffInitialInterval());
        backOffPolicy.setMaxInterval(retryProperties.getBackOffMaxInterval());
        backOffPolicy.setMultiplier(retryProperties.getMaxAttempts());
        return backOffPolicy;
    }

    @Bean
    public RetryPolicy retryPolicy() {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(retryProperties.getMaxAttempts());
        return retryPolicy;
    }

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
