package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.SendingEmailDTO;
import br.com.karatedopi.services.exceptions.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    private final JavaMailSender javaMailSender;

    public void sendEmail(SendingEmailDTO sendingEmailDTO) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSentDate(new Date());
            simpleMailMessage.setTo(sendingEmailDTO.getTo());
            simpleMailMessage.setFrom(emailFrom);
            simpleMailMessage.setSubject(sendingEmailDTO.getSubject());
            simpleMailMessage.setCc(sendingEmailDTO.getCc());
            simpleMailMessage.setText(sendingEmailDTO.getBody());
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            throw new EmailException("Falha ao enviar email");
        }
    }

}
