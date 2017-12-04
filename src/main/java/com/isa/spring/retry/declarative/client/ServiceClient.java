package com.isa.spring.retry.declarative.client;

import com.isa.spring.retry.declarative.service.TheService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceClient.class);

    private final TheService theService;

    @Autowired
    public ServiceClient(TheService theService) {
        this.theService = theService;
    }

    public String callWithRetry() {
        return theService.perform();
    }
}
