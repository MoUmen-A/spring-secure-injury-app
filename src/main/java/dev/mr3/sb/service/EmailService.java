package dev.mr3.sb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final String fromAddress;

    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String fromAddress) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    public boolean sendWelcomeEmail(String toEmail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(toEmail);
            message.setSubject("Welcome to Injury System");
            message.setText("Welcome to Injury System, Sir");
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendAppointmentEmail(String toEmail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(toEmail);
            message.setSubject("Appointment booked");
            message.setText("Your appointment has been booked. Please check your dashboard.");
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
