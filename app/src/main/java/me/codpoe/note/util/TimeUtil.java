package me.codpoe.note.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Codpoe on 2016/12/8.
 */

public class TimeUtil {

    public static String formatMillis(String millis, SimpleDateFormat sdf) {
        return sdf.format(new Date(Long.parseLong(millis)));
    }

    public static String formatModifyTime(String modifyTime) {
        Calendar modify = Calendar.getInstance();
        modify.setTimeInMillis(Long.parseLong(modifyTime));

        Calendar todayZero = Calendar.getInstance();
        todayZero.set(Calendar.HOUR_OF_DAY, 0);
        todayZero.set(Calendar.MINUTE, 0);
        todayZero.set(Calendar.SECOND, 0);
        todayZero.set(Calendar.MILLISECOND, 0);


        Calendar yesterdayZero = Calendar.getInstance();
        yesterdayZero.set(Calendar.DAY_OF_MONTH, yesterdayZero.get(Calendar.DAY_OF_MONTH) - 1);
        yesterdayZero.set(Calendar.HOUR_OF_DAY, 0);
        yesterdayZero.set(Calendar.MINUTE, 0);
        yesterdayZero.set(Calendar.SECOND, 0);
        yesterdayZero.set(Calendar.MILLISECOND, 0);

        if (modify.after(todayZero)) {
            return formatMillis(modifyTime, new SimpleDateFormat("今天 HH:mm"));
        } else if (modify.after(yesterdayZero)) {
            return formatMillis(modifyTime, new SimpleDateFormat("昨天 HH:mm"));
        } else {
            return formatMillis(modifyTime, new SimpleDateFormat("MM.dd HH:mm"));
        }
    }
}
