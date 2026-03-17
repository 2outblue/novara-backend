package com.novaraspace.service;

import com.novaraspace.model.domain.PassResetEmailParams;
import com.novaraspace.model.dto.auth.VerificationTokenDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.enable-email-verification}")
    private boolean emailVerificationEnabled;
    @Value("${app.enable-email-password-reset}")
    private boolean passResetEmailEnabled;
    @Value("${spring.mail.from}")
    private String from;
    @Value("${app.frontend-url}")
    private String frontendUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendActivationEmail(VerificationTokenDTO verificationDTO) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from); //TODO: You can add a second argument here - so the email title (the one you can see before its opened - in the inbox) is not the sender but something pretty like 'Novara Space'
            helper.setTo(verificationDTO.getEmail());
            helper.setSubject("Novara Account Verification");
            helper.setText(
                    "<p>To activate your account please click <a href=\"" + frontendUrl + "/auth/verify-link?linkToken=" + verificationDTO.getLinkToken() + "\">here</a>.</p>" +
                            "<p>Or enter this code: </p>" +
                            verificationDTO.getCode(),

                    true);
            if (emailVerificationEnabled) {
                mailSender.send(mimeMessage);
            }
        } catch (MessagingException | MailException ex) {
            return false;
        }
        return true;
    }

    public boolean sendPasswordResetLink(PassResetEmailParams params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from); //TODO: You can add a second argument here - so the email title (the one you can see before its opened - in the inbox) is not the sender but something pretty like 'Novara Space'
            helper.setTo(params.email());
            helper.setSubject("Novara Password Reset");
            helper.setText(
                    "<p>Hi " + params.userFirstName() + ",</p>" +
                        "<p>We received a request to reset the password for your Novara account associated with this email address.</p>" +
                    "<p>Click <a href=\"" + frontendUrl + "/auth/reset-password-complete?resetToken=" + params.tokenValue() + "\">here</a> in order to reset your password.</p>" +
                            "<p>This link will expire in " + params.expiryMinutes() + " minutes for your security</p>" +
                            "<p>If you did not request a password reset, please ignore this email. Your password will remain unchanged, and you do not need to take any further action.</p>",

                    true);
            if (passResetEmailEnabled) {
                mailSender.send(mimeMessage);
            }
        } catch (MessagingException | MailException ex) {
            return false;
        }
        return true;
    }

}
