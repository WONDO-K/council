package com.wondo.council.service;

import org.springframework.data.crossstore.ChangeSetPersister;

public interface EmailService {

    String sendSimpleMessage(String to)throws Exception;

    Boolean verifyEmail(String key) throws ChangeSetPersister.NotFoundException;
}
