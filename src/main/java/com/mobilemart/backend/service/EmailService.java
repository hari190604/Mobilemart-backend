package com.mobilemart.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String brevoApiKey;

    @Value("${spring.mail.username:rlhp1907@gmail.com}")
    private String fromEmail;

    private final String BREVO_API_URL = "https://api.brevo.com/v3/smtp/email";
    private final RestTemplate restTemplate = new RestTemplate();

    public void sendWelcomeEmail(String toEmail, String fullName) {
        String htmlContent = "<html><body>"
                + "<h2>Welcome to MobileMart, " + fullName + "!</h2>"
                + "<p>We're thrilled to have you with us. Explore the latest smartphones and accessories on our platform.</p>"
                + "<p>Happy Shopping!</p>"
                + "<br>"
                + "<p>Best regards,<br>The MobileMart Team</p>"
                + "</body></html>";
                
        sendEmailViaBrevo(toEmail, fullName, "Welcome to MobileMart!", htmlContent);
    }

    public void sendOtpEmail(String toEmail, String otp) {
        String htmlContent = "<html><body>"
                + "<h2>MobileMart Security Verification</h2>"
                + "<p>You requested a one-time password (OTP) verification. Please use the code below to proceed:</p>"
                + "<h1 style='letter-spacing: 4px; padding: 10px; background-color: #f3f4f6; color: #111827; display: inline-block; border-radius: 4px;'>" + otp + "</h1>"
                + "<p>If you did not request this verification, please ignore this email.</p>"
                + "<br>"
                + "<p>Best regards,<br>The MobileMart Team</p>"
                + "</body></html>";
                
        sendEmailViaBrevo(toEmail, "Valued Customer", "MobileMart - Your Verification Code", htmlContent);
    }
    
    private void sendEmailViaBrevo(String toEmail, String toName, String subject, String htmlContent) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("api-key", brevoApiKey);

            Map<String, Object> sender = new HashMap<>();
            sender.put("name", "MobileMart Security");
            sender.put("email", fromEmail);

            Map<String, Object> to = new HashMap<>();
            to.put("email", toEmail);
            to.put("name", toName);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("sender", sender);
            requestBody.put("to", Collections.singletonList(to));
            requestBody.put("subject", subject);
            requestBody.put("htmlContent", htmlContent);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(BREVO_API_URL, entity, String.class);
            System.out.println("Email sent successfully via Brevo API Setup. Status: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("=========================================");
            System.out.println("BREVO HTTP API FAILURE DIAGNOSTIC");
            System.out.println("Target: " + toEmail);
            System.err.println("Error Mechanism: " + e.getMessage());
            System.out.println("=========================================");
            e.printStackTrace();
        }
    }
}
