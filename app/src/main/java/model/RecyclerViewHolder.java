package model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.digit_25.finaldeal.R;


/**
 * Created by Sonu Saini 10/26/2015.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

   public TextView rate,like_txt;

    public RelativeLayout mainlayoutbg;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        like_txt= (TextView) itemView.findViewById(R.id.like_txt);
        mainlayoutbg= (RelativeLayout) itemView.findViewById(R.id.mainlayoutbg);
        //txt_id=(TextView)itemView.findViewById(R.id.text_xat_id);

    }
}
