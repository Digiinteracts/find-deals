package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.digit_25.finaldeal.R;

import java.util.List;

/**
 * Created by DigiT-25 on 04-10-2016.
 */
public class MyReviewAdapter extends ArrayAdapter<String> {

    LayoutInflater inflater;
    List<String> name = null;
    Context context;
    Animation animation = null;

    public MyReviewAdapter(Context context, List<String> name) {
        super(context, 0,name);

        this.context = context;
        this.name = name;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.my_review_adapter, null, true);

        TextView txt_review = (TextView)view.findViewById(R.id.txt_review);
        txt_review.setText(name.get(position));

        animation =  AnimationUtils.loadAnimation(context, R.anim.push_left_in);
        animation.setDuration(500);
        view.startAnimation(animation);
        animation = null;

        return view;
    }

}
