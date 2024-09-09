package com.myserver.springserver.services.implementation;

import com.myserver.springserver.services.WelcomeService;
import org.springframework.stereotype.Service;

@Service
public class WelcomeServiceImpl implements WelcomeService {
    @Override
    public String greeting(String text) {
        return "Welcome to Film service";
    }
}
