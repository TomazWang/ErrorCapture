package me.tomazwang.errorcapture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TomazWang on 2017/3/23.
 */

public class FileUtil {

    private final static String FILE_NAME = "err_log";
    private final static String ARCHIVED_DIR = "logArchived";
    private final static String EXT_TXT = "txt";

    private final static SimpleDateFormat TIME_STAMP_FORMATTER = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private final static SimpleDateFormat FILE_TIME_STAMP_FORMATTER = new SimpleDateFormat("MMddHHmmss");

    public static void writeFile(String report){
        File downloadFolderDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File logFile = new File(downloadFolderDir, FILE_NAME+"."+EXT_TXT);

        String reportLine = getTimestamp() + "  " + report;
        try {

            boolean createResult = logFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(logFile, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write(reportLine);
            bw.close();
        } catch (IOException ioe) {
            // ...
            ioe.printStackTrace();
        }
    }


    public static String readLog() {
        StringBuilder sb = new StringBuilder();
        File downloadFolderDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File traceFile = new File(downloadFolderDir, FILE_NAME+"."+EXT_TXT);

        try {

            FileInputStream fis = new FileInputStream(traceFile);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        return sb.toString();
    }


    public static void openLogFile(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File downloadFolderDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File traceFile = new File(downloadFolderDir, FILE_NAME+"."+EXT_TXT);

        scanFile(activity, traceFile.getAbsolutePath());

        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        String mimeType = myMime.getMimeTypeFromExtension(EXT_TXT);

        intent.setDataAndType(Uri.fromFile(traceFile),mimeType);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(Intent.createChooser(intent, "Open file"));
    }

    private static void scanFile(Context context, String path) {
        try {
            MediaScannerConnection.scanFile(context, new String[] { path }, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void archive() {

        File downloadFolderDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File traceFile = new File(downloadFolderDir, FILE_NAME+"."+EXT_TXT);

        try {

            traceFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(traceFile, false);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write("");
            bw.close();
        } catch (IOException ioe) {
            // ...
            ioe.printStackTrace();
        }

    }



    private static String getTimestamp() {
        return TIME_STAMP_FORMATTER.format(new Date());
    }



}
