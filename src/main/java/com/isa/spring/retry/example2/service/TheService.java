package com.isa.spring.retry.example2.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface TheService {
    @Retryable(include = RuntimeException.class,
               maxAttemptsExpression = "#{@retryProperties.getMaxAttempts()}",
               backoff = @Backoff(delayExpression = "#{@retryProperties.getBackOffInitialInterval()}",
                                  maxDelayExpression = "#{@retryProperties.getBackOffMaxInterval" + "()}",
                                  multiplierExpression = "#{@retryProperties.getBackOffIntervalMultiplier()}"))
    String perform();

    @Recover
    String recover(RuntimeException exception);
}
