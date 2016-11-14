package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.digit_25.finaldeal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by DigiT-25 on 06-09-2016.
 */
public class DiscoverListAdapter extends ArrayAdapter<String>{

    List<String> name = null;
    Context context;
    Integer[] imageId;
    LayoutInflater inflater;

    public DiscoverListAdapter(Context context,List<String> name,Integer[] imageId) {
        super(context, 0,name);

        this. imageId = imageId;
        this.context = context;
        this.name = name;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.discover_adapter, null, true);

        TextView rate = (TextView)view.findViewById(R.id.rate);
        rate.setText(name.get(position));

        RelativeLayout bgimg = (RelativeLayout) view.findViewById(R.id.mainlayoutbg);
        bgimg.setBackgroundResource(imageId[position]);
        Log.e("DISCOVER 33 ","d "+name.get(position));
        return view;
    }
}
