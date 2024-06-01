package com.task_pay.task_pay.services.impl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import com.task_pay.task_pay.payloads.request.SendEmailRequest;
import com.task_pay.task_pay.services.EmailService;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;
    @Override
    public void sendEmail(SendEmailRequest request) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper=new  MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(request.getTo());
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.setText(request.getBody());
            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            e.printStackTrace();

        }
    }
}
