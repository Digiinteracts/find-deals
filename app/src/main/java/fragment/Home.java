package fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digit_25.finaldeal.DealsDetail;
import com.example.digit_25.finaldeal.MainActivity;
import com.example.digit_25.finaldeal.R;
import com.example.digit_25.finaldeal.StoreDetail;
import com.example.digit_25.finaldeal.Stores;
import com.example.digit_25.finaldeal.TransparentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import adapter.CategoryAdapter;
import adapter.Headerapdater;
import adapter.PopularStoreAdapter;
import dto.CatagoryDto;
import dto.DealsDetailsDTO;
import dto.StoreDTO;
import it.sephiroth.android.library.widget.HListView;
import utils.AppConstants;
import utils.Constants;
import utils.RequestHandler;

/**
 * Created by DigiT-25 on 24-08-2016.
 */
public class Home extends Fragment {
    PopularStoreAdapter adapter;
    View view = null;
    ListView store_listview;
   // GridView gridView1;
    public TextView txtaddress;
    List<StoreDTO> storeDtoList = new ArrayList<>();
    public static Home instance;
    LinearLayout near_by_layout,categorylayout,storeList;
    private HListView hliv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.home,null);

        Constants.HOME_TAG = true;
        instance = this;

        init(view);

        GetStoreData();

        OnClick();

        return view;
    }

    public void init(View view){


       // store_listview = (JazzyListView)view.findViewById(R.id.store_listview);
        store_listview = (ListView) view.findViewById(R.id.listview);
        near_by_layout = (LinearLayout) view.findViewById(R.id.near_by_layout);
        categorylayout = (LinearLayout) view.findViewById(R.id.categorylayout);
        storeList = (LinearLayout) view.findViewById(R.id.storeList);
        txtaddress = (TextView) view.findViewById(R.id.txtaddress);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View header = inflater.inflate(R.layout.header, store_listview, false);
        hliv = (HListView) header.findViewById(R.id.hl_v);
        //gridView1 = (GridView) header.findViewById(R.id.gridView1);
        store_listview.addHeaderView(header, null, false);

        txtaddress.setText(Constants.UNSELECTED_LOCATION);
    }

    public void OnClick(){
        storeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Stores.class);
                getActivity().startActivity(intent);
            }
        });

        hliv.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),DealsDetail.class);
                getActivity().startActivity(intent);
            }
        });

        near_by_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onFragmentReplace(new Discover(),"Discover");
                ((MainActivity)getActivity()).tabLayout.getTabAt(1).select();
            }
        });

        categorylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onFragmentAdd(new Category(),"Category");
                ((MainActivity)getActivity()).tabLayout.getTabAt(3).select();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Constants.HOME_TAG = false;
    }

    public static Home getInstance(){
        return instance;
    }

    private void GetStoreData() {

        class StoreData extends AsyncTask<String, Void, String> {
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

            }
            @Override
            protected String doInBackground(String... params) {
                try{
                    HashMap data = new HashMap();
                    String result = ruc.sendPostRequest(AppConstants.BASEURL + "storedetail",data);
                    Log.e("home:StoreData ", result);
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

                        List<StoreDTO> listdata = new ArrayList<>();
                        StoreDTO storeDTO = new StoreDTO();

                        if(jsonObject.has("store")){

                            JSONArray storeArray = jsonObject.getJSONArray("store");

                            for (int i=0; storeArray.length()>i; i++){
                                JSONObject obj=storeArray.getJSONObject(i);
                                StoreDTO strDto = new StoreDTO();

                                strDto.setName(obj.getString("name"));
                                strDto.setStoreId(obj.getString("id"));
                                strDto.setImg(obj.getString("img"));
                                strDto.setAddress(obj.getString("address"));
                                strDto.setTiming(obj.getString("timings"));
                                strDto.setCategoryId(obj.getString("cat_id"));
                                strDto.setCategory(obj.getString("category"));
                                strDto.setCity(obj.getString("city"));
                                strDto.setState(obj.getString("state"));
                                strDto.setCountry(obj.getString("country"));

                                listdata.add(strDto);
                            }
                            storeDTO.setStoreList(listdata);

                            storeDtoList = storeDTO.getStoreList();

                            if (getActivity() != null) {
                                adapter = new PopularStoreAdapter(getActivity(), storeDTO.getStoreList());
                                store_listview.setAdapter(adapter);
                            }

                            mProgressDialog.dismiss();
                        } /*else if(jsonObject.has("status")) {
                            Toast.makeText(getActivity(),"Server Exceptions..",Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }*/
                        if(jsonObject.has("deal")) {
                            JSONArray array = jsonObject.getJSONArray("deal");
                            List<DealsDetailsDTO> Dlist = new ArrayList<>();

                            for(int i=0; i<array.length(); i++) {
                                JSONObject dealsobj = array.getJSONObject(i);
                                DealsDetailsDTO dealsDto = new DealsDetailsDTO();

                                dealsDto.setDeals_Id(dealsobj.getString("deal_id"));
                                dealsDto.setName(dealsobj.getString("deal_name"));
                                dealsDto.setImg(dealsobj.getString("deal_logo"));

                                Dlist.add(dealsDto);
                            }

                            if (getActivity() != null) {
                                Headerapdater headerapdater = new Headerapdater(getActivity(), Dlist);
                                hliv.setAdapter(headerapdater);
                            }
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
        StoreData ru = new StoreData();
        ru.execute();
    }
}
