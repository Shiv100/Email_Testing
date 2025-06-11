package com.example.demo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/track")
public class TrackingController {

    private static final Logger logger = Logger.getLogger(TrackingController.class.getName());

    @Autowired
    private TrackingService trackingService;

    @GetMapping("/open")
    public void trackOpen(@RequestParam("email") String email, HttpServletResponse response) throws IOException {
        logger.info("Email opened: " + email);

        // Track the open event in memory
        trackingService.trackOpen(email);

        // Return a 1x1 transparent pixel image
        response.setContentType("image/gif");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        response.setContentLength(43);
        response.getOutputStream().write(new byte[]{
                71, 73, 70, 56, 57, 97, 1, 0, 1, 0, (byte) 0xF7, 0, 0,
                (byte) 255, (byte) 255, (byte) 255, 0, 0, 0, 33, (byte) 249, 4, 1,
                0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68,
                1, 0, 59
        });
        response.getOutputStream().flush();
    }

    @GetMapping("/click")
    public void trackClick(@RequestParam("email") String email, @RequestParam("url") String url, HttpServletResponse response) throws IOException {
        logger.info("Email clicked: " + email + ", URL: " + url);

        // Track the click event in memory
        trackingService.trackClick(email);

        // Redirect to the actual URL after tracking
        response.sendRedirect(url);
    }
}
