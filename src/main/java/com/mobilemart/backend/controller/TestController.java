package com.mobilemart.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.internet.MimeMessage;
import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequestMapping("/api/public")
public class TestController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/test-email")
    public String testEmail() {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("test@mobilemart.com");
            helper.setTo("rlhp1907@gmail.com");
            helper.setSubject("Diagnostic Test");
            helper.setText("This is a test from Railway.", true);
            javaMailSender.send(message);
            return "SUCCESS: Email sent perfectly!";
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "FAILED:\n" + sw.toString();
        }
    }
}
