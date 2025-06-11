package com.example.demo;

import java.time.LocalDateTime;

public class EmailTracking {
    private String email;
    private boolean opened;
    private LocalDateTime openTime;
    private boolean clicked;
    private LocalDateTime clickTime;

    public EmailTracking(String email) {
        this.email = email;
    }

    // Getters and Setters for all fields
    public String getEmail() { return email; }

    public boolean isOpened() { return opened; }

    public void setOpened(boolean opened) { this.opened = opened; }

    public LocalDateTime getOpenTime() { return openTime; }

    public void setOpenTime(LocalDateTime openTime) { this.openTime = openTime; }

    public boolean isClicked() { return clicked; }

    public void setClicked(boolean clicked) { this.clicked = clicked; }

    public LocalDateTime getClickTime() { return clickTime; }

    public void setClickTime(LocalDateTime clickTime) { this.clickTime = clickTime; }
}
