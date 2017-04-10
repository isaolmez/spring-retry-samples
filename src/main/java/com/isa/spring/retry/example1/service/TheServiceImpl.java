package com.isa.spring.retry.example1.service;

import org.springframework.stereotype.Service;

@Service
public class TheServiceImpl implements TheService {
    @Override
    public String perform() {
        throw new RuntimeException("Planned");
    }

    @Override
    public String performWithRecover() {
        throw new RuntimeException("Planned");
    }
}
