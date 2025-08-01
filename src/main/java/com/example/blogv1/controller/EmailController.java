package com.example.blogv1.controller;

import com.example.blogv1.dto.EmailRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {
    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDto request) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("neogearbox@gmail.com");
        message.setTo("neogearbox@gmail.com");
        message.setSubject(request.getSubject());

        String fullMessage = String.format(
                "Gönderen: %s <%s>\n\nMesaj:\n%s",
                request.getName(),
                request.getEmail(),
                request.getBody()
        );

        message.setText(fullMessage);
        mailSender.send(message);
        return ResponseEntity.ok("E-posta gönderildi.");
    }
}