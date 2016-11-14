package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.digit_25.finaldeal.R;
import com.example.digit_25.finaldeal.SlidingImgs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 06-10-2016.
 */
public class GalleryAdapter extends ArrayAdapter {

    LayoutInflater inflater;
    Context context;
    public static GalleryAdapter instance;
    public List<String> imgList = new ArrayList<>();

    public GalleryAdapter(Context context, List<String> imgList) {
        super(context, 0,imgList);
        instance = this;
        this.context =context;
        this.imgList = imgList;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gallery, null);

            ImageView imageview = (ImageView) convertView.findViewById(R.id.image_gallary);
            Picasso.with(context).load(imgList.get(position)).placeholder(R.drawable.placeholder).into(imageview);

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SlidingImgs.class);
                    intent.putExtra("imageposition",position);
                    context.startActivity(intent);

                }
            });
        }
        return convertView;
    }

    public List<String> getImglist(){
        return imgList;
    }

    public static GalleryAdapter getInstance(){
        return instance;
    }
}
