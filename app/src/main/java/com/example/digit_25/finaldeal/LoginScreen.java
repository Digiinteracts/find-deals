package com.example.digit_25.finaldeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import utils.AppConstants;
import utils.DialogBox;
import utils.FontStyle;
import utils.RegisterUserClass;
import utils.SharePref;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView findtxt, dealstxt, forgottxt, ortxt;
    Button guest, login_btn, fblogin, gplogin, signup;
    EditText user_name_editText, password_edittext;
    FontStyle fontStyle;
    CallbackManager callbackManager;
    boolean mSignInClicked;
    boolean mIntentInProgress;
    ConnectionResult mConnectionResult;
    String Email, password, device_id, app_version,email2,first_name,last_name,id;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    public LoginButton loginButton;
    PackageInfo pInfo = null;
    DialogBox dialogBox;
    SharePref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_screen);

        sharePref = new SharePref(this);
        fontStyle = new FontStyle(this);
        callbackManager = CallbackManager.Factory.create();
        dialogBox = new DialogBox(LoginScreen.this);

        init();

        signup.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        guest.setOnClickListener(this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.assan", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:is = ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:is = ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        device_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        app_version = pInfo.versionName;
    }

    public void init() {
        loginButton = (LoginButton) findViewById(R.id.login_button2);

        findtxt = (TextView) findViewById(R.id.findtxt);
        dealstxt = (TextView) findViewById(R.id.dealstxt);
        forgottxt = (TextView) findViewById(R.id.forgottxt);
        ortxt = (TextView) findViewById(R.id.ortxt);
        guest = (Button) findViewById(R.id.guest);
        fblogin = (Button) findViewById(R.id.fblogin);
        gplogin = (Button) findViewById(R.id.gplogin);
        signup = (Button) findViewById(R.id.signup);
        login_btn = (Button) findViewById(R.id.login_btn);
        user_name_editText = (EditText) findViewById(R.id.user_name_editText);
        password_edittext = (EditText) findViewById(R.id.password_editText);

        findtxt.setTypeface(fontStyle.MyriadPro_Bold());
        findtxt.setText(Html.fromHtml("<font color='#ff0000'>F</font><font color='#ffffff'>IND</font>"));

        dealstxt.setTypeface(fontStyle.MyriadPro_Regular());
        dealstxt.setText(Html.fromHtml("<font color='#3399ff'> D</font><font color='#ffffff'>EALS</font>"));

        user_name_editText.setTypeface(fontStyle.MyriadPro_Regular());
        password_edittext.setTypeface(fontStyle.MyriadPro_Regular());
        login_btn.setTypeface(fontStyle.MyriadPro_Regular());
        guest.setTypeface(fontStyle.MyriadPro_Regular());
        forgottxt.setTypeface(fontStyle.MyriadPro_Regular());
        fblogin.setTypeface(fontStyle.MyriadPro_Regular());
        gplogin.setTypeface(fontStyle.MyriadPro_Regular());
        ortxt.setTypeface(fontStyle.MyriadPro_Regular());
        signup.setTypeface(fontStyle.MyriadPro_Regular());

        loginButton.setOnClickListener(LoginScreen.this);
        fblogin.setOnClickListener(LoginScreen.this);
        gplogin.setOnClickListener(LoginScreen.this);

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(LoginScreen.this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, new Plus.PlusOptions.Builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        facebooklogin();

    }

    public void facebooklogin() {
        loginButton.setReadPermissions("public_profile", "email");
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                JSONObject res = response.getJSONObject();

                                try {
                                    Log.e("FACEBOOK RESPONSE", "S " + res.toString());
                                    email2 = res.getString("email");
                                    first_name = res.getString("first_name");
                                    last_name = res.getString("last_name");
                                    id = res.getString("id");
                                    //https://graph.facebook.com/USER_ID
                                    String profile_image = "https://graph.facebook.com/" + res.getString("id") + "/picture?type=large";
                                    Log.e("Name", first_name + " " + last_name);
                                    Log.e("Email", email2);

                                    sharePref.saveUserId(res.getString("id"));
                                   /* RequestHandler ruc = new RequestHandler();
                                    String url_sring = ruc.sendGetRequest(profile_image);
                                    Log.e("url_sring", profile_image);
                                    sharepref.saveUserId(LoginActivity.this,id);
                                    sharepref.saveUserImg(LoginActivity.this,profile_image);

                                    appSession.setUserId(id);
                                    appSession.setUserEmail(email2);
                                    appSession.setUserImage(profile_image);
                                    appSession.setUserName(first_name+" " +last_name);

                                    SocialLogin(id);*/
                                    SocialLogin(id,"fb");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // Application code
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,first_name,last_name,locale");
                request.setParameters(parameters);
                request.executeAsync();

                // username.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.email_1), null, null, null);
            }

            @Override
            public void onCancel() {
                // App code

                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                String Exp = exception.toString();
                // App code
                Toast.makeText(getApplicationContext(), Exp, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SocialLogin(String socialid, final String fb_gp) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(LoginScreen.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("id", params[0]);

                if (fb_gp.equals("fb"))
                    data.put("type", "f");
                else
                   data.put("type", "g");

                String result = ruc.sendPostRequest(AppConstants.BASEURL + "socialuser", data);
                Log.v("lOGINREQUEST", result);
                Log.v("Request_Anil", data.toString());
                Log.e("gplus_id Post", id);

                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    Log.e("Social login data Post", result);
                    mProgressDialog.dismiss();
                    try {

                        Object object = new JSONTokener(result).nextValue();
                        if (object instanceof JSONObject) {
                            JSONObject jsonObj = new JSONObject(result);
                            String status = jsonObj.getString("status");
                            if (status.equals("0")) {
                                Intent intent = new Intent(LoginScreen.this, SignUp.class);
                                intent.putExtra("first_name", first_name);
                                intent.putExtra("last_name", last_name);
                                intent.putExtra("email", email2);
                               // intent.putExtra("image", profile_image);

                                if (fb_gp.equals("fb")) {
                                    intent.putExtra("fb_flag",7);
                                    intent.putExtra("facebook_id", id);
                                }
                                else
                                    intent.putExtra("gplus_id", id);

                                startActivity(intent);
                              //  finish();
                                // appSession.setUserloginStatus(status);
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "User successfully registred", Toast.LENGTH_SHORT).show();
                                //  CommonUtils.showToast(getApplicationContext(), "User successfully registred");
                            } else if (status.equals("1")) {
                                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                                startActivity(intent);
                                //finish();
                                // appSession.setUserloginStatus(status);
                                mProgressDialog.dismiss();
                                // CommonUtils.showToast(getApplicationContext(), "Please set the password");
                            }

                        }

                    } catch (JSONException e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                } else {
                    // CommonUtils.showToast(getApplicationContext(),"Server Exceptions...");
                    Toast.makeText(getApplicationContext(), "Server Exceptions...", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }

                // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }


        }

        RegisterUser ru = new RegisterUser();
        ru.execute(socialid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.signup:
                Intent intent = new Intent(LoginScreen.this, SignUp.class);
                startActivity(intent);
                // finish();
                break;

            case R.id.login_btn:
                Email = user_name_editText.getText().toString();
                password = password_edittext.getText().toString();

                if (Email.equals("") || Email.equals(null) || password.equals("") || password.equals(null))
                    valiDate();
                else
                    Login();
                break;

            case R.id.guest:
                Intent intentguest = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intentguest);
                finish();
                break;

            case R.id.fblogin:
                loginButton.performClick();
                break;

            case R.id.gplogin:
                signInWithGplus();
                break;

        }
    }

    public void valiDate() {
        if (Email == null || Email.equals("")) {
            dialogBox.DialogBox("Enter: Email");
        } else if (password == null || password.equals("")) {
            dialogBox.DialogBox("Enter: Password");
        }
    }

    public void Login() {

        class SaveFormData extends AsyncTask<String, Void, String> {
            ProgressDialog mProgressDialog;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(LoginScreen.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait..");
                // Set progressdialog message

                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                String result = "";
                //RequestHandler ruc = new RequestHandler();

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("email", Email);
                data.put("password", password);
                data.put("deviceType", "2");
                data.put("deviceId", device_id);
                data.put("appVersion", app_version);
                data.put("socialid", "");

                result = ruc.sendPostRequest(AppConstants.BASEURL + "login", data);

                Log.e("register data ", "teset" + result);
                Log.e("register data ", "check" + data);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {

                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        if (jsonObject.has("status"))
                        if (jsonObject.getString("status").equals("1")) {
                            mProgressDialog.dismiss();
                            // jsonObject.getString("msg");
                            Toast.makeText(LoginScreen.this, "User successfully login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginScreen.this, "Try again", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }

                        if (jsonObject.has("result")){
                            JSONObject jobj = jsonObject.getJSONObject("result");

                            String userId = jobj.getString("user_id");
                            String email = jobj.getString("email");
                            String name = jobj.getString("name");
                            String profile_img = jobj.getString("profile_img");
                            String phone_no = jobj.getString("phone_no");

                            sharePref.saveUserId(userId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        new SaveFormData().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.e(String.valueOf(requestCode), String.valueOf(resultCode));
        if (requestCode == 9001) {
            System.out.println("ON ACTIVITY RESULT G+");

            if (resultCode != this.RESULT_OK) {
                mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            System.out.println("ON ACTIVITY RESULT FB");
        }
    }

    public void logout() {
        LoginManager.getInstance().logOut();
    }


    //googleplus
    protected void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }

        System.out.println("ON SIGN IN BTN CLICK");
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }

        System.out.println("ON RESOLVE SIGN IN ERROR");
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
        // Get user's information
        getProfileInformation();

        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
        System.out.println("ON CONNECTED");
    }

    private void getProfileInformation() {
        int PROFILE_PIC_SIZE = 400;
        try {
            // CommonUtils.showToast(getApplicationContext(),getResources().getString(R.string.msg_login_success));
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                first_name = currentPerson.getDisplayName();
                String[] name = first_name.split(" ");
                first_name = name[0];
                last_name = name[1];
                String key = "GlId";
                // personPhotoUrl = currentPerson.getImage().getUrl();
//                String personGooglePlusProfile = currentPerson.getUrl();
                email2 = Plus.AccountApi.getAccountName(mGoogleApiClient);
                id = currentPerson.getId();
                Log.e("gmail_social_name", first_name);

                Log.e("gmail_id", id);
                sharePref.saveUserId(currentPerson.getId());

                 SocialLogin(id,"gp");

                Toast.makeText(this, "login successfully", Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(this, "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        System.out.println("ON SUSPENDED");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                resolveSignInError();
            }
        }
        System.out.println("ON CONNECTION FAILED");
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

        System.out.println("ON START");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        System.out.println("ON STOP");
    }
}
