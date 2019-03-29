package com.endless.permission.annotation;

import android.Manifest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.StringDef;

/**
 * 需要申请权限的
 * @author haosiyuan
 * @date 2019/3/29 2:46 PM
 */
@StringDef({
        Permission.CalendarGroup.READ_CALENDAR,
        Permission.CalendarGroup.WRITE_CALENDAR,
        Permission.CameraGroup.CAMERA,
        Permission.ContactsGroup.READ_CONTACTS,
        Permission.ContactsGroup.WRITE_CONTACTS,
        Permission.ContactsGroup.GET_ACCOUNTS,
        Permission.LocationGroup.ACCESS_FINE_LOCATION,
        Permission.LocationGroup.ACCESS_COARSE_LOCATION,
        Permission.MicroPhoneGroup.RECORD_AUDIO,
        Permission.PhoneGroup.READ_CALL_LOG,
        Permission.PhoneGroup.WRITE_CALL_LOG,
        Permission.PhoneGroup.ADD_VOICEMAIL,
        Permission.PhoneGroup.USE_SIP,
        Permission.PhoneGroup.PROCESS_OUTGOING_CALLS,
        Permission.SensorsGroup.BODY_SENSORS,
        Permission.SMSGroup.SEND_SMS,
        Permission.SMSGroup.RECEIVE_SMS,
        Permission.SMSGroup.READ_SMS,
        Permission.SMSGroup.RECEIVE_WAP_PUSH,
        Permission.SMSGroup.RECEIVE_MMS,
        Permission.StorageGroup.READ_EXTERNAL_STORAGE,
        Permission.StorageGroup.WRITE_EXTERNAL_STORAGE,
})
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface Permission {

    int RequestCode = 10001;

    /**
     * 日历权限组
     */
    interface CalendarGroup {

        String READ_CALENDAR = Manifest.permission.READ_CALENDAR;

        String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    }

    /**
     * 相机权限
     */
    interface CameraGroup {

        String CAMERA = Manifest.permission.CAMERA;
    }

    /**
     * 联系人权限
     */
    interface ContactsGroup {

        String READ_CONTACTS = Manifest.permission.READ_CONTACTS;

        String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;

        String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    }

    /**
     * 定位权限
     */
    interface LocationGroup {

        String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

        String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    }

    /**
     * 录音权限
     */
    interface MicroPhoneGroup {

        String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    }

    /**
     * 手机权限组
     */
    interface PhoneGroup {

        String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;

        String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;

        String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;

        String USE_SIP = Manifest.permission.USE_SIP;

        String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    }

    /**
     * 传感器权限组
     */
    interface SensorsGroup {

        String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    }

    /**
     * 短信权限组
     */
    interface SMSGroup {

        String SEND_SMS = Manifest.permission.SEND_SMS;

        String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;

        String READ_SMS = Manifest.permission.READ_SMS;

        String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;

        String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    }

    /**
     * 读写权限组
     */
    interface StorageGroup {

        String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

        String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }
}
