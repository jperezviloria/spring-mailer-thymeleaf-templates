package com.emailsender.templates.service.implementation;

import com.emailsender.templates.model.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class SmtpSenderImplementation {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;


    @Value("classpath:/mail-logo.png")
    private Resource resourceFile;

    public void send(ContactForm contactForm) throws MessagingException, UnsupportedEncodingException {


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);


        String mailSubject = contactForm.getNameContactForm() + " would like to concrete a schedule for an Online course ";
        String mailContent = "<h1><center>Spanish Tongue Sound</h1></center>" +
                "<h2>You have received a new inquiry </h2>" +
                "<p>" + contactForm.getNameContactForm() + " has contacted you for a spanish course. </p>" +
                "<ul>" +
                "<li><p><b>Name: </b>"+ contactForm.getNameContactForm() + "</p>" + "</li>" +
                "<li><p><b>Phone Number: </b>"+ contactForm.getPhoneContactForm() + "</p>" + "</li>" +
                "<li><p><b>Email: </b>"+ contactForm.getEmailContactForm() + "</p>" + "</li>" +
                "<li><p><b>Age: </b>"+ contactForm.getAgeContactForm() + "</p>" + "</li>" +
                "<li><p><b>Sex: </b>"+ contactForm.getSexContactForm() + "</p>" + "</li>" +
                "<li><p><b>Country: </b>"+ contactForm.getCountryContactForm() + "</p>"+ "</li>" +
                "<li><p><b>Message: </b>"+ contactForm.getBoxMessageContactForm() + "</p>" + "</li>" + "</ul>";

        helper.setFrom("ceo@protobot.dev", "Spanish Tongue Sound");
        helper.setSubject(mailSubject);
        helper.setTo("perezjulioernesto@gmail.com");
        helper.setText(mailContent,true);

        javaMailSender.send(message);
    }



    public void sendMessageUsingThymeleafTemplate(
            String to, String subject, Map<String, Object> templateModel)
            throws MessagingException, UnsupportedEncodingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("ceo@protobot.dev", "Spanish Tongue Sound");
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(htmlBody,true);
        helper.addInline("attachment.png", resourceFile);
        javaMailSender.send(message);
    }

}
