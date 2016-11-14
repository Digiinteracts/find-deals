package com.example.digit_25.finaldeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import adapter.CountryListAdapter;
import utils.AppConstants;
import utils.DialogBox;
import utils.FontStyle;
import utils.RegisterUserClass;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    TextView term_condition_txt;
    Button shop_owner_register,user_register;
    EditText user_name_editText,password_edittext,company_edittxt,address_edtxt,zipcod_editText,city_editText,companyDistrict_edittxt,orgno_edittxt,
            email_edittxt,phon_edittxt,fname,lname,phone,email,password;
    FontStyle fontStyle;
    Spinner countryspin;
    CheckBox usercheckbox,ownercheckbox;
    LinearLayout userlayout,shopownerlayout,ll_back;
    String user_fname,user_lname, user_phone, user_email, user_password,device_id,app_version,owner_uname,owner_pass,owner_comp,own_add,owner_zipcode,owner_city, owner_countryDistrict,owner_orgno,owner_email,owner_phon,firstname,lastname,emailtxt,facebook_id = "",gplus_id="";
    PackageInfo pInfo = null;
    DialogBox dialogBox;

    public static String[] country = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",

            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria",

            "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",

            "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",

            "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei", "Bulgaria",

            "Burkina Faso", "Burma (Myanmar)", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",

            "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island",

            "Cocos (Keeling) Islands", "Colombia", "Comoros", "Cook Islands", "Costa Rica",

            "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo",

            "Denmark", "Djibouti", "Dominica", "Dominican Republic",

            "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",

            "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia",

            "Gabon", "Gambia", "Gaza Strip", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",

            "Greenland", "Grenada", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",

            "Haiti", "Holy See (Vatican City)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",

            "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica",

            "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait",

            "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",

            "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia",

            "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mayotte", "Mexico",

            "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco",

            "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia",

            "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea",

            "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama",

            "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",

            "Portugal", "Puerto Rico", "Qatar", "Republic of the Congo", "Romania", "Russia", "Rwanda",

            "Saint Barthelemy", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin",

            "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino",

            "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",

            "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea",

            "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland",

            "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau",

            "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands",

            "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "US Virgin Islands", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",

            "Wallis and Futuna", "West Bank", "Yemen", "Zambia", "Zimbabwe"};

    public static String[]  code = new String[]{"+93", "+355", "+213", "+1 684", "+376", "+244", "+1 264", "+672", "+1 268", "+54", "+374",

            "+297", "+61", "+43", "+994", "+1 242", "+973", "+880", "+1 246", "+375", "+32", "+501",

            "+229", "+1 441", "+975", "+591", "+387", "+267", "+55", "+246", "+1 284", "+673", "+359",

            "+226", "+95", "+257", "+855", "+237", "+1", "+238", "+1 345", "+236", "+235", "+56", "+86",

            "+61", "+891", "+57", "+269", "+682", "+506", "+385", "+53", "+357", "+420", "+243", "+45",

            "+253", "+1 767", "+1 849", "+1 829", "+1 809", "+593", "+20", "+503", "+240", "+291", "+372",

            "+251", "+500", "+298", "+679", "+358", "+33", "+689", "+241", "+220", "+970", "+995", "+49",

            "+233", "+350", "+30", "+299", "+1 473", "+1 671", "+502", "+224", "+245", "+592", "+509",

            "+379", "+504", "+852", "+36", "+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972",

            "+39", "+225", "+1 876", "+81", "+44", "+962", "+7", "+254", "+686", "+381", "+965", "+996",

            "+856", "+371", "+961", "+266", "+231", "+218", "+423", "+370", "+352", "+853", "+389",

            "+261", "+265", "+60", "+960", "+223", "+356", "+692", "+222", "+230", "+262", "+52", "+691",

            "+373", "+377", "+976", "+382", "+1 664", "+212", "+258", "+264", "+674", "+977", "+31",

            "+599", "+687", "+64", "+505", "+227", "+234", "+683", "+672", "+850", "+1 670", "+47",

            "+968", "+92", "+680", "+507", "+675", "+595", "+51", "+63", "+870", "+48", "+351", "+1",

            "+974", "+242", "+40", "+7", "+250", "+590", "+290", "+1 869", "+1 758", "+1 599", "+508",

            "+1 784", "+685", "+378", "+239", "+966", "+221", "+381", "+248", "+232", "+65", "+421",

            "+386", "+677", "+252", "+27", "+82", "+34", "+94", "+249", "+597", "+268", "+46", "+41",

            "+963", "+886", "+992", "+255", "+66", "+670", "+228", "+690", "+676", "+1 868", "+216",

            "+90", "+993", "+1 649", "+688", "+256", "+380", "+971", "+44", "+1", "+598", "+1 340",

            "+998", "+678", "+58", "+84", "+681", "+970", "+967", "+260", "+263"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        fontStyle = new FontStyle(this);
        dialogBox = new DialogBox(SignUp.this);

        init();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            firstname = bundle.getString("first_name");
            lastname = bundle.getString("last_name");
            emailtxt = bundle.getString("email");

            int fb_flag = bundle.getInt("fb_flag");
            if (fb_flag == 7)
            facebook_id = bundle.getString("facebook_id");

            else
                gplus_id =bundle.getString("gplus_id");

            user_name_editText.setText(firstname+" "+lastname);
            email_edittxt.setText(emailtxt);

            fname.setText(firstname);
            lname.setText(lastname);
            email.setText(emailtxt);
        }

        usercheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.r_check));
        ownercheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.r_unchek));

        usercheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (usercheckbox.isChecked()){

                    usercheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.r_check));
                    ownercheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.r_unchek));
                    userlayout.setVisibility(View.VISIBLE);
                    shopownerlayout.setVisibility(View.GONE);
                    shop_owner_register.setVisibility(View.GONE);
                    shopownerlayout.animate().alpha(0.0f).setDuration(2000);
                    userlayout.animate().alpha(1.0f);
                }
            }
        });

        ownercheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ownercheckbox.isChecked()){
                    usercheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.r_unchek));
                    ownercheckbox.setButtonDrawable(getResources().getDrawable(R.drawable.r_check));
                    userlayout.setVisibility(View.GONE);
                    shopownerlayout.setVisibility(View.VISIBLE);
                    shop_owner_register.setVisibility(View.VISIBLE);
                    userlayout.animate().alpha(0.0f).setDuration(2000);
                    shopownerlayout.animate().alpha(1.0f);
                }
            }
        });


        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        app_version = pInfo.versionName;

        CountryListAdapter adapter = new CountryListAdapter(this,country);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, country);
        countryspin.setAdapter(adapter);

        countryspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                owner_countryDistrict = country[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void init(){

        countryspin = (Spinner) findViewById(R.id.countryspin);
        term_condition_txt = (TextView)findViewById(R.id.term_condition_txt);

        shop_owner_register = (Button)findViewById(R.id.shop_owner_btn);
        user_register = (Button)findViewById(R.id.user_signup);

        user_name_editText = (EditText)findViewById(R.id.user_name_editText);
        password_edittext = (EditText)findViewById(R.id.password_editText);
        company_edittxt = (EditText)findViewById(R.id.company_edittxt);
        address_edtxt = (EditText)findViewById(R.id.address_edtxt);
        zipcod_editText = (EditText)findViewById(R.id.zipcod_editText);
        city_editText = (EditText)findViewById(R.id.city_editText);
      //  companyDistrict_edittxt = (EditText)findViewById(R.id.companyDistrict_edittxt);
        orgno_edittxt = (EditText)findViewById(R.id.orgno_edittxt);
        email_edittxt = (EditText)findViewById(R.id.email_edittxt);
        phon_edittxt = (EditText)findViewById(R.id.phon_edittxt);

        //user edit text
        fname = (EditText)findViewById(R.id.fname);
        lname = (EditText)findViewById(R.id.lname);
        phone = (EditText)findViewById(R.id.phone);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        usercheckbox = (CheckBox)findViewById(R.id.checkbox1);
        ownercheckbox = (CheckBox)findViewById(R.id.checkbox2);

        userlayout = (LinearLayout)findViewById(R.id.userlayout);
        shopownerlayout = (LinearLayout)findViewById(R.id.shopownerlayout);
        ll_back = (LinearLayout)findViewById(R.id.ll_back);

        term_condition_txt.setText(Html.fromHtml("<font color='#ffffff'>I Accept </font><font color='#05B3F1'>Terms & Conditions</font><font color='#ffffff'> and </font><font color='#05B3F1'>Privacy Policy</font><font color='#ffffff'> of Find Deals </font>"));

        user_name_editText.setTypeface(fontStyle.MyriadPro_Regular());
        password_edittext.setTypeface(fontStyle.MyriadPro_Regular());
        company_edittxt.setTypeface(fontStyle.MyriadPro_Regular());
        address_edtxt.setTypeface(fontStyle.MyriadPro_Regular());
        zipcod_editText.setTypeface(fontStyle.MyriadPro_Regular());
        city_editText.setTypeface(fontStyle.MyriadPro_Regular());
       // companyDistrict_edittxt.setTypeface(fontStyle.MyriadPro_Regular());
        orgno_edittxt.setTypeface(fontStyle.MyriadPro_Regular());
        email_edittxt.setTypeface(fontStyle.MyriadPro_Regular());
        phon_edittxt.setTypeface(fontStyle.MyriadPro_Regular());

        ll_back.setOnClickListener(this);
        shop_owner_register.setOnClickListener(this);
        user_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;

            case R.id.user_signup:

                user_fname = fname.getText().toString();
                user_lname = lname.getText().toString();
                user_phone = phone.getText().toString();
                user_email = email.getText().toString();
                user_password = password.getText().toString();

                if (user_fname.equals("") || user_fname.equals(null) || user_lname.equals("") || user_lname.equals(null) || user_phone.equals("") || user_phone.equals(null)
                || user_email.equals("") || user_email.equals(null) || user_password.equals("") || user_password.equals(null))
                    UservaliDate();
                else
                    SaveUserData();
                break;

            case R.id.shop_owner_btn:

                owner_uname = user_name_editText.getText().toString();
                owner_pass = password_edittext.getText().toString();
                owner_comp = company_edittxt.getText().toString();
                own_add = address_edtxt.getText().toString();
                owner_zipcode = zipcod_editText.getText().toString();
                owner_city = city_editText.getText().toString();
               // owner_countryDistrict = companyDistrict_edittxt.getText().toString();
                owner_orgno = orgno_edittxt.getText().toString();
                owner_email = email_edittxt.getText().toString();
                owner_phon = phon_edittxt.getText().toString();

                if (owner_uname.equals("") || owner_uname.equals(null) || owner_pass.equals("") || owner_pass.equals(null) || owner_comp.equals("") || owner_comp.equals(null)
                        || own_add.equals("") || own_add.equals(null) || owner_zipcode.equals("") || owner_zipcode.equals(null)||
                        owner_city.equals("") || owner_city.equals(null) || owner_countryDistrict.equals("") || owner_countryDistrict.equals(null) || owner_orgno.equals("") || owner_orgno.equals(null)
                        || owner_email.equals("") || owner_email.equals(null) || owner_phon.equals("") || owner_phon.equals(null))
                    ShopOwnervaliDate();
                else
                    SaveShopOwnerData();
                break;
        }
    }

    public void UservaliDate(){
        if (user_fname == null || user_fname.equals("")) {
            dialogBox.DialogBox("Enter: First Name");
        }
        else if (user_lname == null || user_lname.equals("")) {
            dialogBox.DialogBox("Enter: Last Name");
        }
        else if (user_phone == null || user_phone.equals("")) {
            dialogBox.DialogBox("Enter: Phone no");
        }
        else if (user_email == null || user_email.equals("")) {
            dialogBox.DialogBox("Enter: Email");
        }
        else if (user_password == null || user_password.equals("")) {
            dialogBox.DialogBox("Enter: Password");
        }
    }

    public void ShopOwnervaliDate(){
        if (owner_uname == null || owner_uname.equals("")) {
            dialogBox.DialogBox("Enter: User Name");
        }
        else if (owner_pass == null || owner_pass.equals("")) {
            dialogBox.DialogBox("Enter: Password");
        }
        else if (owner_comp == null || owner_comp.equals("")) {
            dialogBox.DialogBox("Enter: Company Name");
        }
        else if (own_add == null || own_add.equals("")) {
            dialogBox.DialogBox("Enter: Address");
        }
        else if (owner_zipcode == null || owner_zipcode.equals("")) {
            dialogBox.DialogBox("Enter: Zipcode");
        }
        else if (owner_city == null || owner_city.equals("")) {
            dialogBox.DialogBox("Enter: City");
        }
        else if (owner_countryDistrict == null || owner_countryDistrict.equals("")) {
            dialogBox.DialogBox("Enter: Country District");
        }
        else if (owner_orgno == null || owner_orgno.equals("")) {
            dialogBox.DialogBox("Enter: ORG Number");
        }
        else if (owner_email == null || owner_email.equals("")) {
            dialogBox.DialogBox("Enter: Email");
        }
        else if (owner_phon == null || owner_phon.equals("")) {
            dialogBox.DialogBox("Enter: Phone");
        }
    }

    public void SaveUserData(){

        class SaveFormData extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(SignUp.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                String result="";
                //RequestHandler ruc = new RequestHandler();

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("first_name", user_fname);
                data.put("last_name",user_lname);
                data.put("phone", user_phone);
                data.put("email", user_email);
                data.put("password", user_password);
                data.put("deviceType", "2");
                data.put("deviceId", device_id);
                data.put("appVersion", app_version);
                data.put("FbId", facebook_id);
                data.put("GlId", gplus_id);
              //  data.put("type", "1");

                result = ruc.sendPostRequest(AppConstants.BASEURL + "registration", data);

                Log.e("register data ","teset"+result);
                Log.e("register data ","check"+data);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s !=null) {

                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        if (jsonObject.getString("status").equals("3"))
                        {
                            mProgressDialog.dismiss();
                           // jsonObject.getString("msg");
                            Toast.makeText(SignUp.this,jsonObject.getString("msg").toString(),Toast.LENGTH_SHORT).show();
                            //finish();
                        }
                        else if (jsonObject.getString("status").equals("1")){
                            Intent intent = new Intent(SignUp.this,MainActivity.class);
                            startActivity(intent);
                            mProgressDialog.dismiss();
                        }else {
                            Toast.makeText(SignUp.this,"Try again",Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        new SaveFormData().execute();
    }

    public void SaveShopOwnerData(){

        class SaveFormData extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(SignUp.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {

                String result="";
                //RequestHandler ruc = new RequestHandler();

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("username", owner_uname);
                data.put("password",owner_pass);
                data.put("companyName", owner_comp);
                data.put("address", own_add);
                data.put("zipCode", owner_zipcode);
                data.put("city", owner_city);
                data.put("country", owner_countryDistrict);
                data.put("orgNumber", owner_orgno);
                data.put("email", owner_email);
                data.put("phone", owner_phon);
                data.put("FbId", facebook_id);
                data.put("GlId", gplus_id);
               // data.put("type", "2");
                data.put("deviceType", "2");
                data.put("deviceId", device_id);
                data.put("appVersion", app_version);
                data.put("profileImage", "");

                result = ruc.sendPostRequest(AppConstants.BASEURL + "registration", data);

                Log.e("register data ","teset"+result);
                Log.e("register data ","check"+data);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s !=null) {

                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        if (jsonObject.getString("status").equals("3"))
                        {
                            mProgressDialog.dismiss();
                            // jsonObject.getString("msg");
                            Toast.makeText(SignUp.this,jsonObject.getString("msg").toString(),Toast.LENGTH_SHORT).show();
                            //finish();
                        }
                        else if(jsonObject.getString("status").equals("0")){
                            Toast.makeText(SignUp.this,jsonObject.getString("msg").toString(),Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }else {
                            Toast.makeText(SignUp.this,"Try again",Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        new SaveFormData().execute();
    }
}
