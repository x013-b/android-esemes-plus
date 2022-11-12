//package com.example.esemes;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.telephony.SmsMessage;
//import android.util.Log;
//import android.widget.Toast;
//
//public class Receiver extends BroadcastReceiver {
//    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
//    public static final String SMS_BUNDLE = "pdus";
//
//    String smsReceiveNumber, smsReceivedBody;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(SMS_RECEIVED)) {
//            Bundle intentExtras = intent.getExtras();
//            if (intentExtras != null) {
//                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
//                String smsMessageStr = "";
//                for (int i = 0; i < sms.length; ++i) {
//                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
//
//                    String smsBody = smsMessage.getMessageBody().toString();
//                    String address = smsMessage.getOriginatingAddress();
//
//                    smsMessageStr += "SMS From: " + address + "\n";
//                    smsMessageStr += smsBody + "\n";
//
//                    smsReceiveNumber = address;
//                    smsReceivedBody = smsBody;
//                }
//
//
//            }
//        }
//
//    }
//
//}
