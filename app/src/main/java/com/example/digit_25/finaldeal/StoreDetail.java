package com.example.digit_25.finaldeal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import adapter.ActiveDealsAdapter;
import adapter.GalleryAdapter;
import adapter.ReviewListAdapter;
import dto.ReviewListDTO;
import dto.StoreDealsDTO;
import it.sephiroth.android.library.widget.HListView;
import utils.AppConstants;
import utils.Constants;
import utils.DialogBox;
import utils.RequestHandler;
import utils.SharePref;

public class StoreDetail extends AppCompatActivity implements View.OnClickListener {

    WebView webView;
    LinearLayout back_button, like_btn, rating_btn,reviewlist,reviewslayout,activedeals_list;
    String rate = "", reviewText = "";
    TextView writereview, rating_value, followers_txt,viewall ,distance, like, review, storetime_txt, storeName, stateCountryName, phoneTxt, rating, address, storeTime,dealsviewall;
    it.sephiroth.android.library.widget.HListView gallery;
    //ListView activedeals_list;
    public List<StoreDealsDTO> dealsList = new ArrayList<>();
    public List<String> offpersent = new ArrayList<>();
    View view = null;
    public List<ReviewListDTO> rlist = new ArrayList<>();
    RelativeLayout phonelayout, rating_layout;
    String id, PhoneNo, likeStatus = "", user_like_status = "";
    int likeCounts = 0;
    SharePref sharePref;
    ImageView bannerImg, likeImg;
    Button rating_save_btn;
    RatingBar ratingBar;
    public static StoreDetail instance;
    boolean expended = false, checkLike = false;
    DialogBox dialogBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail);

        instance = this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
        }

        dialogBox = new DialogBox(this);
        sharePref = new SharePref(StoreDetail.this);

        init();

        GetStoreDetails();

        offpersent.add("10 %");
        offpersent.add("12 %");

    }

    public void init() {
        // webView = (WebView)findViewById(R.id.webview);
        //reviewlist = (ListView) findViewById(R.id.reviewlist);
        bannerImg = (ImageView) findViewById(R.id.iv_background);
        likeImg = (ImageView) findViewById(R.id.likeImg);

        back_button = (LinearLayout) findViewById(R.id.ll_left);
         reviewlist = (LinearLayout) findViewById(R.id.reviewlist);
        like_btn = (LinearLayout) findViewById(R.id.like_btn);
        rating_btn = (LinearLayout) findViewById(R.id.rating_btn);
        reviewslayout = (LinearLayout) findViewById(R.id.reviewslayout);
        activedeals_list = (LinearLayout) findViewById(R.id.activedeals_list);
        phonelayout = (RelativeLayout) findViewById(R.id.phonelayout);
        rating_layout = (RelativeLayout) findViewById(R.id.rating_layout);

        ratingBar = (RatingBar) findViewById(R.id.ratingbar);

        gallery = (HListView) findViewById(R.id.gallery1);
        writereview = (TextView) findViewById(R.id.writereview);
        followers_txt = (TextView) findViewById(R.id.followers_txt);
        phoneTxt = (TextView) findViewById(R.id.phoneTxt);
        stateCountryName = (TextView) findViewById(R.id.tv_putlet_adress1);
        storeName = (TextView) findViewById(R.id.tv_outlet_name);
        distance = (TextView) findViewById(R.id.distance);
        rating = (TextView) findViewById(R.id.rating);
        like = (TextView) findViewById(R.id.like);
        review = (TextView) findViewById(R.id.review);
        storetime_txt = (TextView) findViewById(R.id.storetime_txt);
        address = (TextView) findViewById(R.id.address);
        storeTime = (TextView) findViewById(R.id.storeTime);
        rating_value = (TextView) findViewById(R.id.rating_value);
        viewall = (TextView) findViewById(R.id.viewall);
        dealsviewall = (TextView) findViewById(R.id.dealsviewall);
        //activedeals_list = (ListView) findViewById(R.id.activedeals_list);
        rating_save_btn = (Button) findViewById(R.id.rating_save_btn);

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rating_value.setText(String.valueOf(ratingBar.getRating()));
                rate = String.valueOf(ratingBar.getRating());
                return false;
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (ratingBar.getRating() < 0.5f) {
                    ratingBar.setRating(0.5f);
                    rating_value.setText("0.5");
                }
            }
        });

        back_button.setOnClickListener(this);
        writereview.setOnClickListener(this);
        phonelayout.setOnClickListener(this);
        like_btn.setOnClickListener(this);
        rating_btn.setOnClickListener(this);
        rating_save_btn.setOnClickListener(this);
        viewall.setOnClickListener(this);
        reviewslayout.setOnClickListener(this);
        dealsviewall.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_left:
                finish();
                break;

            case R.id.viewall:
                Intent intent1 = new Intent(StoreDetail.this,ReviewListActivity.class);
                intent1.putExtra("StoreDetailsFlag",12);
                intent1.putExtra("store_id",id);
                startActivity(intent1);
                break;

            case R.id.dealsviewall:
                Intent intentviewall = new Intent(StoreDetail.this,DealsList.class);
                intentviewall.putExtra("store_id",id);
                startActivity(intentviewall);
                break;

            case R.id.reviewslayout:
                Intent intent2 = new Intent(StoreDetail.this,ReviewListActivity.class);
                intent2.putExtra("StoreDetailsFlag",12);
                intent2.putExtra("store_id",id);
                startActivity(intent2);
                break;

            case R.id.phonelayout:
                phoneDialogBox();
                break;

            case R.id.rating_btn:
                setRating();
                break;

            case R.id.rating_save_btn:
                rating_value.setText(String.valueOf(ratingBar.getRating()));
                rating_layout.animate().setDuration(200).translationY(-230);
                expended = false;

                if (sharePref.getUserId().equals("null")) {
                    dialogBox.DialogBox("Please First Login..");
                } else {
                    sendRating(null);
                }
                break;

            case R.id.like_btn:
                if (sharePref.getUserId().equals("null")) {
                    dialogBox.DialogBox("Please First Login..");
                } else {
                    Log.e("StoreDetail", "id : " + id + ", userId : " + sharePref.getUserId());
                    LikeStore();
                }
                break;

            case R.id.writereview:
                if (sharePref.getUserId().equals("null")) {
                    dialogBox.DialogBox("Please First Login..");
                } else {
                    Intent intent = new Intent(StoreDetail.this, WriteReview.class).putExtra("rating", rate);
                    startActivityForResult(intent, 101);
                }
                break;
        }
    }

    public void setRating() {
        expended = !expended;

        if (expended) {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                rating_layout.animate().setDuration(200).translationY(rating_layout.getMeasuredHeight());
            } else {
                rating_layout.animate().setDuration(200).translationY(rating_layout.getMeasuredHeight());
            }

            if (!rate.equals("")) {
                ratingBar.setRating(Float.valueOf(rate));
            }
        } else {
            rating_layout.animate().setDuration(200).translationY(-230);
            expended = false;
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
                mProgressDialog = new ProgressDialog(StoreDetail.this);
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
                    data.put("store_id", id);
                    data.put("user_lat", Constants.LATITUDE);
                    data.put("user_lng", Constants.LONGITUDE);
                    data.put("user_id", userId);

                    return ruc.sendPostRequest(AppConstants.BASEURL + "storedetail2", data);
                } catch (Exception e) {
                    Log.e("HOME Activity subcat", "Error " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                transparentActivity = TransparentActivity.getInstance();
                JSONArray img_array = null;
                JSONArray review_list_array = null,dealsarray = null;

                if (s != null) {
                    try {

                        JSONObject jsonObject = new JSONObject(s);

                        Log.e("store", "onPostExecute: " + s);

                        if (jsonObject.has("status")) {
                            if (jsonObject.getString("status").equals("1")) {
                                if (jsonObject.has("store")) {

                                    JSONArray storeArray = jsonObject.getJSONArray("store");

                                    for (int i = 0; storeArray.length() > i; i++) {
                                        JSONObject obj = storeArray.getJSONObject(i);

                                        storeName.setText(obj.getString("name"));
                                        stateCountryName.setText(obj.getString("state") + ", " + obj.getString("country"));
                                        followers_txt.setText(obj.getString("follow_count") + " " + getResources().getString(R.string.FolloWers));
                                        PhoneNo = obj.getString("phone_no");
                                        distance.setText(obj.getString("distance") + getResources().getString(R.string.KMAway));

                                        likeCounts = Integer.parseInt(obj.getString("like_count"));
                                        like.setText(obj.getString("like_count") + " " + getResources().getString(R.string.Likes));
                                        review.setText(obj.getString("like_count") + " " + getResources().getString(R.string.Reviews));
                                        storetime_txt.setText(getResources().getString(R.string.StoreTime) + ":");
                                        storeTime.setText(obj.getString("timings"));

                                        address.setText(obj.getString("address"));

                                        Picasso.with(StoreDetail.this).load(obj.getString("banner_img")).placeholder(R.drawable.placeholder).into(bannerImg);
                                        if (obj.has("gallery_img"))
                                            img_array = obj.getJSONArray("gallery_img");

                                        if (obj.has("reviews"))
                                            review_list_array = obj.getJSONArray("reviews");

                                        if(obj.has("deals"))
                                            dealsarray = obj.getJSONArray("deals");

                                        if (!sharePref.getUserId().equals("null")) {

                                            rate = obj.getString("user_rating");
                                            rating.setText(rate);
                                            rating_value.setText(rate);
                                            likeStatus = obj.getString("user_like_status");

                                            if (likeStatus.equals("0")) {
                                                likeImg.setImageResource(R.drawable.like_2);
                                            } else if (likeStatus.equals("1")) {
                                                likeImg.setImageResource(R.drawable.like);
                                            }
                                        }
                                    }

                                    if (!dealsarray.equals(null)){

                                        for (int i=0; dealsarray.length()>i; i++){

                                            final JSONObject dealobj = dealsarray.getJSONObject(i);

                                            final StoreDealsDTO dealsDTO = new StoreDealsDTO();
                                            dealsDTO.setId(dealobj.getString("deal_id"));
                                            dealsDTO.setName(dealobj.getString("deal_name"));
                                            dealsDTO.setDeal_price(dealobj.getString("deal_price"));
                                            dealsDTO.setDiscounted_price(dealobj.getString("discounted_price"));
                                            dealsDTO.setPercentage_discount(dealobj.getString("percentage_discount"));
                                            dealsDTO.setImg(dealobj.getString("deal_logo"));

                                            dealsList.add(dealsDTO);

                                            view = getLayoutInflater().inflate(R.layout.active_deals, null);
                                            view.setBackgroundColor(Color.parseColor("#ffffff"));


                                            final LinearLayout activedealsll = (LinearLayout) view.findViewById(R.id.activedealsll);
                                            Picasso.with(StoreDetail.this).load(dealobj.getString("deal_logo")).into(new Target() {
                                                @Override
                                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                    BitmapDrawable background = new BitmapDrawable(bitmap);
                                                    activedealsll.setBackgroundDrawable(background);
                                                }

                                                @Override
                                                public void onBitmapFailed(Drawable errorDrawable) {

                                                }

                                                @Override
                                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                                }
                                            });

                                            TextView offtxt = (TextView) view.findViewById(R.id.offtxt);
                                            offtxt.setText(dealobj.getString("percentage_discount")+" %");

                                            TextView offtxt2 = (TextView) view.findViewById(R.id.offtxt2);
                                            offtxt2.setText(dealobj.getString("percentage_discount")+" % off");

                                            TextView name = (TextView) view.findViewById(R.id.name);
                                            name.setText(dealobj.getString("deal_name"));

                                            LinearLayout linearLayout1 = new LinearLayout(StoreDetail.this);
                                            linearLayout1.setBackgroundColor(Color.parseColor("#ffffff"));
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3);
                                            linearLayout1.setLayoutParams(params);


                                            if (3>i) {
                                                activedeals_list.addView(linearLayout1);
                                                activedeals_list.addView(view);

                                                view.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent intent = new Intent(StoreDetail.this, DealsDetail.class);
                                                        intent.putExtra("id",dealsDTO.getId());
                                                        startActivity(intent);

                                                        Log.e("iiidd", "onClick: "+dealsDTO.getId() );
                                                    }
                                                });
                                            }
                                        }

                                       // ActiveDealsAdapter adapter = new ActiveDealsAdapter(StoreDetail.this, offpersent);
                                       // activedeals_list.setAdapter(adapter);
                                    }

                                    if (!img_array.equals(null)) {

                                        List<String> imgList = new ArrayList<>();

                                        for (int i = 0; img_array.length() > i; i++) {

                                            JSONObject imgObj = img_array.getJSONObject(i);
                                            imgList.add(imgObj.getString("img"));
                                        }
                                        GalleryAdapter galleryAdapter = new GalleryAdapter(StoreDetail.this, imgList);
                                        gallery.setAdapter(galleryAdapter);

                                    }

                                    if (!review_list_array.equals(null) || !review_list_array.equals(""))
                                        for (int j = 0; review_list_array.length() > j; j++) {

                                            JSONObject reviewObj = review_list_array.getJSONObject(j);

                                            ReviewListDTO reviewDto = new ReviewListDTO();
                                            reviewDto.setId(reviewObj.getString("review_id"));
                                            reviewDto.setName(reviewObj.getString("user_name"));
                                            reviewDto.setImg(reviewObj.getString("user_pic"));
                                            reviewDto.setView(reviewObj.getString("review_content"));
                                            reviewDto.setTime(reviewObj.getString("modified_time"));

                                            rlist.add(reviewDto);


                                            view = getLayoutInflater().inflate(R.layout.review_list_adapter, null);
                                            view.setBackgroundColor(Color.parseColor("#ffffff"));

                                            utils.CircleImageView imageView = (utils.CircleImageView) view.findViewById(R.id.imageview);
                                            Picasso.with(StoreDetail.this).load(reviewObj.getString("user_pic")).placeholder(R.drawable.placeholder).into(imageView);

                                            TextView title = (TextView) view.findViewById(R.id.title);
                                            title.setText(reviewObj.getString("user_name"));

                                            TextView description = (TextView) view.findViewById(R.id.description);
                                            description.setText(reviewObj.getString("review_content"));

                                            LinearLayout linearLayout1 = new LinearLayout(StoreDetail.this);
                                            linearLayout1.setBackgroundColor(Color.parseColor("#AAAAAA"));
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                                            linearLayout1.setLayoutParams(params);


                                            if (3>j){
                                            reviewlist.addView(linearLayout1);
                                            reviewlist.addView(view);
                                            }
                                        }
                                    mProgressDialog.dismiss();
                                }
                            } else {
                                Toast.makeText(StoreDetail.this, "Server Exceptions..", Toast.LENGTH_SHORT).show();
                                mProgressDialog.dismiss();
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(StoreDetail.this, "Server Exceptions..", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }
        }
        StoreData ru = new StoreData();
        ru.execute();
    }

    public void phoneDialogBox() {

        final Dialog dialog = new Dialog(StoreDetail.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.phone_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView phonno = (TextView) dialog.findViewById(R.id.phonno);
        phonno.setText(PhoneNo);

        phonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + PhoneNo));
                try {
                    startActivity(callIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
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
                mProgressDialog = new ProgressDialog(StoreDetail.this);
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
                    data.put("type", "store");
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

                                likeImg.setImageResource(R.drawable.like_2);
                            } else if (likeStatus.equals("1")) {
                                likeCounts += 1;
                                likeImg.setImageResource(R.drawable.like);
                            }

                            like.setText(likeCounts + " " + getResources().getString(R.string.Likes));

                            Log.e("LIKESTORE", "" + res.getString("msg") + " : " + likeCounts + " : " + likeStatus);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
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
                mProgressDialog = new ProgressDialog(StoreDetail.this);
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
                data.put("type", "store");
                data.put("user_id", sharePref.getUserId());
                data.put("id", id);

                if (reviewTxt == null) {
                    data.put("ref_type", "rate");
                } else {
                    data.put("ref_type", "review");
                    data.put("review_content", reviewTxt);
                }
                data.put("rate", rate);
                return ruc.sendPostRequest(AppConstants.BASEURL + "all_rating_review", data);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mProgressDialog.dismiss();
                if (s != null) {

                    try {
                        JSONObject res = new JSONObject(s);

                        if (res.getString("msg").equals("Successful")) {
                            rating.setText(rate);
                            reviewlist.removeAllViews();
                            GetStoreDetails();
                        }
                    } catch (JSONException e) {
                        mProgressDialog.dismiss();
                        e.printStackTrace();
                    }
                }

            }
        }.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == 101) {
                rate = data.getStringExtra("rating");
                reviewText = data.getStringExtra("review");
                rating.setText(rate);
                sendRating(reviewText);
            }
        }
    }

    public static StoreDetail getInstance(){
        return instance;
    }
}
