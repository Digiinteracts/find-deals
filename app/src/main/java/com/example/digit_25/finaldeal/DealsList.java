package com.example.digit_25.finaldeal;

import android.app.Dialog;
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
import dto.StoreDealsDTO;
import utils.AppConstants;
import utils.RequestHandler;

public class DealsList extends AppCompatActivity implements View.OnClickListener {

    ListView listview;
    LinearLayout ll_left;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_list);

        init();

        Bundle bundle = getIntent().getExtras();
            if (bundle != null){

                id = bundle.getString("store_id");

                AllDealsList();
        }
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

    private void AllDealsList() {
        new AsyncTask<String, Void, String>() {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();
            public List<StoreDealsDTO> dealsList = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(DealsList.this);
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
                    data.put("type", "deal");

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
                    mProgressDialog.dismiss();

                    try {
                        JSONObject res = new JSONObject(s);

                        if (res.has("store")){
                            JSONObject store = res.getJSONObject("store");
                            JSONArray array = store.getJSONArray("deals");

                            for (int i=0; array.length()>i; i++){
                                final JSONObject dealobj = array.getJSONObject(i);

                                final StoreDealsDTO dealsDTO = new StoreDealsDTO();
                                dealsDTO.setId(dealobj.getString("deal_id"));
                                dealsDTO.setName(dealobj.getString("deal_name"));
                                dealsDTO.setDeal_price(dealobj.getString("deal_price"));
                                dealsDTO.setDiscounted_price(dealobj.getString("discounted_price"));
                                dealsDTO.setPercentage_discount(dealobj.getString("percentage_discount"));
                                dealsDTO.setImg(dealobj.getString("deal_logo"));

                                dealsList.add(dealsDTO);
                            }

                            ActiveDealsAdapter adapter = new ActiveDealsAdapter(DealsList.this, dealsList);
                            listview.setAdapter(adapter);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

}
