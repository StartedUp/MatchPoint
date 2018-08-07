package com.matchpoint.batch;

import com.matchpoint.Util.DateUtil;
import com.matchpoint.model.Mailer;
import com.matchpoint.model.Payment;
import com.matchpoint.model.User;
import com.matchpoint.service.MailService;
import com.matchpoint.service.PaymentManager;
import com.matchpoint.service.UserManager;
import com.matchpoint.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by root on 6/8/18.
 */
@Component
public class MailScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailScheduler.class);

    @Autowired
    private PaymentManager paymentManager;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String[] ccList;

    @Value("${mail.monthly.fee.reminder}")
    private String monthlyFeeReminder;

    @Scheduled(cron = "55 53 19 * * *")
    public void sendMonthlyFeeReminder(){
        List<User> users = userService.fetchUsersWithoutMonthlyPayment();
        if (!users.isEmpty()) {
            users.forEach(user -> {
                Mailer mailer = new Mailer();
                mailer.setBccList(new String[]{user.getEmail()});
                mailer.setCcList(ccList);
                mailer.setSubject("Reminder for monthly fee");
                Map<String,String> mailTemplateData=new HashMap<>();
                mailTemplateData.put("userName", user.getFirstName());
                mailTemplateData.put("templateName","mailTemplates/monthlyFeeReminderMail");
                mailTemplateData.put("content", monthlyFeeReminder);
                mailService.prepareAndSend(mailer,mailTemplateData);
            });
        }
    }
}
