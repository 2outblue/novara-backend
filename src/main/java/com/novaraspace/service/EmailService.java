package com.novaraspace.service;

import com.novaraspace.model.dto.auth.VerificationTokenDTO;
import com.novaraspace.model.exception.FailedSendingEmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;
    @Value("${app.frontend-url}")
    private String frontendUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationEmail(VerificationTokenDTO verificationDTO) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from); //TODO: You can add a second argument here - so the email title (the one you can see before its opened - in the inbox) is not the sender but something pretty like 'Novara Space'
            helper.setTo(verificationDTO.getEmail());
            helper.setSubject("Novara Registration Email");
            helper.setText(
                    "<p>To verify your account please click <a href=\"" + frontendUrl + "/" + verificationDTO.getLinkToken() + "\">here</a> to verify your email.</p>" +
                            "<p>Or enter this code: </p>" +
                            verificationDTO.getCode(),

                    true);
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
            throw new FailedSendingEmailException("Failed sending email.");
        } catch (RuntimeException ex) {
            throw new FailedSendingEmailException("Failed sending email.");
        }
        mailSender.send(mimeMessage);
    }

}
