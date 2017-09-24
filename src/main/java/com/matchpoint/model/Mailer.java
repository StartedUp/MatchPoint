package com.matchpoint.model;

/**
 * Created by Prithu on 6/9/17.
 */
public class Mailer {
    private String subject;
    private String[] recipients;
    private String[] bccList;
    private String[] ccList;
    private String body;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    public String[] getBccList() {
        return bccList;
    }

    public void setBccList(String[] bccList) {
        this.bccList = bccList;
    }

    public String[] getCcList() {
        return ccList;
    }

    public void setCcList(String[] ccList) {
        this.ccList = ccList;
    }

    public String getBody() {
        return body;
    }

    public Mailer setBody(String body) {
        this.body = body;
        return this;
    }
}