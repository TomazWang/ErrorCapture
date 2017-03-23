package me.tomazwang.errorcapture;

/**
 * Created by TomazWang on 2017/3/23.
 */

public class TopExceptionHandler implements Thread.UncaughtExceptionHandler{

    private final Thread.UncaughtExceptionHandler defaultUEH;

    public TopExceptionHandler() {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable e) {

        StackTraceElement[] arr = e.getStackTrace();
        StringBuilder report = new StringBuilder(e.toString());
        report.append("\n\n");
        report.append("------- Stack trace -------------------\n\n");
        for(StackTraceElement ste: arr){
            report.append("    ").append(ste).append("\n");
        }

        report.append("---------------------------------------\n\n");


        report.append("-------- Cause ------------------------\n\n");
        Throwable cause = e.getCause();
        if(cause != null){
            report.append(cause).append("\n\n");
            arr = cause.getStackTrace();
            for(StackTraceElement ste:arr){
                report.append("    ").append(ste).append("\n");
            }
        }

        report.append("---------------------------------------\n\n");

        // TODO: 2017/3/23, @tomaz: write file

        defaultUEH.uncaughtException(thread, e);

    }
}
