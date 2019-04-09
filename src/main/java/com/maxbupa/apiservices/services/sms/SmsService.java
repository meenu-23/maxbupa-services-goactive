package com.maxbupa.apiservices.services.sms;

import com.maxbupa.apiservices.model.sms.SmsModel;

import java.util.List;

public interface SmsService {

    List<StringBuffer> sendsms(SmsModel smsModel);
}
