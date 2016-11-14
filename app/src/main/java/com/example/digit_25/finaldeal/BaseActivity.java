package com.example.digit_25.finaldeal;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class BaseActivity extends FragmentActivity implements OnFragmentChangeListener{

    public static FragmentManager fragmentManager = null;
    LinearLayout homelayout,discoverlayout,myacc_layout,morelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        fragmentManager = getSupportFragmentManager();


    }

    @Override
    public void onFragmentAdd(Fragment fragment, String TAG) {

        Fragment existingFragment = fragmentManager.findFragmentByTag(TAG);
        if (existingFragment == null) {
            fragmentManager.beginTransaction().add(R.id.customFragment, fragment).addToBackStack(TAG).commit();
        } else {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void onFragmentReplace(Fragment fragment, String TAG) {
        Fragment existingFragment = fragmentManager.findFragmentByTag(TAG);
        if (existingFragment == null) {
            fragmentManager.beginTransaction().replace(R.id.customFragment, fragment).addToBackStack(TAG).commit();
        } else {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
        fragmentManager.executePendingTransactions();

    }

 /*   @Override
    public void onFragmentReplace(Fragment fragment) {

        fragmentManager.beginTransaction().replace(R.id.customFragment, fragment).commit();
        fragmentManager.executePendingTransactions();


    }*/
    public void replaceFragment(Fragment fragment) {
       // FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.customFragment, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    @Override
    public void onFragmentAddWithArgument(Fragment fragment, String TAG, Bundle arguments) {
        fragment.setArguments(arguments);

        Fragment existingFragment = fragmentManager.findFragmentByTag(TAG);
        if (existingFragment == null) {
            fragmentManager.beginTransaction().add(R.id.customFragment, fragment).addToBackStack(TAG).commit();
        } else {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
        fragmentManager.executePendingTransactions();

    }

    @Override
    public void onBackPressed() {
        int fragments = fragmentManager.getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            // make layout invisible since last fragment will be removed
        }
        else {
            fragmentManager.popBackStack();
        }
    }

}
