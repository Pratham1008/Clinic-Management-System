package com.coder_crushers.clinic_management.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SelfPingTask {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PING_URL = "https://clinic-management-system-xczz.onrender.com/ping";

    @Scheduled(fixedRate = 60000)
    public void pingSelf() {
        try {
            restTemplate.getForObject(PING_URL, String.class);
            System.out.println("Pinged self to prevent sleep");
        } catch (Exception e) {
            System.err.println("Ping failed: " + e.getMessage());
        }
    }
}
