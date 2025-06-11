package com.example.demo;

import jakarta.mail.*;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class BounceTrackingService {

    public void checkForBounces() {
        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "info.avair.ai@gmail.com", "lrha lhmm ehnx awwu");

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();

            for (Message message : messages) {
                if (message.getSubject().contains("Undeliverable")) {
                    System.out.println("🚨 Bounce detected: " + message.getSubject());
                }
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
