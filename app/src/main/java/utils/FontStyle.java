package utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by DigiT-25 on 22-08-2016.
 */
public class FontStyle {

    Context context ;
    Activity activity;

    public FontStyle(Context context){
        this.context = context;
        activity = (Activity) context;
    }

    public Typeface BRADHITC_Font(){
        return Typeface.createFromAsset(activity.getAssets(),"fonts/BRADHITC.TTF");
    }

    public Typeface MyriadPro_Bold(){
        return Typeface.createFromAsset(activity.getAssets(),"fonts/MyriadPro-Bold.ttf");
    }

    public Typeface MyriadPro_Regular(){
        return Typeface.createFromAsset(activity.getAssets(),"fonts/MyriadPro-Regular.ttf");
    }
}
