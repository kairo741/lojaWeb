package com.kairo.lojaWeb.services.email;

import com.kairo.lojaWeb.dto.MailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    @Value("${spring.mail.username}")
    private String email;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;


    /**
     * Envia e-mail
     *
     * @param mail dados do e-mail
     * @param template template do e-mail
     */
    @Override
    public void sendEmail(MailDTO mail, String template) throws MessagingException {
        mail.setFrom(email);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(mail.getProps());

        String html = templateEngine.process(template, context);
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        emailSender.send(message);
    }

    /**
     * Envia e-mail via thread
     *
     * @param mail Dados do e-mail
     */
    @Override
    public void sendEmailThread(MailDTO mail, String template) {
        new Thread(() -> {
            try {
                this.sendEmail(mail, template);
            } catch (Exception e) {
                log.error("Erro ao enviar email");
                log.error(e.getMessage(), e);
            }
        }).start();
    }
}
