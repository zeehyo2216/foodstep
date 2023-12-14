package com.example.foodstep.service;

import com.example.foodstep.model.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.foodstep.enums.ErrorCode.CANNOT_SEND_VERIFICATION_MAIL;

@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail,
                          String title,
                          String text) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            throw new CustomException(CANNOT_SEND_VERIFICATION_MAIL);
        }
    }

    private SimpleMailMessage createEmailForm(String toEmail,
                                              String title,
                                              String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}