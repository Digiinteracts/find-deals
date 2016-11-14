package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.digit_25.finaldeal.MainActivity;
import com.example.digit_25.finaldeal.R;
import com.example.digit_25.finaldeal.TransparentActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ImageLoader.Loader;
import dto.CatagoryDto;
import utils.AppConstants;
import utils.DialogBox;
import utils.GifView;
import utils.RequestHandler;
import utils.SharePref;

/**
 * Created by DigiT-25 on 14-09-2016.
 */
public class CategoryAdapter extends ArrayAdapter<CatagoryDto> {

    List<CatagoryDto> data = null;
    Context context;
    Loader loader;
    View view;
    Dialog gifdialog;
    String id, status = "0";
    ;
    LayoutInflater inflater;
    SharePref sharePref;
    boolean check;
    DialogBox dialogBox;
    TransparentActivity transparentActivity;
    HashMap<Integer, Boolean> checkmap = new HashMap<>();


    public CategoryAdapter(Context context, List<CatagoryDto> data) {
        super(context, 0, data);

        this.context = context;
        // activity = (Activity) context;

        this.data = data;
        inflater = LayoutInflater.from(context);
        loader = new Loader(context);
        sharePref = new SharePref(context);
        dialogBox = new DialogBox(context);
        gifdialog = new Dialog(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        final Holder holder;
        if (view == null) {
            holder = new Holder();

            view = inflater.inflate(R.layout.category_adapter, null, true);

            holder.title = (utils.Boldtext) view.findViewById(R.id.title);
            holder.imageview = (ImageView) view.findViewById(R.id.imageview);
            holder.catlike = (ImageView) view.findViewById(R.id.catlike);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        checkmap.put(position, data.get(position).isSetCheck());
        if (checkmap.get(position)) {
            holder.catlike.setImageResource(R.drawable.fav_active);

        } else {
            holder.catlike.setImageResource(R.drawable.fav_unactive);
        }

        holder.title.setText(data.get(position).getCatagoryName());
        Picasso.with(context).load(data.get(position).getCatagoryImg()).placeholder(R.drawable.placeholder).into(holder.imageview);

        holder.catlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = data.get(position).getCatagoryId();

                if (String.valueOf(sharePref.getUserId()).equals("null"))
                    dialogBox.DialogBox("Please First Login..");
                else {

                    if (checkmap.get(position) == true) {
                        checkmap.put(position, false);
                        holder.catlike.setImageResource(R.drawable.fav_unactive);
                        data.get(position).setSetCheck(false);
                        status = "0";
                    } else {
                        checkmap.put(position, true);
                        holder.catlike.setImageResource(R.drawable.fav_active);
                        status = "1";
                        data.get(position).setSetCheck(true);
                    }
                    getCategoryData();
                }
            }
        });

        return view;
    }

    class Holder {
        TextView title;
        ImageView imageview, catlike;
    }

    private void getCategoryData() {

        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(context);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
               // Intent intent = new Intent(context, TransparentActivity.class);
               // context.startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    HashMap<String, String> data = new HashMap();
                    data.put("id", id);
                    data.put("user_id", sharePref.getUserId());
                    data.put("type", "category");
                    data.put("status", status);
                    data.put("deviceType", "2");

                    String result = ruc.sendPostRequest(AppConstants.BASEURL + "all_like_unlike", data);
                    Log.e("catagory:anilapi", result);
                    Log.e("catagory:dta", "d " + data);
                    return result;
                } catch (Exception e) {
                    Log.e("HOME Activity subcat", "Error " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        mProgressDialog.dismiss();
                       // transparentActivity = TransparentActivity.getInstance();
                        //transparentActivity.finishActivity();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                }
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

}
