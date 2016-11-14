package com.example.digit_25.finaldeal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.MyReviewAdapter;

public class ReviewList extends AppCompatActivity implements View.OnClickListener {

    ListView myReviewList;
    List<String> name = new ArrayList<>();
    ImageView backicn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        init();

        name.add("name1");
        name.add("name2");
        name.add("name3");
        name.add("name4");
        name.add("name5");
        name.add("name6");
        name.add("name7");
        name.add("name8");
        name.add("name9");

        MyReviewAdapter adapter = new MyReviewAdapter(ReviewList.this,name);
        myReviewList.setAdapter(adapter);
    }

    public void init(){
        myReviewList = (ListView)findViewById(R.id.listview);
        backicn = (ImageView) findViewById(R.id.backicn);


        backicn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.backicn:
                finish();
                break;
        }
    }
}
