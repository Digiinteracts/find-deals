package fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.digit_25.finaldeal.R;
import com.example.digit_25.finaldeal.TransparentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.CategoryAdapter;
import dto.CatagoryDto;
import utils.AppConstants;
import utils.DialogBox;
import utils.GifView;
import utils.RequestHandler;
import utils.SharePref;

/**
 * Created by DigiT-25 on 14-09-2016.
 */
public class Category extends Fragment {

    View view = null;
    GridView gridView;
    SharePref pref;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.category,null);
        pref = new SharePref(getActivity());

        init(view);

        getCategoryData();

        return view;
    }

    public void init(View view){
        gridView = (GridView) view.findViewById(R.id.gridView1);
        //listview = (ListView) view.findViewById(R.id.listview);
    }

    private void getCategoryData() {

        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RequestHandler ruc = new RequestHandler();
            Dialog dialogBox;
            TransparentActivity transparentActivity;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressDialog = new ProgressDialog(getActivity());
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
              //  Intent intent = new Intent(getActivity(), TransparentActivity.class);
                //getActivity().startActivity(intent);

            }
            @Override
            protected String doInBackground(String... params) {
                try{
                    HashMap<String,String> data = new HashMap();
                    data.put("user_id",pref.getUserId());

                    String result = ruc.sendPostRequest(AppConstants.BASEURL + "getcat_subCat",data);
                    Log.e("home:anilapi", result);
                    return result;}
                catch (Exception e){
                    Log.e("HOME Activity subcat","Error "+e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                transparentActivity = TransparentActivity.getInstance();

                if (s != null) {
                    try {

                        JSONObject jsonObject = new JSONObject(s);

                        List<CatagoryDto> listdata = new ArrayList<>();
                        CatagoryDto catDto = new CatagoryDto();

                        if(jsonObject.has("subcategory")){

                            JSONArray subcat = jsonObject.getJSONArray("subcategory");

                                for (int i=0; subcat.length()>i; i++){
                                    JSONObject obj=subcat.getJSONObject(i);
                                    CatagoryDto catagoryDto = new CatagoryDto();

                                    catagoryDto.setCatagoryName(obj.getString("name"));
                                    catagoryDto.setCatagoryId(obj.getString("id"));
                                    catagoryDto.setCatagoryImg(obj.getString("img"));
                                    catagoryDto.setLike_unlike_status(obj.getString("like_unlike_status"));

                                    if (obj.getString("like_unlike_status").equals("0")){
                                        catagoryDto.setSetCheck(false);
                                    }
                                    else {
                                        catagoryDto.setSetCheck(true);
                                    }

                                    listdata.add(catagoryDto);
                                }
                            catDto.setCatList(listdata);

                            if (getActivity() != null) {
                                CategoryAdapter adapter = new CategoryAdapter(getActivity(), catDto.getCatList());
                                gridView.setAdapter(adapter);
                            }

                            mProgressDialog.dismiss();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(getActivity(),"Server Exceptions..",Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

}
