package com.mobilemart.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username:noreply@mobilemart.com}")
    private String fromEmail;

    public void sendWelcomeEmail(String toEmail, String fullName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Welcome to MobileMart!");
            
            String htmlContent = "<html><body>"
                    + "<h2>Welcome to MobileMart, " + fullName + "!</h2>"
                    + "<p>We're thrilled to have you with us. Explore the latest smartphones and accessories on our platform.</p>"
                    + "<p>Happy Shopping!</p>"
                    + "<br>"
                    + "<p>Best regards,<br>The MobileMart Team</p>"
                    + "</body></html>";
                    
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
            System.out.println("Welcome email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send welcome email to " + toEmail);
            e.printStackTrace();
        }
    }
}
