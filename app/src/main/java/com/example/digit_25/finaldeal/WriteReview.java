package com.example.digit_25.finaldeal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import utils.AppConstants;
import utils.DialogBox;
import utils.RequestHandler;
import utils.SharePref;

/**
 * Created by DigiT-25 on 29-09-2016.
 */
public class WriteReview extends AppCompatActivity implements View.OnClickListener {

    EditText WriteReview_edittext;
    TextView cancel, submit;
    ImageView backicn;
    String rating = "0",id = "";
    int Deals_detail_flag=0;
    float rate = 0;
    RatingBar ratingBar;
    SharePref sharePref;
    DialogBox dialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review);

        sharePref = new SharePref(this);
        dialogBox = new DialogBox(this);
        init();
    }

    public void init(){
        WriteReview_edittext = (EditText)findViewById(R.id.WriteReview_edittext);
        cancel = (TextView) findViewById(R.id.cancel);
        backicn = (ImageView) findViewById(R.id.backicn);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        submit = (TextView) findViewById(R.id.submit);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(3);
        drawable.setStroke(3, Color.parseColor("#AAAAAA"));
        drawable.setColor(Color.parseColor("#ffffff"));
        WriteReview_edittext.setBackgroundDrawable(drawable);

        rating = getIntent().getStringExtra("rating");
        Deals_detail_flag = getIntent().getIntExtra("Deals_detail_flag",0);
        id = getIntent().getStringExtra("id");

        if(rating != null) {
            ratingBar.setRating(Float.valueOf(rating));
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(ratingBar.getRating() < 0.5f) {
                    ratingBar.setRating(0.5f);
                    rate = ratingBar.getRating();
                }
            }
        });

        cancel.setOnClickListener(this);
        backicn.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cancel:
                finish();
                break;

            case R.id.backicn:
                finish();
                break;

            case R.id.submit:
                if(WriteReview_edittext.getText().toString().isEmpty() || ratingBar.getRating() == 0.0f) {
                   dialogBox.DialogBox("Please enter a review and rating..");
                } else {
                    if (Deals_detail_flag == 7) {
                        sendRating(WriteReview_edittext.getText().toString());
                    }
                    else {
                        setResult(101, new Intent()
                                .putExtra("review", WriteReview_edittext.getText().toString())
                                .putExtra("rating", String.valueOf(ratingBar.getRating())));
                        finish();
                    }
                }
        }
    }

    private void sendRating(final String reviewTxt) {
        new AsyncTask<String, Void, String>() {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();
            Dialog dialogBox;
            String result;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(WriteReview.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap();
                data.put("deviceType", "2");
                data.put("type", "deal");
                data.put("user_id", sharePref.getUserId());
                data.put("id", id);

                data.put("ref_type", "review");
                data.put("review_content", reviewTxt);
                data.put("rate", String.valueOf(rate));
                Log.e("writere", "ddd : "+data );
                return ruc.sendPostRequest(AppConstants.BASEURL + "all_rating_review", data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s != null) {
                    Log.e("writere", "onPostExecute: "+s );
                    try {
                        JSONObject res = new JSONObject(s);

                        if(res.getString("msg").equals("Successful")) {
                            Intent intent = new Intent(WriteReview.this,DealsDetail.class);
                            intent.putExtra("Writereviewflag",10);
                            setResult(101,intent);
                            finish();
                        }
                        mProgressDialog.dismiss();
                    } catch (JSONException e) {
                        mProgressDialog.dismiss();
                        e.printStackTrace();
                    }
                }

            }
        }.execute();
    }
}
