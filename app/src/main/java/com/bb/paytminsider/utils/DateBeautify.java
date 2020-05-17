package com.bb.paytminsider.utils;

import java.text.SimpleDateFormat;

public class DateBeautify {
    /*
    Beautify dates coming as epoch time in long
     */
    public static String beautifyDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy | HH:mm");
        return simpleDateFormat.format(time);
    }
}
