package com.example.digit_25.finaldeal;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.ReviewListAdapter;
import dto.ReviewListDTO;
import utils.AppConstants;
import utils.RequestHandler;

public class DealsLike extends AppCompatActivity {

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deals_like);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            userID = bundle.getString("userId");
            DealsLikeList();
        }
    }

    private void DealsLikeList() {
        new AsyncTask<String, Void, String>() {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();
            public List<ReviewListDTO> rlist = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(DealsLike.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();

            }
            @Override
            protected String doInBackground(String... params) {
                try {
                    HashMap<String, String> data = new HashMap();
                    data.put("user_id", userID);

                    return ruc.sendPostRequest(AppConstants.BASEURL + "user_profile", data);
                } catch (Exception e) {
                    Log.e("HOME Activity subcat", "Error " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {

                    Log.e("Deakslike", " " +s);

                    try {
                        JSONObject res = new JSONObject(s);

                        if (res.has("store")){
                            JSONObject store = res.getJSONObject("store");
                            JSONArray array = store.getJSONArray("reviews");

                            for (int i=0; array.length()>i; i++){
                                JSONObject reviewObj = array.getJSONObject(i);

                                ReviewListDTO reviewDto = new ReviewListDTO();
                                reviewDto.setId(reviewObj.getString("review_id"));
                                reviewDto.setName(reviewObj.getString("user_name"));
                                reviewDto.setImg(reviewObj.getString("user_pic"));
                                reviewDto.setView(reviewObj.getString("review_content"));
                                reviewDto.setTime(reviewObj.getString("modified_time"));

                                rlist.add(reviewDto);
                            }

                           // ReviewListAdapter adapter = new ReviewListAdapter(ReviewListActivity.this,rlist);
                           // listview.setAdapter(adapter);

                            mProgressDialog.dismiss();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }
}
