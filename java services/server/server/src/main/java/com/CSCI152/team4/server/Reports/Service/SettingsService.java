package com.CSCI152.team4.server.Reports.Service;

import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private final TokenAuthenticator authenticator;

    @Autowired
    public SettingsService(TokenAuthenticator authenticator) {
        this.authenticator = authenticator;
    }
}
