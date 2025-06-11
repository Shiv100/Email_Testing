package com.example.demo;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TrackingService {

    private final ConcurrentHashMap<String, EmailTracking> trackingMap = new ConcurrentHashMap<>();

    public void trackOpen(String email) {
        EmailTracking entry = trackingMap.computeIfAbsent(email, EmailTracking::new);
        entry.setOpened(true);
        entry.setOpenTime(LocalDateTime.now());
    }

    public void trackClick(String email) {
        EmailTracking entry = trackingMap.computeIfAbsent(email, EmailTracking::new);
        entry.setClicked(true);
        entry.setClickTime(LocalDateTime.now());
    }

    public Collection<EmailTracking> getAllRecords() {
        return trackingMap.values();
    }
}
