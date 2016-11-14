package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.digit_25.finaldeal.R;

import java.util.ArrayList;
import java.util.List;

import model.RecyclerViewHolder;

/**
 * Created by DigiT-25 on 07-10-2016.
 */
public class DiscoverAdapter extends  RecyclerView.Adapter<RecyclerViewHolder>  {

    private List<String> name;
    Context context;
    LayoutInflater inflater;
    Integer[] imageId;

    public DiscoverAdapter(Context context,List<String> name, Integer[] imageId){

        this.context =context;
        this.name = name;
        this.imageId = imageId;
        Log.e("DISCOVER 22 ","d "+name.size());
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.discover_adapter, parent, false);

        RecyclerViewHolder viewHolder=new RecyclerViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.like_txt.setText(name.get(position));
        holder.mainlayoutbg.setBackgroundResource(imageId[position]);

       // holder.rate.setTag(holder);
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return name.size();
    }


    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return name.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}
