package com.MailSendr.MailSendr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailFormModel {
    private String subject;
    private String message;
    private String to;
}
