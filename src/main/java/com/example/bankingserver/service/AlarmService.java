package com.example.bankingserver.service;

import org.springframework.stereotype.Service;

@Service
public class AlarmService {

    public void callAlarm(Long userId, String message) throws InterruptedException {
        Thread.sleep(1000);
    }

}
