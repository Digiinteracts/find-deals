package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.digit_25.finaldeal.DealsDetail;
import com.example.digit_25.finaldeal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import dto.DealsDetailsDTO;
import utils.Constants;

/**
 * Created by DigiT-25 on 29-09-2016.
 */
public class Headerapdater extends ArrayAdapter<DealsDetailsDTO> {
    LayoutInflater inflater;
    Context context;
    List<DealsDetailsDTO> data = null;

    public Headerapdater(Context context,List<DealsDetailsDTO> data) {
        super(context,0 ,data);

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.header_adapter, null, true);

        utils.Boldtext title = (utils.Boldtext)view.findViewById(R.id.title);
        title.setText(data.get(position).getName());

        ImageView imageview = (ImageView)view.findViewById(R.id.imageview);
        Picasso.with(context).load(data.get(position).getImg()).placeholder(R.drawable.placeholder).into(imageview);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DealsDetail.class);
                intent.putExtra("id",data.get(position).getDeals_Id());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
