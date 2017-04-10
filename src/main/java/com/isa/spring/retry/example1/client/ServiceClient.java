package com.isa.spring.retry.example1.client;

import com.isa.spring.retry.example1.service.TheService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceClient {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceClient.class);

    private final TheService theService;

    private final RetryTemplate retryTemplate;

    @Autowired
    public ServiceClient(TheService theService, RetryTemplate retryTemplate) {
        this.theService = theService;
        this.retryTemplate = retryTemplate;
    }

    public String callWithRetry() {
        return retryTemplate.execute(new RetryCallback<String, RuntimeException>() {
            @Override
            public String doWithRetry(RetryContext context) {
                LOG.info("Inside doWithRetry()");
                return theService.perform();
            }
        });
    }

    public String callWithRetryAndRecover() {
        return retryTemplate.execute(new RetryCallback<String, RuntimeException>() {
            @Override
            public String doWithRetry(RetryContext context) {
                LOG.info("Inside doWithRetry()");
                return theService.perform();
            }
        }, new RecoveryCallback<String>() {
            @Override
            public String recover(RetryContext context) throws Exception {
                LOG.info("Inside recover()");
                return "Recovered";
            }
        });
    }
}
