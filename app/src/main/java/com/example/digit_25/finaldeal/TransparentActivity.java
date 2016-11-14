package com.example.digit_25.finaldeal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.InputStream;

import utils.GifView;

public class TransparentActivity extends AppCompatActivity {

    public static TransparentActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);

        instance = this;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.likelayout);
        Display display = getWindowManager().getDefaultDisplay();
        GifView view = new GifView(this, display,2);
        linearLayout.addView(view);
    }

    public static TransparentActivity getInstance(){
        return instance;
    }

    public void finishActivity(){
        finish();
    }

}
