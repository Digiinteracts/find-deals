package com.example.digit_25.finaldeal;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    TextView signup;
    LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        init();
    }

    public void init(){
        signup = (TextView)findViewById(R.id.signup);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(3);
        drawable.setStroke(3, Color.parseColor("#AAAAAA"));
        drawable.setColor(Color.parseColor("#ffffff"));
        signup.setBackgroundDrawable(drawable);

        ll_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
