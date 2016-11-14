package utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by DigiT-25 on 14-09-2016.
 */
public class Boldtext extends TextView {

    public Boldtext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Boldtext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Boldtext(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/MyriadPro-Bold.ttf");
        setTypeface(tf);
    }

}
