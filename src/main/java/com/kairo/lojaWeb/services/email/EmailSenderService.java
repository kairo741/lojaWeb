package com.kairo.lojaWeb.services.email;


import com.kairo.lojaWeb.dto.MailDTO;
import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailSenderService {

    public void sendEmail(MailDTO mail, String template) throws MessagingException, IOException;

    public void sendEmailThread(MailDTO mail, String template);
}
