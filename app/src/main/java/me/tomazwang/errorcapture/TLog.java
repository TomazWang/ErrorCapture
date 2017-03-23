package me.tomazwang.errorcapture;

import android.util.Log;

import static me.tomazwang.errorcapture.FileUtil.writeFile;

/**
 * Created by TomazWang on 2017/3/23.
 */

public class TLog {

    public static void d(String tag, String log) {
        writeFile("D/[" + tag + "]: " + log + "\n");
        Log.d(tag, log);
    }


    public static void e(String tag, String log) {
        writeFile("E/[" + tag + "]: " + log + "\n");
        Log.e(tag, log);
    }

    public static void w(String tag, String log) {
        writeFile("W/[" + tag + "]: " + log + "\n");
        Log.w(tag, log);
    }


    public static void i(String tag, String log) {
        writeFile("I/[" + tag + "]: " + log + "\n");
        Log.i(tag, log);
    }

    public static void v(String tag, String log) {
        writeFile("V/[" + tag + "]: " + log + "\n");
        Log.v(tag, log);
    }

}
