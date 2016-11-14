package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.digit_25.finaldeal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import dto.ReviewListDTO;

/**
 * Created by DigiT-25 on 29-09-2016.
 */
public class ReviewListAdapter extends ArrayAdapter<ReviewListDTO> {

    LayoutInflater inflater;
    List<ReviewListDTO> data = null;
    Context context;

    public ReviewListAdapter(Context context, List<ReviewListDTO> data) {
        super(context, 0,data);

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.review_list_adapter, null, true);

        TextView title = (TextView)view.findViewById(R.id.title);
        title.setText(data.get(position).getName());

        TextView description = (TextView)view.findViewById(R.id.description);
        description.setText(data.get(position).getView());

        utils.CircleImageView logo = (utils.CircleImageView)view.findViewById(R.id.imageview);
        Picasso.with(context).load(data.get(position).getImg()).placeholder(R.drawable.placeholder).into(logo);

        return view;
    }
}
