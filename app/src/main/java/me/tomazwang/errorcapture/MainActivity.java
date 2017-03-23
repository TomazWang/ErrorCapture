package me.tomazwang.errorcapture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private View btnError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnError = findViewById(R.id.btn_error);
        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createError();
            }
        });

    }

    private void createError() {
        throw new RuntimeException("custom Error");
    }

}
