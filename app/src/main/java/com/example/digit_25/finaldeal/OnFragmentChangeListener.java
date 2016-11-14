package com.example.digit_25.finaldeal;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by DigiT-25 on 14-09-2016.
 */
public interface OnFragmentChangeListener {
    void onFragmentAdd(Fragment fragment, String TAG);

    void onFragmentReplace(Fragment fragment, String TAG);

   // void onFragmentReplace(Fragment fragment);

    void onFragmentAddWithArgument(Fragment fragment, String TAG, Bundle arguments);
}
