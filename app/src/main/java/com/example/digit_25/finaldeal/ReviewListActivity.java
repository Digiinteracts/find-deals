package com.example.digit_25.finaldeal;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.ActiveDealsAdapter;
import adapter.ReviewListAdapter;
import dto.ReviewListDTO;
import dto.StoreDealsDTO;
import utils.AppConstants;
import utils.RequestHandler;

public class ReviewListActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listview;
    LinearLayout ll_left;
    String id = "";
    int DealsDetailFlag,StoreDetailsFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list2);

        init();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            DealsDetailFlag = bundle.getInt("DealsDetailFlag");
            StoreDetailsFlag = bundle.getInt("StoreDetailsFlag");
            id = bundle.getString("store_id");
        }

        AllReviewList();
    }

    public void init(){
        listview = (ListView)findViewById(R.id.listview);
        ll_left = (LinearLayout)findViewById(R.id.ll_left);

        ll_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ll_left:
                finish();
                break;
        }
    }

    private void AllReviewList() {
        new AsyncTask<String, Void, String>() {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();
            public List<ReviewListDTO> rlist = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(ReviewListActivity.this);
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
                    data.put("store_id", id);
                    data.put("type", "review");

                    return ruc.sendPostRequest(AppConstants.BASEURL + "store_all_deals_reviews", data);
                } catch (Exception e) {
                    Log.e("HOME Activity subcat", "Error " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {

                    Log.e("LIKESTORE", " " +s);

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

                            ReviewListAdapter adapter = new ReviewListAdapter(ReviewListActivity.this,rlist);
                            listview.setAdapter(adapter);

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
