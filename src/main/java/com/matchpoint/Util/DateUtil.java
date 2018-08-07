package com.matchpoint.Util;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by root on 6/8/18.
 */
@Service
public class DateUtil {

    public static boolean isCurrentMonth(Date givenDate){
        if(givenDate==null)
            return false;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(givenDate);
        cal2.setTime(new Date());
        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }
}
