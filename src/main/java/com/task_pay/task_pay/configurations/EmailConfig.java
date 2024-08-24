package com.task_pay.task_pay.configurations;
import jakarta.mail.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.naming.NamingException;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.username}")
    private String userName;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
           mailSender.setPort(port);
           mailSender.setHost(host);
           mailSender.setPassword(password);
           mailSender.setUsername(userName);
           return mailSender;
    }

//    public Session session(){
//        JndiTemplate template = new JndiTemplate();
//        Session session = null;
//        try {
//            session = (Session) template.lookup("java:jboss/mail/Email");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//        return session;
//    }

}
