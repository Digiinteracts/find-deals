package utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

public final class WebRequest extends AsyncTask<String, String, JSONObject> {
    String requestApi;
    Context mContext;
    ConnectionDetector status;
    HttpURLConnection connection;

    ProgressDialog prog;
    WebApiResult webServiceResult;
    Hashtable<String, String> postParameters;
    //Global global;
    boolean showProcessing = true;
    /**
     * Initizalize web address calling object
     *
     * @param mContext
     * @param url
     * @param webResultInterface
     */

    public WebRequest(Context mContext, String url,
                      WebApiResult webResultInterface,
                      boolean showProgress) {
        this.showProcessing = showProgress;
        this.mContext = mContext;
        this.requestApi = url;
        this.webServiceResult = webResultInterface;
        status = new ConnectionDetector(mContext);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

            if (!showProcessing) return;
            /*prog = new ProgressDialog(mContext);
            prog.setMessage("Loading..." + "\nPlease wait");
            prog.setIndeterminate(false);
            prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prog.setCancelable(false);
            prog.show();*/
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            URL url = new URL(this.requestApi);
             Log.e("Url:", requestApi);
           /* Log.e("Request:", getPostParamString(postParameters));*/
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setConnectTimeout(15000); //connection.setConnectTimeout(30000);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            try {
                    DataOutputStream outputStream = new DataOutputStream(
                            connection.getOutputStream());
                    outputStream.flush();
                    outputStream.close();
            } catch (ConnectException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {


                e.printStackTrace();
                return null;
            } finally {

            }
            // Get Response
            InputStream inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            bufferedReader.close();
            return new JSONObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
     /*   if (Home.progressBarHome != null)
            Home.progressBarHome.setVisibility(View.GONE);*/
       /* if (prog != null)
            if (prog.isShowing()) {
                prog.dismiss();
                prog = null;
            }*/
        try {
            webServiceResult.getWebResult(result);

        }
        catch (Exception e) {
            Log.e("error ","error "+e.getMessage());

        }
        if (!showProcessing)
            return;


    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    /*    if (Home.progressBarHome != null)
            Home.progressBarHome.setVisibility(View.GONE)*/
        ;
        if (prog != null)
            if (prog.isShowing()) {
                prog.dismiss();
                prog = null;
            }
    }
}
