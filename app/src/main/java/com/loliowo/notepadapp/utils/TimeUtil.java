package com.loliowo.notepadapp.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {
  public static String getTime() {
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    Date date = new Date(System.currentTimeMillis());
    return simpleDateFormat.format(date);
  }
}
