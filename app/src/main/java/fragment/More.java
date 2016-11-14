package fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.digit_25.finaldeal.LoginScreen;
import com.example.digit_25.finaldeal.MainActivity;
import com.example.digit_25.finaldeal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import utils.DialogBox;
import utils.SharePref;


public class More extends Fragment {

    View view = null;
    ToggleButton toggleButton;
    RelativeLayout ll_logout,ll_about;
    TextView engtxt;
    List<String> langlist = new ArrayList();
    SharePref pref;
    DialogBox dialogBox;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.more_screen, null);

        pref = new SharePref(getActivity());

        dialogBox = new DialogBox(getActivity());
        init(view);

        onClick();
        return view;
    }

    public void init(View view){
        toggleButton = (ToggleButton)view.findViewById(R.id.toggleButton);
        ll_logout = (RelativeLayout) view.findViewById(R.id.ll_logout);
        ll_about = (RelativeLayout) view.findViewById(R.id.ll_about);
       // lang_select = (Spinner) view.findViewById(R.id.spinner);
        engtxt = (TextView) view.findViewById(R.id.engtxt);


        //langlist.add("Select");
        langlist.add("English");
        langlist.add("Norwegian");


        if (!pref.getLangVal().equals("null"))
            if (pref.getLangVal().equals("English"))
                engtxt.setText("English");
        else
                engtxt.setText("Norwegian");

    }

    public void onClick(){
    toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

            }
        });

        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginScreen.class);
                getActivity().startActivity(intent);
                getActivity().finish();

                pref.clearUserId();
                pref.removeUserId();
            }
        });

        engtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialigbox();
            }
        });
    }




    public void spinnerDialigbox(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_spinner);

        final CheckBox engcheckbox = (CheckBox) dialog.findViewById(R.id.engcheckbox);

        engcheckbox.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.uncheckbtn));

        engcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                engcheckbox.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.checkbtn));
                pref.saveLangValue("English");

                dialogBox.selectLanguage("eg");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                dialog.dismiss();


            }
        });
        final CheckBox norcheckbox = (CheckBox) dialog.findViewById(R.id.norcheckbox);

        norcheckbox.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.uncheckbtn));

        norcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                norcheckbox.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.checkbtn));
                pref.saveLangValue("Norwegian");

                dialogBox.selectLanguage("nb");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                dialog.dismiss();

            }
        });

        if (!pref.getLangVal().equals("null"))
        if (pref.getLangVal().equals("English")){
            engcheckbox.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.checkbtn));
            norcheckbox.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.uncheckbtn));

        }
        else if (pref.getLangVal().equals("Norwegian")){
            norcheckbox.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.checkbtn));
            engcheckbox.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.uncheckbtn));

        }

       /* RelativeLayout englayout = (RelativeLayout)dialog.findViewById(R.id.englayout);
        englayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (!pref.getLangVal().equals("null"))

            }
        });

        RelativeLayout nblayout = (RelativeLayout)dialog.findViewById(R.id.nblayout);
        nblayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (!pref.getLangVal().equals("null"))

            }
        });*/


        dialog.show();
    }
}
