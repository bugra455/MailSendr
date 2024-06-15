package com.MailSendr.MailSendr.service;
import com.MailSendr.MailSendr.model.MailModel;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSenderImpl mailSender;
    String tls;
    public MailService() {
        Dotenv dotenv = Dotenv.load();
        String host = dotenv.get("SPRING_CONFIG_PROPERTY.spring.mail.host");
        int port = Integer.parseInt(dotenv.get("SPRING_CONFIG_PROPERTY.spring.mail.port"));
        String username = dotenv.get("SPRING_CONFIG_PROPERTY.spring.mail.username");
        String password = dotenv.get("SPRING_CONFIG_PROPERTY.spring.mail.password");

        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setProtocol("smtp");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.ssl.enable", "false");
        tls = dotenv.get("SPRING_CONFIG_PROPERTY.spring.mail.properties.mail.smtp.starttls.enable");
    }
    @PostConstruct
    public void init() {
        System.out.println("MailSender initialized");
        System.out.println(mailSender.getHost());
        System.out.println(mailSender.getPort());
        System.out.println(mailSender.getUsername());
        System.out.println(mailSender.getPassword());
        System.out.println(tls);
    }

    public void sendMail(String mail, MailModel mailModel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender.getUsername());
        message.setTo(mail);
        message.setSubject(mailModel.getSubject());
        message.setText(mailModel.getMessage());
        mailSender.send(message);
    }
}