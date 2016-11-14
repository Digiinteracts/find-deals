package com.example.digit_25.finaldeal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import adapter.GalleryAdapter;
import utils.zoom.ExtendedViewPager;
import utils.zoom.TouchImageView;

/**
 * Created by DigiT-25 on 15-07-2016.
 */
public class SlidingImgs extends AppCompatActivity {

    int img_posi = 0;
   public List<String> imgslist;
    boolean check = true;
    TextView imgposition,totalimg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_img_activity);

        Bundle bundle = getIntent().getExtras();
        imgposition = (TextView)findViewById(R.id.imgposi);
        if(bundle != null){
            img_posi = bundle.getInt("imageposition");
        }
        GalleryAdapter detailGalllary = GalleryAdapter.getInstance();

        ImageView cancelbutn = (ImageView)findViewById(R.id.cancel);

        cancelbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ExtendedViewPager viewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(SlidingImgs.this,detailGalllary.getImglist());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(img_posi);

        String ss = ""+img_posi;
        String ss2 = ""+ detailGalllary.getImglist().size();
        totalimg = (TextView)findViewById(R.id.totalimg);
        totalimg.setText(ss2);

    }

    public void getImgs(List<String> IMG){

        this.imgslist =IMG;

    }
    private class ImagePagerAdapter extends PagerAdapter {
      //  Utils.ImageLoader imageLoader;
        //Loader loader;
        public List<String> imgslist;
        Context context;
        public ImagePagerAdapter(Context context,List<String> imgslist){
            this.imgslist = imgslist;
            this.context =context;
            //loader=new Loader(context);
          //  imageLoader = new Utils.ImageLoader(context);
        }

        @Override
        public int getCount() {

        return imgslist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = SlidingImgs.this;
            TouchImageView imageView = new TouchImageView(context);
            int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);

            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                imgposition.setText(String.valueOf(position));

             //   loader.displayImage(imgslist.get(position).getImage(), imageView);
            Picasso.with(context).load(imgslist.get(position)).placeholder(R.drawable.placeholder).into(imageView);
                ((ViewPager) container).addView(imageView, 0);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }
}
