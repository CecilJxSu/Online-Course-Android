package cn.canlnac.onlinecourse.presentation.util;

import android.os.Build;

public class APIUtil {
    public static boolean isSupport(int apiNo){
        return Build.VERSION.SDK_INT >= apiNo;
    }

    public static String formatDuration(long duration) {
        if (!(duration >= 0)) {
            duration = 0;
        }

        duration /= 1000;
        long hours = duration / 3600;
        duration -= hours * 3600;
        long minutes = duration / 60;
        duration -= minutes * 60;
        long second = duration;

        String durationString;

        if (hours > 0 && hours < 10) {
            durationString = "0" + hours + ":";
        } else if (hours >= 10 && hours < 24) {
            durationString = hours + ":";
        } else {
            durationString = "";
        }

        if (minutes > 0 && minutes < 10) {
            durationString += "0" + minutes + ":";
        } else if (minutes >= 10 && minutes < 60) {
            durationString += minutes + ":";
        } else {
            durationString += "00:";
        }

        if (second > 0 && second < 10) {
            durationString += "0" + second;
        } else if ( second >= 10 && second < 60) {
            durationString += second;
        } else {
            durationString += "00";
        }

        return durationString;
    }
}
