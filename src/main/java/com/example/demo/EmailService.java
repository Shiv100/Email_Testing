package com.example.demo;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService {

    private static final Logger logger = Logger.getLogger(EmailService.class.getName());

    @Autowired
    private SendGrid sendGrid;

    @Value("${tracking.base-url}")
    private String trackingBaseURL;

    public String sendEmail(EmailRequest request) throws IOException {
        if (request.getTo() == null || request.getTo().isEmpty()) {
            return "❌ Error: Recipient email list is empty!";
        }

        int successCount = 0;

        for (String recipientEmail : request.getTo()) {
            try {
                String encodedEmail = URLEncoder.encode(recipientEmail, StandardCharsets.UTF_8);
                String timestamp = String.valueOf(System.currentTimeMillis());

                String trackingPixelURL = trackingBaseURL + "/track/open?email=" + encodedEmail + "&t=" + timestamp;
                String clickTrackingURL = trackingBaseURL + "/track/click?email=" + encodedEmail
                        + "&url=" + URLEncoder.encode("https://avair.ai/", StandardCharsets.UTF_8);

                String trackingPixel = "<img src='" + trackingPixelURL + "' width='1' height='1' style='opacity:0;' alt='.' />";
                String trackedLink = "<a href='" + clickTrackingURL + "' target='_blank'>Click Here</a>";

                String emailContent = "<html><body>"
                        + request.getBody()
                        + "<br><br>" + trackedLink
                        + "<br><br>" + trackingPixel
                        + "</body></html>";

                Email from = new Email("avairai.pvt.ltd@gmail.com"); // use verified sender
                Email to = new Email(recipientEmail);
                Content content = new Content("text/html", emailContent);
                Mail mail = new Mail(from, request.getSubject(), to, content);

                Request sgRequest = new Request();
                sgRequest.setMethod(Method.POST);
                sgRequest.setEndpoint("mail/send");
                sgRequest.setBody(mail.build());

                Response response = sendGrid.api(sgRequest);

                if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                    successCount++;
                    logger.info("✅ Email sent successfully to " + recipientEmail);
                } else {
                    logger.warning("⚠️ Failed to send email to " + recipientEmail + ": " + response.getBody());
                }

            } catch (IOException e) {
                logger.log(Level.SEVERE, "❌ Exception while sending email to " + recipientEmail, e);
            }
        }

        return "📬 Emails sent: " + successCount + "/" + request.getTo().size();
    }
}
