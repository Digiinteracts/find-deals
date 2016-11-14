package utils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
/**
 * Created by DigiT-25 on 14-09-2016.
 */
public interface OnFragmentChangeListener {
    public void onFragmentAdd(Fragment fragment, String TAG);
    public void onFragmentReplace(Fragment fragment, String TAG);
    public void onFragmentReplace(Fragment fragment);
    public void onFragmentAddWithArgument(Fragment fragment, String TAG, Bundle arguments);
}
