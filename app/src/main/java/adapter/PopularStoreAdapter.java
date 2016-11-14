package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digit_25.finaldeal.MainActivity;
import com.example.digit_25.finaldeal.R;
import com.example.digit_25.finaldeal.StoreDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

import dto.StoreDTO;

/**
 * Created by DigiT-25 on 14-09-2016.
 */
public class PopularStoreAdapter extends ArrayAdapter<StoreDTO> {

    Context context;
    LayoutInflater inflater;
    List<StoreDTO> data = null;
    Animation animation = null;

    public PopularStoreAdapter(Context context, List<StoreDTO> data) {
        super(context, 0,data);

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

       View view = convertView;
        final Holder holder;

        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.populatdealadapter, null, true);

            holder.title = (utils.Boldtext) view.findViewById(R.id.title);
            holder.stateCountry = (TextView)view.findViewById(R.id.description);
            holder.imageview = (ImageView)view.findViewById(R.id.imageview);

            view.setTag(holder);
        }
        else {
            holder = (Holder) view.getTag();
        }

        holder.title.setText(data.get(position).getName());
        holder.stateCountry.setText(data.get(position).getState() + ", " + data.get(position).getCountry());
        Picasso.with(context).load(data.get(position).getImg()).placeholder(R.drawable.placeholder).into(holder.imageview);

        animation =  AnimationUtils.loadAnimation(context, R.anim.push_left_in);
        animation.setDuration(500);
        view.startAnimation(animation);
        animation = null;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StoreDetail.class);
                intent.putExtra("id",data.get(position).getStoreId());
                context.startActivity(intent);
            }
        });

        return view;
    }

    class Holder {
        TextView title,stateCountry;
        ImageView imageview, catlike;
    }
}
