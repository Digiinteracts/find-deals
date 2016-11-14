package com.example.digit_25.finaldeal;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.PopularStoreAdapter;
import dto.StoreDTO;
import utils.AppConstants;
import utils.Constants;
import utils.DialogBox;
import utils.FontStyle;
import utils.GifView;
import utils.RequestHandler;
import utils.SharePref;

public class SplashScreen extends AppCompatActivity{

    LinearLayout linearLayout;
    DialogBox dialogBox;
    SharePref pref;
    private static final int PERMISSION_REQUEST_CODE = 1;
    boolean checkData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new SharePref(SplashScreen.this);
        dialogBox = new DialogBox(SplashScreen.this);

        if (!String.valueOf(pref.getLangVal()).equals("null")){
            if (String.valueOf(pref.getLangVal()).equals("English")) {
                dialogBox.selectLanguage("eg");
            }
            else if (pref.getLangVal().equals("Norwegian")){
                dialogBox.selectLanguage("nb");
            }

        }

        setContentView(R.layout.splash);

        FontStyle fontStyle = new FontStyle(SplashScreen.this);

        TextView deals = (TextView)findViewById(R.id.deals);
        deals.setTypeface(fontStyle.BRADHITC_Font());

        linearLayout = (LinearLayout) findViewById(R.id.liner);
        Display display = getWindowManager().getDefaultDisplay();
        GifView view = new GifView(this, display,1);
        linearLayout.addView(view);

        checkNetworkConn();
    }

    public void gotonext() {
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {

             //   if (checkData) {
                    Intent intent;
                    intent = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
               // }

            }
        };
        handler.postDelayed(r, 6500);
    }

    public void checkNetworkConn(){

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(15);
        drawable.setStroke(1, Color.parseColor("#AAAAAA"));
        drawable.setColor(Color.parseColor("#FFFFFF"));

        GradientDrawable drawable1 = new GradientDrawable();
        drawable1.setShape(GradientDrawable.RECTANGLE);
        drawable1.setCornerRadius(15);
        drawable1.setStroke(1, Color.parseColor("#AAAAAA"));
        drawable1.setColor(Color.parseColor("#ffffff"));

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}


        if(!gps_enabled || !isNetworkAvailable()) {
            // notify user
            if(!isNetworkAvailable()) {
                final Dialog dialog = new Dialog(SplashScreen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogbox);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView title = (TextView) dialog.findViewById(R.id.title);

                RelativeLayout titlelayout = (RelativeLayout) dialog.findViewById(R.id.titlelayout);
                RelativeLayout oklayout = (RelativeLayout) dialog.findViewById(R.id.oklayout);

                titlelayout.setBackgroundDrawable(drawable1);
                oklayout.setBackgroundDrawable(drawable);

                title.setText("Please check your internet connection...");
                TextView ok = (TextView) dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent t = new Intent(SplashScreen.this, SplashScreen.class);
                        startActivity(t);
                        finish();
                    }
                });
                dialog.show();
            }
            else {
                final Dialog dialog = new Dialog(SplashScreen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogbox);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView title = (TextView) dialog.findViewById(R.id.title);

                RelativeLayout titlelayout = (RelativeLayout) dialog.findViewById(R.id.titlelayout);
                RelativeLayout oklayout = (RelativeLayout) dialog.findViewById(R.id.oklayout);

                titlelayout.setBackgroundDrawable(drawable1);
                oklayout.setBackgroundDrawable(drawable);

                title.setText("Please first on your Location...");
                TextView ok = (TextView) dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent t = new Intent(SplashScreen.this, SplashScreen.class);
                        startActivity(t);
                        finish();
                    }
                });
                dialog.show();
            }
        }
        else {
            gotonext();
        //    GetStoreData();
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}