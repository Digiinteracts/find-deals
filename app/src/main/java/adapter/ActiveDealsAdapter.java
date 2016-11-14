package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.digit_25.finaldeal.DealsDetail;
import com.example.digit_25.finaldeal.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import dto.StoreDealsDTO;

/**
 * Created by DigiT-25 on 06-10-2016.
 */
public class ActiveDealsAdapter extends ArrayAdapter<StoreDealsDTO> {

    List<StoreDealsDTO> data = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public ActiveDealsAdapter(Context context, List<StoreDealsDTO> data) {
        super(context, 0,data);

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.active_deals,null);

        TextView offtxt = (TextView)view.findViewById(R.id.offtxt);
        offtxt.setText(data.get(position).getPercentage_discount()+" %");

        TextView offtxt2 = (TextView)view.findViewById(R.id.offtxt2);
        offtxt2.setText(data.get(position).getPercentage_discount()+" % off");

        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(data.get(position).getName());

        final LinearLayout activedealsll = (LinearLayout) view.findViewById(R.id.activedealsll);
        Picasso.with(context).load(data.get(position).getImg()).into(new Target() {
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DealsDetail.class);
                intent.putExtra("id",data.get(position).getId());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
