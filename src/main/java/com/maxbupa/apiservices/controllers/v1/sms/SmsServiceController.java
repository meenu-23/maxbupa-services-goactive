package com.maxbupa.apiservices.controllers.v1.sms;

import com.maxbupa.apiservices.model.sms.SmsModel;
import com.maxbupa.apiservices.services.sms.SmsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/maxbupa")
public class SmsServiceController {

    private final SmsService smsService;

    public SmsServiceController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping(value="/sendsms")
    public List<StringBuffer> sendsms(@RequestBody SmsModel smsModel){
        return smsService.sendsms(smsModel);
    }
}