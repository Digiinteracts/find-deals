package fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.digit_25.finaldeal.DealsLike;
import com.example.digit_25.finaldeal.EditProfile;
import com.example.digit_25.finaldeal.R;
import com.example.digit_25.finaldeal.ReviewList;

import utils.DialogBox;
import utils.SharePref;

public class MyProfile extends Fragment {

    View view = null;
    TextView editprofile;
    SharePref sharePref;
    DialogBox dialogBox;
    LinearLayout ll_reviews,dealsLike,ll_stores_followed,ll_fav_category;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.my_profile,null);

        sharePref = new SharePref(getActivity());
        dialogBox = new DialogBox(getActivity());
        init(view);

        onClick();

        return view;
    }

    public void init(View view){
        editprofile = (TextView)view.findViewById(R.id.editprofile);
        ll_reviews = (LinearLayout) view.findViewById(R.id.ll_reviews);
        dealsLike = (LinearLayout) view.findViewById(R.id.ll_like);
        ll_stores_followed = (LinearLayout) view.findViewById(R.id.ll_stores_followed);
        ll_fav_category = (LinearLayout) view.findViewById(R.id.ll_fav_category);
    }

    public void onClick(){
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                getActivity().startActivity(intent);
            }
        });

        ll_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewList.class);
                getActivity().startActivity(intent);
            }
        });

        dealsLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharePref.getUserId().equals("null"))
                    dialogBox.DialogBox("Please First Login..");
                else {
                        Intent intent = new Intent(getActivity(), DealsLike.class);
                        intent.putExtra("userId",sharePref.getUserId());
                        getActivity().startActivity(intent);
                }
            }
        });

        ll_stores_followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharePref.getUserId().equals("null"))
                    dialogBox.DialogBox("Please First Login..");
                else {
                    Intent intent = new Intent(getActivity(), DealsLike.class);
                    intent.putExtra("userId",sharePref.getUserId());
                    getActivity().startActivity(intent);
                }
            }
        });

        ll_fav_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox.DialogBox("Coming Soon");
            }
        });
    }

}
