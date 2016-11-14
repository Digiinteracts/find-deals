package fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.digit_25.finaldeal.R;

import java.util.ArrayList;
import java.util.List;

import adapter.DiscoverAdapter;
import adapter.DiscoverListAdapter;
import utils.Constants;
import utils.FlipListener;

/**
 * Created by DigiT-25 on 24-08-2016.
 */
public class Discover extends Fragment {

    View view = null;
   // ListView listview;
    RecyclerView listview;
    LinearLayout listviewlayout;
    List<String> name = new ArrayList<>();
    ValueAnimator mFlipAnimator;
    ImageView mapicn,map;
    boolean check = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.discover,null);

        init(view);

        name.add("4 like");
        name.add("4 like");
        name.add("4 like");
        name.add("4 like");
        name.add("4 like");
        name.add("4 like");
        name.add("4 like");
        name.add("4 like");
        name.add("4 like");

        Integer[] imageId = {
                R.drawable.pic_3,
                R.drawable.pic_4,
                R.drawable.pic_3,
                R.drawable.pic_4,
                R.drawable.pic_3,
                R.drawable.pic_4,
                R.drawable.pic_3,
                R.drawable.pic_4,
                R.drawable.pic_3
        };

        //DiscoverListAdapter adapter = new DiscoverListAdapter(getActivity(),name,imageId);
        DiscoverAdapter adapter = new DiscoverAdapter(getActivity(),name,imageId);
        //  CategoryAdapter adapter = new CategoryAdapter(getActivity(),name,imageId);
        listview.setAdapter(adapter);

        listview.setHasFixedSize(true);
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));

        onClick();

        return view;
    }

    public void init(View view){
      //  listview = (ListView)view.findViewById(R.id.listview);
        listview = (RecyclerView)view.findViewById(R.id.listview);
        mapicn = (ImageView) view.findViewById(R.id.mapview);
        map = (ImageView) view.findViewById(R.id.map);
        listviewlayout = (LinearLayout) view.findViewById(R.id.listviewlayout);
    }

    public void onClick(){
        mapicn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check) {
                    mapicn.setImageResource(R.drawable.flipicn);
                    check = false;
                    mFlipAnimator = ValueAnimator.ofFloat(0f, 1f);
                    mFlipAnimator.addUpdateListener(new FlipListener(listviewlayout, map));
                    mFlipAnimator.start();
                }
                else {
                    mapicn.setImageResource(R.drawable.map_icon);
                    check = true;
                    mFlipAnimator.reverse();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Constants.HOME_TAG = true;
    }
}
