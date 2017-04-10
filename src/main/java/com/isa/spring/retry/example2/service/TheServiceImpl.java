package com.isa.spring.retry.example2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TheServiceImpl implements TheService {
    private static final Logger LOG = LoggerFactory.getLogger(TheServiceImpl.class);

    @Override
    public String perform() {
        throw new RuntimeException("Planned");
    }

    @Override
    public String recover(RuntimeException exception) {
        LOG.error("Inside recover(): {}", exception.getMessage());
        return "Recovered";
    }
}
