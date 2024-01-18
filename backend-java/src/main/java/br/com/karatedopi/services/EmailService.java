package br.com.karatedopi.services;

import br.com.karatedopi.controllers.dtos.SendingEmailDTO;
import br.com.karatedopi.services.exceptions.EmailException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    private final JavaMailSender javaMailSender;

    public void sendEmail(MultipartFile[] files, SendingEmailDTO sendingEmailDTO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(emailFrom);
            mimeMessageHelper.setTo(sendingEmailDTO.getTo());
            mimeMessageHelper.setCc(sendingEmailDTO.getCc());
            mimeMessageHelper.setSubject(sendingEmailDTO.getSubject());
            mimeMessageHelper.setText(sendingEmailDTO.getBody());

            for (MultipartFile file: files) {
                if (Objects.nonNull(file) && Objects.nonNull(file.getOriginalFilename())) {
                    mimeMessageHelper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));
                }
            }

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Falha ao enviar email");
        }
    }

}
