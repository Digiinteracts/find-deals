package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.digit_25.finaldeal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 12-10-2016.
 */
public class CountryListAdapter extends ArrayAdapter<String> {

  //  List<String> name = null;
    String[] name= null;
    List<String> checkCategory = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public CountryListAdapter(Context context,     String[] name) {
        super(context,0, name);

        this.context = context;
        this.name = name;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView = null;
        view = inflater.inflate(R.layout.spinnertxt, null);

        TextView spinner_text = (TextView) view.findViewById(R.id.text);

        //spinner_text.setText(name.get(position));
        spinner_text.setText(name[position]);
        //  spinner_text.setTextSize(17);
        return view;
    }

   /* public View getDropDownView(int position, View convertView, ViewGroup parent) {

        //   LayoutInflater inflater = getLayoutInflater();
        View row = inflater.inflate(R.layout.spinne_txt, parent, false);
        TextView make = (TextView) row.findViewById(R.id.text);
        final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkbox);

        checkBox.setButtonDrawable(context.getResources().getDrawable(R.drawable.uncheckbtn));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                checkBox.setButtonDrawable(context.getResources().getDrawable(R.drawable.checkbtn));

            }
        });
        make.setText(name.get(position));
        //   make.setTextSize(17);
        return row;
    }*/

    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        //   LayoutInflater inflater = getLayoutInflater();
        View row = inflater.inflate(R.layout.countryspinertxt, parent, false);
        TextView make = (TextView) row.findViewById(R.id.text);

        make.setText(name[position]);
        //   make.setTextSize(17);
       // make.setTypeface(setfont("OpenSans-Regular.ttf"));
        return row;
    }
}

