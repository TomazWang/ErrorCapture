package me.tomazwang.errorcapture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private View btnError;
    private View btnMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnMail = findViewById(R.id.btn_mail);
        btnError = findViewById(R.id.btn_error);
        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createError();
            }
        });


        btnMail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mailToMe();
            }
        });
    }

    private void createError() {
        throw new RuntimeException("custom Error");
    }



    public void mailToMe(){
        String line = "";
        String trace = "";
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(MainActivity.this.openFileInput("stack.trace")));
            while((line = reader.readLine()) != null) {
                trace += line+"\n";
                Log.d(TAG, ""+line);
            }
        } catch(FileNotFoundException fnfe) {
            //
        } catch(IOException ioe) {
            //
        }

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        String subject = "Error report";
        String body =
                "Mail this to tomaz@linkwish.com: "+
                        "\n"+
                        trace+
                        "\n";

        sendIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[] {"tomaz@linkwish.com"});
        sendIntent.putExtra(Intent.EXTRA_TEXT, body);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.setType("message/rfc822");

        MainActivity.this.startActivity(
                Intent.createChooser(sendIntent, "Title:"));




    }


}
