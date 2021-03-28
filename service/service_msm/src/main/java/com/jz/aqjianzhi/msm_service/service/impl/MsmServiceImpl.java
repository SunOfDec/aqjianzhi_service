package com.jz.aqjianzhi.msm_service.service.impl;

import com.jz.aqjianzhi.msm_service.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(String from, String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }
}
