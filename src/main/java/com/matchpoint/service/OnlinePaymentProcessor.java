package com.matchpoint.service;

import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.InvalidPaymentOrderException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.Fee;
import com.matchpoint.model.Payment;
import com.matchpoint.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OnlinePaymentProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlinePaymentProcessor.class.getName());
    @Value("${payment.online.instamojo.api.endpoint}")
    private String instamojoApiEndpoint;
    @Value("${payment.online.instamojo.auth.endpoint}")
    private String instamojoAuthEndpoint;
    @Value("${payment.online.instamojo.client.id}")
    private String instamojoClientId;
    @Value("${payment.online.instamojo.client.secret}")
    private String instamojoClientSecret;
    @Value("${domain.name}")
    private String domainName;
    public String placeOrder(Payment payment){
        PaymentOrder order = new PaymentOrder();
        User user = payment.getUser();
        Fee fee =payment.getFee();
        order.setName(user.getFirstName()+" "+user.getLastName());
        order.setEmail(user.getEmail());
        order.setPhone(user.getMobile());
        order.setCurrency("INR");
        order.setAmount(payment.getAmount().doubleValue());
        order.setDescription(fee.getFeeName());
        order.setRedirectUrl(domainName+"/u/user/payment/paymentStatus");
        //order.setWebhookUrl("http://www.google.com/");
        order.setTransactionId(payment.getTransactionId());

        Instamojo api = null;
        String longUrl = null;

        try {
            // gets the reference to the instamojo api
            api = InstamojoImpl.getApi(instamojoClientId, instamojoClientSecret, instamojoApiEndpoint, instamojoAuthEndpoint);
        } catch (ConnectionException e) {
            //LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        boolean isOrderValid = order.validate();

        if (isOrderValid) {
            try {
                CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);
                // print the status of the payment order.
                longUrl = createPaymentOrderResponse.getPaymentOptions().getPaymentUrl();
                LOGGER.info(" print the status of the payment order. "+createPaymentOrderResponse.getPaymentOptions().getPaymentUrl());
            } catch (InvalidPaymentOrderException e) {
                //LOGGER.log(Level.SEVERE, e.toString(), e);

                if (order.isTransactionIdInvalid()) {
                    LOGGER.info("Transaction id is invalid. This is mostly due to duplicate  transaction id.");
                }
                if (order.isCurrencyInvalid()) {
                    LOGGER.info("Currency is invalid.");
                }
            } catch (ConnectionException e) {
                //LOGGER.log(Level.SEVERE, e.toString(), e);
            }
            return longUrl!=null?longUrl.replace("?embed=form",""):null;
        } else {
            // inform validation errors to the user.
            if (order.isTransactionIdInvalid()) {
                LOGGER.info("Transaction id is invalid.");
            }
            if (order.isAmountInvalid()) {
                LOGGER.info("Amount can not be less than 9.00.");
            }
            if (order.isCurrencyInvalid()) {
                LOGGER.info("Please provide the currency.");
            }
            if (order.isDescriptionInvalid()) {
                LOGGER.info("Description can not be greater than 255 characters.");
            }
            if (order.isEmailInvalid()) {
                LOGGER.info("Please provide valid Email Address.");
            }
            if (order.isNameInvalid()) {
                LOGGER.info("Name can not be greater than 100 characters.");
            }
            if (order.isPhoneInvalid()) {
                LOGGER.info("Phone is invalid.");
            }
            if (order.isRedirectUrlInvalid()) {
                LOGGER.info("Please provide valid Redirect url.");
            }

            if (order.isWebhookInvalid()) {
                LOGGER.info("Provide a valid webhook url");
            }
            return longUrl;
        }
    }

    public String payForEventRegistration(EventRegistration eventRegistration){
        PaymentOrder order = new PaymentOrder();
        Payment payment = eventRegistration.getPayment();
        order.setName(eventRegistration.getPlayerName());
        order.setEmail(eventRegistration.getPlayerEmail());
        order.setPhone(eventRegistration.getPlayerMobile());
        order.setCurrency("INR");
        order.setAmount(payment.getAmount().doubleValue());
        order.setDescription(payment.getDescription());
        order.setRedirectUrl(domainName+"/eventRegistration/paymentStatus");
        //order.setWebhookUrl("http://www.google.com/");
        order.setTransactionId(payment.getTransactionId());

        Instamojo api = null;
        String longUrl = null;

        try {
            // gets the reference to the instamojo api
            api = InstamojoImpl.getApi(instamojoClientId, instamojoClientSecret, instamojoApiEndpoint, instamojoAuthEndpoint);
        } catch (ConnectionException e) {
            //LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        boolean isOrderValid = order.validate();

        if (isOrderValid) {
            try {
                CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);
                // print the status of the payment order.
                longUrl = createPaymentOrderResponse.getPaymentOptions().getPaymentUrl();
                LOGGER.info(" print the status of the payment order. "+createPaymentOrderResponse.getPaymentOptions().getPaymentUrl());
            } catch (InvalidPaymentOrderException e) {
                //LOGGER.log(Level.SEVERE, e.toString(), e);

                if (order.isTransactionIdInvalid()) {
                    LOGGER.info("Transaction id is invalid. This is mostly due to duplicate  transaction id.");
                }
                if (order.isCurrencyInvalid()) {
                    LOGGER.info("Currency is invalid.");
                }
            } catch (ConnectionException e) {
                //LOGGER.log(Level.SEVERE, e.toString(), e);
            }
            return longUrl!=null?longUrl.replace("?embed=form",""):null;
        } else {
            // inform validation errors to the user.
            if (order.isTransactionIdInvalid()) {
                LOGGER.info("Transaction id is invalid.");
            }
            if (order.isAmountInvalid()) {
                LOGGER.info("Amount can not be less than 9.00.");
            }
            if (order.isCurrencyInvalid()) {
                LOGGER.info("Please provide the currency.");
            }
            if (order.isDescriptionInvalid()) {
                LOGGER.info("Description can not be greater than 255 characters.");
            }
            if (order.isEmailInvalid()) {
                LOGGER.info("Please provide valid Email Address.");
            }
            if (order.isNameInvalid()) {
                LOGGER.info("Name can not be greater than 100 characters.");
            }
            if (order.isPhoneInvalid()) {
                LOGGER.info("Phone is invalid.");
            }
            if (order.isRedirectUrlInvalid()) {
                LOGGER.info("Please provide valid Redirect url.");
            }

            if (order.isWebhookInvalid()) {
                LOGGER.info("Provide a valid webhook url");
            }
            return longUrl;
        }
    }
}