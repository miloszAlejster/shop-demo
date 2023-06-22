package com.pb.service.impl;

import com.pb.controller.IndexController;
import com.pb.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service(value = "MailService")
public class MailServiceImpl implements MailService {

    Logger logger = LoggerFactory.getLogger(IndexController.class);
    private Environment environment;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(Environment env) {
        this.environment = env;
    }

    @Override
    public void sendMail(String mailAddress, String title, String mailMessage) {
        logger.info(this.getClass().getName() + ": Sending email start.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("spring.mail.username"));
        message.setTo(mailAddress);
        message.setSubject(title);
        message.setText(mailMessage);

        javaMailSender.send(message);

        logger.info(this.getClass().getName() + ": Sending email end.");
    }
}
