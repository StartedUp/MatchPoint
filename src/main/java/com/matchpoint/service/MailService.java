package com.matchpoint.service;

/**
 * Created by Prithu on 6/9/17.
 */

import com.matchpoint.Util.MailTemplateBuilder;
import com.matchpoint.model.Event;
import com.matchpoint.model.Mailer;
import com.matchpoint.model.User;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailService {
    private static Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    private JavaMailSender mailSender;
    private MailTemplateBuilder mailTemplateBuilder;
    @Autowired
    private Environment environment;
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserService userService;
    @Value("${mail.event.notification}")
    private String eventNotification;

    @Autowired
    public MailService(JavaMailSender mailSender, MailTemplateBuilder mailTemplateBuilder) {
        this.mailSender = mailSender;
        this.mailTemplateBuilder = mailTemplateBuilder;
    }

    @Async
    public void prepareAndSend(Mailer mailer, Map<String, String> mailTemplateData) {
        LOGGER.info("Sending mail");
        LOGGER.info("Mail creds from {} password {}", environment.getProperty("spring.mail.username"),
                environment.getProperty("spring.mail.password")
        );
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(environment.getRequiredProperty("spring.mail.username"));
            messageHelper.setTo(mailer.getRecipients());
            if (mailer.getBccList() != null)
                messageHelper.setBcc(mailer.getBccList());
            if (mailer.getCcList() != null)
                messageHelper.setCc(mailer.getCcList());
            if (mailer.getSubject() != null)
                messageHelper.setSubject(mailer.getSubject());
            LOGGER.info("Building Template for mail with subject : {}",mailer.getSubject());
            String content = mailTemplateBuilder.build(mailTemplateData);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            LOGGER.info("Error while sending mail {}", e);
        }
    }

    /*@Async*/
    public void sendEventNotification(Event event) {
        LOGGER.info("Send event notificatipon : {}", event);
        List<User> users = userManager.findByActive(true);
        if(users!=null && !users.isEmpty()){
            List<String> userEmails = userService.getUserEmails(users);
            Mailer mailer = new Mailer();
            mailer.setRecipients(new String[]{"matchpointtta@gmail.com"});
            mailer.setBccList(userEmails.stream().toArray(String[]::new));
            mailer.setSubject("Table tennis events for you");
            Map<String,String> mailTemplateData=new HashMap<>();
            mailTemplateData.put("templateName","mailTemplates/eventNotificationMail");
            mailTemplateData.put("content", eventNotification);
            mailTemplateData.put("eventName", event.getName());
            mailTemplateData.put("eventStartDate", event.getStartDate().toString());
            mailTemplateData.put("eventEndDate", event.getEndDate().toString());
            mailTemplateData.put("eventLocation", event.getLocation());
            mailTemplateData.put("eventNotificationDate", DateUtils.formatDate(event.getNotificationDate(), "dd-MM-YYYY"));
            mailTemplateData.put("eventRegistrationLastDate", DateUtils.formatDate(event.getRegistrationLastDate(), "dd-MM-YYYY"));
            prepareAndSend(mailer,mailTemplateData);
        }


    }
}