package utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import dto.StoreDTO;

/**
 * Created by DigiT-25 on 14-09-2016.
 */
public class Constants {

    public static boolean HOME_TAG = true;
    public static String UNSELECTED_LOCATION="";
    public static String LATITUDE="";
    public static String LONGITUDE="";
    public static  int REQURIED_SIZE = 0;
    public static  List<StoreDTO> storeDtoList = new ArrayList<>();

    public static boolean checkPermission(String permission, Context context){
        int result = ContextCompat.checkSelfPermission(context,permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
