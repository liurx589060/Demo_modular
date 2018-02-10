package com.lrx.loginlib;

import java.util.Date;

/**
 * Created by daven.liu on 2018/2/10 0010.
 */

public class FunUtil {
    public static String printTime() {
        Date date = new Date(System.currentTimeMillis());
        return date.toLocaleString();
    }
}
