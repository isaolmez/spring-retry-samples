package com.isa.spring.retry.example2;


import com.isa.spring.retry.example2.client.ServiceClient;
import com.isa.spring.retry.example2.config.RetryConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RetryConfiguration.class, ServiceClientTest.TestConfiguration.class})
@TestPropertySource(properties = {"retry.attempts.max=5", "retry.backoff.interval.initial=10", "retry.backoff.interval.max=100", "retry.backoff.interval.multiplier=2"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ServiceClientTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ServiceClient serviceClient;

    @Autowired
    private TestConfiguration.TestRetryListener retryListener;

    @Test
    public void shouldRetry_WithMaxAttempts() {
        final int expectedAttempts = 5;
        final String expectedResult = "Recovered";
        String actualResult = null;
        try {
            actualResult = serviceClient.callWithRetry();
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
        }

        assertEquals(expectedResult, actualResult);
        assertEquals(expectedAttempts, retryListener.getCount());
    }

    @Configuration
    static class TestConfiguration {
        @Bean
        @Primary
        public RetryListener retryListener() {
            return new TestRetryListener();
        }

        static class TestRetryListener extends RetryListenerSupport {

            private int count = 0;

            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                count++;
            }

            public int getCount() {
                return count;
            }
        }
    }
}
