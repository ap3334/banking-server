package com.example.bankingserver.service;

import org.springframework.stereotype.Service;

/**
 * 외부 알림 서버를 호출하는 가짜 클래스
 */
@Service
public class AlarmService {

    public void callAlarm(Long userId, String message) throws InterruptedException {
        Thread.sleep(1000);
    }

}
