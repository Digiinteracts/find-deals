package com.example.digit_25.finaldeal;

import android.*;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Handler;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import android.location.Address;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import fragment.Category;
import fragment.Discover;
import fragment.Home;
import fragment.More;
import fragment.MyProfile;
import utils.Constants;
import utils.DialogBox;
import utils.WebApiResult;
import utils.WebRequest;
import utils.floatingActionMenu.FloatingActionButton;
import utils.floatingActionMenu.FloatingActionsMenu;

public class MainActivity extends BaseActivity implements View.OnClickListener, WebApiResult,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {

    ProgressDialog progressDialog;
    public TabLayout tabLayout;
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static FragmentManager fragmentManager;
   // public ViewPager viewPager;
   public static FloatingActionButton floating_button;
    public static FloatingActionsMenu floatingActionsMenu;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    DialogBox dialogBox;
    Location mLastLocation = null;
    Handler mHandler;
    Home home;
 //   LocationRequest mLocationRequest;
    Runnable runnable;
    Timer myTimer;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setLangRecreate("hi");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        mGoogleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();

        dialogBox = new DialogBox(MainActivity.this);
        home = new Home();

        if(Constants.checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,this)){
            if (checkPlayServices()) {
                buildGoogleApiClient();
                // displayLocation();
            }
        }
        else {
            requestPermission();
        }

        onFragmentAdd(home,"Home");
        init();

        setupTabIcons();
        bindWidgetsWithAnEvent();
    }

    public void init(){
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        floating_button = (utils.floatingActionMenu.FloatingActionButton)findViewById(R.id.addfabButton);

        floating_button.setVisibility(View.VISIBLE);
        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                CustomFragment cs = new CustomFragment();
                cs.show(fragmentTransaction, "floatingactionmenu");
                floating_button.setVisibility(View.GONE);
            }
        });
    }

    public void setupTabIcons() {

        TextView hometab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        hometab.setText(R.string.Home);
        hometab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_tab_style, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(hometab));

        TextView nearbytab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        nearbytab.setText(R.string.nearby);
        nearbytab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.discover_tab_style, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(nearbytab));

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("");
       // tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.user, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabThree));

        TextView myaccounttab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        myaccounttab.setText(R.string.Categories);
        myaccounttab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.my_account_style, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(myaccounttab));

        TextView moretab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        moretab.setText(R.string.More);
        moretab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.more_style, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(moretab));

    }

    private void bindWidgetsWithAnEvent()
    {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :
                replaceFragment(new Home());
                break;
            case 1 :
                replaceFragment(new Discover());
                break;

            case 2 :
               // replaceFragment(new Category());
                break;

            case 3 :
                replaceFragment(new Category());
                break;

            case 4 :
                replaceFragment(new More());
                break;
        }
    }



    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Home(), "Home");
        adapter.addFragment(new Discover(), "Discover");
        adapter.addFragment(new Home(), "");
        adapter.addFragment(new Category(), "My Account");
        adapter.addFragment(new Home(), "More");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getWebResult(JSONObject result) {
        Log.e("MainActivity", "getWebResult: " );
        if (!String.valueOf(result.toString()).equals(null)) {
            Log.e("result=" , result.toString());
            try {
                JSONArray Result = result.getJSONArray("results");
                JSONArray  address_components = Result.getJSONObject(0).getJSONArray("address_components");
                String CityName ="";
                String StateName = "";
                for (int i=0 ;i <address_components.length(); i++){
                    JSONArray Types = address_components.getJSONObject(i).getJSONArray("types");
                    for (int j = 0;j <Types.length();j++ ){
                        if (Types.getString(j).equals("sublocality_level_1") ) {
                            CityName = address_components.getJSONObject(i).getString("long_name");
                            if (CityName.equals("Al Souq Al Kabeer"))
                                CityName = "Meena Bazaar";
                        }
                        else  if (Types.getString(j).equals("administrative_area_level_1")){
                            StateName = address_components.getJSONObject(i).getString("long_name");
                        }
                    }
                }
                progressDialog.dismiss();
                Log.e("CityName ",CityName);
                Log.e("StateName ",StateName);
                Constants.UNSELECTED_LOCATION =CityName+", "+StateName;
                home.txtaddress.setText(CityName+", "+StateName);
                //txt_address .setText(CityName+", "+StateName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else Log.e("Web services response ", "Null");
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public class CustomFragment extends DialogFragment {
        public CustomFragment() {}

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.floationactionmenu, container, false);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));

            floatingActionsMenu = (FloatingActionsMenu)v.findViewById(R.id.addButton);
            FloatingActionButton favoritecategories = (FloatingActionButton)v.findViewById(R.id.favoritecategories);
            FloatingActionButton myprofile = (FloatingActionButton)v.findViewById(R.id.myprofile);
            FloatingActionButton favoritestores = (FloatingActionButton)v.findViewById(R.id.favoritestores);

            myprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    floatingActionsMenu.collapse();
                    FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction ft1 = fm1.beginTransaction();
                    ft1.replace(R.id.customFragment, new MyProfile());
                    ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft1.commit();
                    tabLayout.getTabAt(2).select();
                }
            });

            favoritestores.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    floatingActionsMenu.collapse();
                    dialogBox.DialogBox("Coming soon");
                }
            });

            favoritecategories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    floatingActionsMenu.collapse();
                    dialogBox.DialogBox("Coming soon");
                    //replaceFragment(new MyProfile());
                }
            });

            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                   floatingActionsMenu.collapse();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getDialog().dismiss();
                        }
                    }, 80);
                    return false;
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    floatingActionsMenu.expand();
                }
            }, 80);

            floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
                @Override
                public void onMenuExpanded() {}

                @Override
                public void onMenuCollapsed() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getDialog().dismiss();
                        }
                    }, 80);
                }
            });

            return v;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            floating_button.setVisibility(View.VISIBLE);
        }
    }

    //get location====================================================

    public void GetLocation(){
         Log.e("long lat", "home1 " );
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.e("long lat", "home2 " );
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.e("long lat", "home4 "+mLastLocation );
        if (mLastLocation != null) {
         Constants.LATITUDE= String.valueOf(mLastLocation.getLatitude());
         Constants.LONGITUDE= String.valueOf(mLastLocation.getLongitude());

            WebRequest request = new WebRequest(getApplicationContext(), "http://maps.google.com/maps/api/geocode/json?latlng="+mLastLocation.getLatitude()+","+mLastLocation.getLongitude()+"&output=json&sensor=false", this, true);
            request.execute();
            mHandler.removeCallbacksAndMessages(null);
            myTimer.cancel();
            Log.e("long lat", "ssss "+ Constants.LATITUDE);
        }
        else{
            // Toast.makeText(getApplicationContext(), "(Couldn't get the location. Make sure location is enabled on the device)", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayLocation() {
        try {
            mHandler = new Handler();
            runnable = new Runnable() {
                public void run() {
                    GetLocation();
                }
            };

            myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    mHandler.post(runnable);

                }
            },0, 200);
        }
        catch (Exception e){
            e.getMessage();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
      //  createLocationRequest();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //  Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION)){

            Toast.makeText(this,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"Permission Granted, Now you can access location data..",Toast.LENGTH_LONG).show();
                    // Snackbar.make(view,"Permission Denied, You cannot access location data.",Snackbar.LENGTH_LONG).show();
                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                        // displayLocation();
                    }

                } else {
                    Toast.makeText(this,"Permission Granted, Now you can access location data..",Toast.LENGTH_LONG).show();
                    // Snackbar.make(view,"Permission Denied, You cannot access location data.",Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }

}
