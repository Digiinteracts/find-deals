package utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.digit_25.finaldeal.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by DigiT-25 on 29-09-2016.
 */
public class DialogBox {

    Context context;

    public DialogBox(Context context){
        this.context =context;
    }
    public void helper(ListView myListView, List name) {
        int unitlistsize = name.size();
        if (unitlistsize != 0) {
            ListAdapter myListAdapter = myListView.getAdapter();
            if (myListAdapter == null) {
                //do nothing return null
                return;
            }
            int totalHeight = 0;
            for (int size = 0; size < myListAdapter.getCount(); size++) {
                View listItem = myListAdapter.getView(size, null, myListView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = myListView.getLayoutParams();
            params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
            myListView.setLayoutParams(params);
        }
    }

    public void DialogBox(String msg) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(15);
        drawable.setStroke(1, Color.parseColor("#AAAAAA"));
        drawable.setColor(Color.parseColor("#FFFFFF"));

        GradientDrawable drawable1 = new GradientDrawable();
        drawable1.setShape(GradientDrawable.RECTANGLE);
        drawable1.setCornerRadius(15);
        drawable1.setStroke(1, Color.parseColor("#AAAAAA"));
        drawable1.setColor(Color.parseColor("#ffffff"));


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView title = (TextView) dialog.findViewById(R.id.title);

        RelativeLayout titlelayout = (RelativeLayout) dialog.findViewById(R.id.titlelayout);
        RelativeLayout oklayout = (RelativeLayout) dialog.findViewById(R.id.oklayout);

        titlelayout.setBackgroundDrawable(drawable1);
        oklayout.setBackgroundDrawable(drawable);

        title.setText(msg);

        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener()
                              {
                                  @Override
                                  public void onClick (View v){
                                      dialog.dismiss();
                                  }
                              }
        );


        dialog.show();
    }

    public void selectLanguage(String lang){
        //  String languageToLoad = "nb"; // your language
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());

    }
}
