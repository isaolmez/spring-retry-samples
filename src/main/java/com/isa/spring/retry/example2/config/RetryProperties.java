package com.isa.spring.retry.example2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RetryProperties {

    @Value("${retry.attempts.max}")
    private int maxAttempts;

    @Value("${retry.backoff.interval.initial}")
    private int backOffInitialInterval;

    @Value("${retry.backoff.interval.max}")
    private int backOffMaxInterval;

    @Value("${retry.backoff.interval.multiplier}")
    private int backOffIntervalMultiplier;

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getBackOffInitialInterval() {
        return backOffInitialInterval;
    }

    public void setBackOffInitialInterval(int backOffInitialInterval) {
        this.backOffInitialInterval = backOffInitialInterval;
    }

    public int getBackOffMaxInterval() {
        return backOffMaxInterval;
    }

    public void setBackOffMaxInterval(int backOffMaxInterval) {
        this.backOffMaxInterval = backOffMaxInterval;
    }

    public int getBackOffIntervalMultiplier() {
        return backOffIntervalMultiplier;
    }

    public void setBackOffIntervalMultiplier(int backOffIntervalMultiplier) {
        this.backOffIntervalMultiplier = backOffIntervalMultiplier;
    }
}
