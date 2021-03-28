package com.jz.aqjianzhi.msm_service.service;

public interface MsmService {

    /**
     * 发送邮件
     */
    void sendSimpleMail(String from, String to, String subject, String content);
}
