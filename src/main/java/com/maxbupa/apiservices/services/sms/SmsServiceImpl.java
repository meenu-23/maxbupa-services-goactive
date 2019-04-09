package com.maxbupa.apiservices.services.sms;


import com.maxbupa.apiservices.utilities.CommonUtility;
import com.maxbupa.apiservices.model.sms.SmsModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmsServiceImpl implements SmsService {

    @Override
    public List<StringBuffer> sendsms(SmsModel smsModel) {
        String url ="";
        String method = smsModel.getMethod();
        String sender = smsModel.getSender();
        List<String> to = smsModel.getTo();
        String api = smsModel.getApi_key();
        String format = smsModel.getFormat();
        String message = smsModel.getMessage();
        List<StringBuffer> getresponse = new ArrayList<>();
        List<StringBuffer> postresponse = new ArrayList<>();
        List<String> res = CommonUtility.phoneNumberList(to);
        if(to.size()<=10)
        {
            url = "https://api-alerts.kaleyra.com/v4/?method="+method+"&sender="+sender+"&to="+res.get(0)+"&message="+message+"&api_key="+api+"&format="+format;
            getresponse.add(new StringBuffer(CommonUtility.getHttp(url)));
            return getresponse;
        }
        else{
            for(int start =0; start < res.size(); start++)
            {
                url = "https://api-alerts.kaleyra.com/v4/?method="+method+"&sender="+sender+"&to="+res.get(start)+"&message="+message+"&api_key="+api+"&format="+format;
                postresponse.add(new StringBuffer(CommonUtility.postHttp(url)));
            }
        }
        return postresponse;
    }
}
