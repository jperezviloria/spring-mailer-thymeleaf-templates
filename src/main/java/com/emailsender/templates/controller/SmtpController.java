package com.emailsender.templates.controller;

import com.emailsender.templates.service.implementation.SmtpSenderImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("email")
public class SmtpController {

    private static String to = "perezjulioernesto@gmail.com";
    private static String subject = "example";


    @Autowired
    SmtpSenderImplementation smtpSenderImplementation;


    @GetMapping
    public String sendMail() throws UnsupportedEncodingException, MessagingException {
        Map<String, Object> object= new HashMap<>();
        object.put("julio","recipientName");
        object.put("este es el mensaje cool","text");
        object.put("julio2","senderName");
        smtpSenderImplementation.sendMessageUsingThymeleafTemplate(to, subject,object);
        return "send it";
    }


}
