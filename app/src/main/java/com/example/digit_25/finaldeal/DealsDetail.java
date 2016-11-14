package com.example.digit_25.finaldeal;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digit_25.finaldeal.R;
import com.google.android.gms.playlog.internal.LogEvent;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import adapter.GalleryAdapter;
import adapter.ReviewListAdapter;
import adapter.SlidingImage_Adapter;
import dto.ReviewListDTO;
import utils.AppConstants;
import utils.Constants;
import utils.DialogBox;
import utils.RequestHandler;
import utils.SharePref;

/**
 * Created by DigiT-25 on 15-09-2016.
 */
public class DealsDetail extends Activity implements View.OnClickListener {

    WebView webView;
    LinearLayout back_button,reviewlist,reviewlayout;
    TextView writereview,viewall,dealsName,dealsAdd,dealsEnd,liketxt,viewed,reviewtxt,share,description,dealsprice,discounted_price;
    ImageView iv_background,likeimg;
    View view = null;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    CirclePageIndicator indicator;
    private static cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager mPager;
    String likeStatus = "";
    int likeCounts = 0;
    DialogBox dialogBox;
    public static DealsDetail instance;
    public List<ReviewListDTO> rlist = new ArrayList<>();
    String id = "";
    SharePref sharePref;
    List<String> name = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deals_detail);

        instance = this;
        dialogBox = new DialogBox(DealsDetail.this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getString("id");
        }

        sharePref = new SharePref(DealsDetail.this);
        name.add("name1");
        name.add("name2");
        name.add("name3");

        init();

        GetDealsDetails();

    }

    public void init(){
        iv_background = (ImageView) findViewById(R.id.iv_background);
        likeimg = (ImageView) findViewById(R.id.likeimg);
        back_button = (LinearLayout) findViewById(R.id.ll_left);
        reviewlist = (LinearLayout) findViewById(R.id.reviewlist);
        reviewlayout = (LinearLayout) findViewById(R.id.reviewlayout);

        writereview = (TextView) findViewById(R.id.writereview);
        dealsName = (TextView) findViewById(R.id.tv_outlet_name);
        dealsAdd = (TextView) findViewById(R.id.tv_putlet_adress1);
        dealsEnd = (TextView) findViewById(R.id.daystxt);
        liketxt = (TextView) findViewById(R.id.like);
        viewed = (TextView) findViewById(R.id.view);
        reviewtxt = (TextView) findViewById(R.id.review);
        share = (TextView) findViewById(R.id.share);
        description = (TextView) findViewById(R.id.description);
        discounted_price = (TextView) findViewById(R.id.discounted_price);
        dealsprice = (TextView) findViewById(R.id.dealsprice);
        viewall = (TextView) findViewById(R.id.viewall);

        mPager = (cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager)findViewById(R.id.pager);
      //  mPager = (ViewPager)findViewById(R.id.pager);
        back_button.setOnClickListener(this);
        writereview.setOnClickListener(this);
        likeimg.setOnClickListener(this);
        viewall.setOnClickListener(this);
        reviewlayout.setOnClickListener(this);
     }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ll_left:
            finish();
                break;

            case R.id.viewall:
                Intent intent1 = new Intent(DealsDetail.this,ReviewListActivity.class);
                intent1.putExtra("DealsDetailFlag",21);
                intent1.putExtra("store_id",id);
                startActivity(intent1);
                break;

            case R.id.reviewlayout:
                Intent intent2 = new Intent(DealsDetail.this,ReviewListActivity.class);
                intent2.putExtra("DealsDetailFlag",21);
                intent2.putExtra("store_id",id);
                startActivity(intent2);
                break;

            case R.id.likeimg:
                if (sharePref.getUserId().equals("null")) {
                    dialogBox.DialogBox("Please First Login..");
                } else {
                    Log.e("StoreDetail", "id : " + id + ", userId : " + sharePref.getUserId());
                    LikeStore();
                }
                break;

            case R.id.writereview:
                if(sharePref.getUserId().equals("null")) {
                    dialogBox.DialogBox("Please First Login..");
                } else {
                    Intent intent = new Intent(DealsDetail.this, WriteReview.class).putExtra("Deals_detail_flag", 7).putExtra("id",id);
                    startActivityForResult(intent, 101);
                }
                break;
        }
    }

    private void GetDealsDetails() {

        class DealDetails extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();
            Dialog dialogBox;
            String userId = "",result = "";
            JSONArray review_list_array = null;
            TransparentActivity transparentActivity;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressDialog = new ProgressDialog(DealsDetail.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();

                if (sharePref.getUserId().equals("null"))
                    userId = "";
                else
                    userId = sharePref.getUserId();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("deviceType", "2");
                    data.put("deal_id", id);
                    data.put("user_id", userId);

                    result =  ruc.sendPostRequest(AppConstants.BASEURL + "dealdetail", data);
                    Log.e("DealsDetails ", "doInBackground: "+ result);
                    Log.e("DealsDetails ", "result: "+ data);

                    return result;
                } catch (Exception e) {
                    Log.e("HOME Activity subcat", "Error " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s != null) {
                    try{
                        JSONArray imgAry = null;
                        JSONObject obj  = new JSONObject(s);

                        if (obj.has("deal")){
                            JSONArray jsonArray = obj.getJSONArray("deal");

                            for (int i=0; jsonArray.length()>i; i++){
                                JSONObject jobj = jsonArray.getJSONObject(i);

                                dealsName.setText(jobj.getString("deal_name"));
                                dealsAdd.setText(jobj.getString("store_state")+", "+jobj.getString("store_country"));

                                Date d1 = new SimpleDateFormat("yyyy-M-dd").parse((String) jobj.getString("start_time"));
                                Date d2 = new SimpleDateFormat("yyyy-M-dd").parse((String) jobj.getString("end_time"));
                                long diff = Math.abs(d1.getTime() - d2.getTime());
                                long diffDays = diff / (24 * 60 * 60 * 1000);
                                dealsEnd.setText(""+diffDays+" days");

                                liketxt.setText(jobj.getString("like_count"));
                                reviewtxt.setText(jobj.getString("review_count"));
                                viewed.setText(jobj.getString("viewed"));
                                share.setText(jobj.getString("viewed"));

                                description.setText(jobj.getString("description"));
                                dealsprice.setText(jobj.getString("deal_price"));
                                discounted_price.setText(jobj.getString("discounted_price"));

                                if (jobj.has("gallery_img"))
                                imgAry = jobj.getJSONArray("gallery_img");

                                if (jobj.has("review_list"))
                                    review_list_array = jobj.getJSONArray("review_list");

                                likeCounts = Integer.parseInt(jobj.getString("like_count"));

                                if (!sharePref.getUserId().equals("null")) {
                                    likeStatus = jobj.getString("user_like_status");
                                    if (likeStatus.equals("0")) {
                                        likeimg.setImageResource(R.drawable.like_2);
                                    } else if (likeStatus.equals("1")) {
                                        likeimg.setImageResource(R.drawable.like);
                                    }
                                }
                            }

                            if (!imgAry.equals(null)) {
                                for (int i = 0; imgAry.length() > i; i++) {
                                    JSONObject imgobj = imgAry.getJSONObject(i);
                                    ImagesArray.add(imgobj.getString("img"));
                                }
                                viewpager(ImagesArray);
                            }
                             if (!review_list_array.equals(null))
                                    for (int j = 0; review_list_array.length() > j; j++) {
                                        JSONObject reviewObj = review_list_array.getJSONObject(j);

                                        ReviewListDTO reviewDto = new ReviewListDTO();
                                        reviewDto.setId(reviewObj.getString("rev_id"));
                                        reviewDto.setName(reviewObj.getString("user_name"));
                                        reviewDto.setImg(reviewObj.getString("user_pic"));
                                        reviewDto.setView(reviewObj.getString("rev"));
                                        reviewDto.setTime(reviewObj.getString("modified"));

                                        rlist.add(reviewDto);

                                        view = getLayoutInflater().inflate(R.layout.review_list_adapter, null);
                                        view.setBackgroundColor(Color.parseColor("#ffffff"));

                                        utils.CircleImageView imageView = (utils.CircleImageView)view.findViewById(R.id.imageview);
                                        Picasso.with(DealsDetail.this).load(reviewObj.getString("user_pic")).placeholder(R.drawable.placeholder).into(imageView);

                                        TextView title = (TextView)view.findViewById(R.id.title);
                                        title.setText(reviewObj.getString("user_name"));

                                        TextView description = (TextView)view.findViewById(R.id.description);
                                        description.setText(reviewObj.getString("rev"));

                                        LinearLayout linearLayout1 = new LinearLayout(DealsDetail.this);
                                        linearLayout1.setBackgroundColor(Color.parseColor("#AAAAAA"));
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                                        linearLayout1.setLayoutParams(params);

                                        if (3>j) {
                                            reviewlist.addView(linearLayout1);
                                            reviewlist.addView(view);
                                        }
                                    }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    mProgressDialog.dismiss();
                }
                else {
                    Toast.makeText(DealsDetail.this, "Server Exceptions..", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }
        }
            DealDetails ru = new DealDetails();
            ru.execute();

    }

    public void viewpager(ArrayList<String> arraylist) {
        mPager.setAdapter(new SlidingImage_Adapter(getApplicationContext(), arraylist));
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
       int NUM_PAGES = arraylist.size();
        // Auto start of viewpager

        mPager.setInterval(3000);
      //  mPager.startAutoScroll();
       // mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void LikeStore() {
        new AsyncTask<String, Void, String>() {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();
            Dialog dialogBox;
            String result;
            int likeno, likeresult;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(DealsDetail.this);
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
                    data.put("deviceType", "2");
                    data.put("id", id);
                    data.put("user_id", sharePref.getUserId());
                    data.put("type", "deal");
                    data.put("status", (likeStatus.equals("") || likeStatus.equals("0")) ? (likeStatus = "1") : (likeStatus = "0"));

                    return ruc.sendPostRequest(AppConstants.BASEURL + "all_like_unlike", data);
                } catch (Exception e) {
                    Log.e("HOME Activity subcat", "Error " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    mProgressDialog.dismiss();

                    try {
                        JSONObject res = new JSONObject(s);


                        if (res.getString("msg").equals("Successful")) {
                            if (likeStatus.equals("") || likeStatus.equals("0")) {
                                if (likeCounts != 0) {
                                    likeCounts -= 1;
                                }

                                likeimg.setImageResource(R.drawable.like_2);
                            } else if (likeStatus.equals("1")) {
                                likeCounts += 1;
                                likeimg.setImageResource(R.drawable.like);
                            }

                            liketxt.setText(likeCounts + " " + getResources().getString(R.string.Likes));

                            Log.e("LIKESTORE", "" + res.getString("msg") + " : " + likeCounts + " : " + likeStatus);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            if(resultCode == 101) {
                ImagesArray.clear();
                reviewlist.removeAllViews();
                GetDealsDetails();

            }
        }
    }

    public static DealsDetail getInstance(){
        return instance;
    }
}
