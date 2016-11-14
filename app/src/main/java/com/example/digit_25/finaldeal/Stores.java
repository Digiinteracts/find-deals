package com.example.digit_25.finaldeal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.GalleryAdapter;
import adapter.PopularStoreAdapter;
import dto.ReviewListDTO;
import dto.StoreDTO;
import fragment.Home;
import utils.AppConstants;
import utils.Constants;
import utils.RequestHandler;

public class Stores extends AppCompatActivity implements View.OnClickListener {

    //JazzyListView storeList;
    ListView storeList;
    List<String> popularstore = new ArrayList<>();
    LinearLayout back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stores);

        init();

        GetStoreDetails();

    }

    public void init(){
        //storeList = (JazzyListView)findViewById(R.id.storeslist);
        storeList = (ListView) findViewById(R.id.storeslist);
        back_button = (LinearLayout) findViewById(R.id.ll_left);

        back_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    private void GetStoreDetails() {

        class StoreData extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();
            Dialog dialogBox;
            String userId = "";
            TransparentActivity transparentActivity;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(Stores.this);
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
                    HashMap<String, String> data = new HashMap<>();
                    data.put("user_lat", Constants.LATITUDE);
                    data.put("user_lng", Constants.LONGITUDE);

                    return ruc.sendPostRequest(AppConstants.BASEURL + "storedetail3", data);
                } catch (Exception e) {
                    Log.e("HOME Activity subcat", "Error " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                transparentActivity = TransparentActivity.getInstance();

                List<StoreDTO> listdata = new ArrayList<>();
                StoreDTO storeDTO = new StoreDTO();
                if (s != null) {

                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if (jsonObject.has("store")) {

                            JSONArray array = jsonObject.getJSONArray("store");
                            for (int i=0; array.length()>i; i++){
                                JSONObject obj = array.getJSONObject(i);

                                StoreDTO strDto = new StoreDTO();

                                strDto.setName(obj.getString("name"));
                                strDto.setStoreId(obj.getString("id"));
                                strDto.setImg(obj.getString("img"));
                                strDto.setTiming(obj.getString("timings"));
                                strDto.setState(obj.getString("state"));
                                strDto.setCountry(obj.getString("country"));

                                listdata.add(strDto);
                            }
                            storeDTO.setStoreList(listdata);

                            PopularStoreAdapter adapter = new PopularStoreAdapter(Stores.this, storeDTO.getStoreList());
                            storeList.setAdapter(adapter);
                            mProgressDialog.dismiss();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(Stores.this, "Server Exceptions..", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }
        }
        StoreData ru = new StoreData();
        ru.execute();
    }

}
