//package com.task_pay.task_pay.services.impl;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.Random;
//import java.util.UUID;
//
//@Service
//public class EmailServiceImpl{
//    Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
//
//    @Autowired JavaMailSender javaMailSender;
//    @Autowired ValorOtpCache valorOtpCache;
//
//    public boolean sendValidate(String deviceId, String email, String devOrProduction)
//            throws MessagingException, IOException {
//
//        logger.info("Sending Email...");
//
//        //        sendEmail();
//        sendEmailWithAttachment(deviceId, email, devOrProduction);
//
//        logger.info("Done");
//        return true;
//    }
//
//    void sendEmail() {
//
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("genalpha95@gmail.com");
//        msg.setSubject("Testing from Spring Boot");
//        msg.setText("Hello World \n Spring Boot Email");
//
//        javaMailSender.send(msg);
//    }
//
//    void sendEmailWithAttachment(String deviceId, String email, String devOrProduction)
//            throws MessagingException, IOException {
//
//        MimeMessage msg = javaMailSender.createMimeMessage();
//
//        // true = multipart message
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//        helper.setTo(email);
//
//        helper.setSubject("Valor Account Verification");
//
//        helper.setText(
//                "<h1>Verify Your Valor Account</h1>"
//                        + "<h2>Your OTP Is </h2><br>"
//                        + "<h2>"+generateOtp(deviceId)+" </h2>",
//                true);
//
//        //        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
//
//        javaMailSender.send(msg);
//    }
//
//    public String generateOtp(String deviceId)
//    {
//        Random random=new Random();
//        String otp = String.format("%04d", random.nextInt(10000));
//        valorOtpCache.storeInCache(deviceId, otp);
//        return otp;
//    }
//
//    public boolean validateEmail(String deviceId,String otp){
//        boolean valid = false;
//        valid = valorOtpCache.getCachedToken(deviceId).equals(otp);
//        return valid;
//    }
//
//    public String generateUinqueCode(String deviceId) {
//        String uuid = String.valueOf(UUID.randomUUID());
//        String str = deviceId + ":" + uuid;
//        Charset charset = StandardCharsets.UTF_8;
//        byte[] byteArray = str.getBytes(charset);
//        String string = new String(byteArray);
//        byte[] base64Str = Base64.getEncoder().encode(byteArray);
//        String base64 = new String(base64Str);
//        valorOtpCache.storeInCache(deviceId, uuid);
//        return base64;
//    }
//
//    public boolean confirmEmail(String code) throws IllegalArgumentException {
//        boolean valid = false;
//        String decodedString;
//        try {
//            byte[] decodedByte = Base64.getDecoder().decode(code);
//            decodedString = new String(decodedByte);
//            logger.info("Decoded String =" + decodedString);
//            String fetchUuid = decodedString.split(":")[1];
//            String deviceId = decodedString.split(":")[0];
//            valid = valorOtpCache.getCachedToken(deviceId).equals(fetchUuid);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("InvalidArgumentException");
//        }
//        return valid;
//    }
//
//
//}
