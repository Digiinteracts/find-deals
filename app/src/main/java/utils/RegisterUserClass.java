package utils;



import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Sonu Saini
 */
public class RegisterUserClass {
    private final int TIMEOUT_CONNECTION = 13000;
    static public boolean isUnknownHostException = false;
    int serverResponseCode;
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {

        Log.e("Request_ravi",postDataParams.toString());
        Log.e("Request_URL",requestURL.toString());
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Log.e("Request_URL1",requestURL.toString());
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            Log.e("Request_URL2",requestURL.toString());
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = br.readLine();
                Log.e("Request_URL3",requestURL.toString());
            }
            else {
                response="Error Registering";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERRRROR",e.getMessage());
        }
        Log.e("RESPONSE",response);

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

 /*   public JSONObject makeHttpRequest(String url, String method, String obj){
        JSONArray jArray = null;
        JSONObject jObj = null;
        HttpURLConnection conn = null;
        String charSet = "UTF-8";
        StringBuilder result = null;
        URL urlObj;
        int httpTimeOut = 90 * 1000;

        try {
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Charset", charSet);
            conn.setRequestProperty("Accept-Language", "UTF-8");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setConnectTimeout(httpTimeOut);

            if (method.equals("POST")) {
                conn.setDoOutput(true);
                conn.setReadTimeout(8 * 1000 * 60);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("data", obj);

                OutputStream outputStreamWriter = conn.getOutputStream();
                outputStreamWriter.write(jsonObject.toString().getBytes());
            }
            conn.connect();

            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Log.e("long lat", "REEEESS 1");
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            if (result!=null) {

                Log.e("long lat", "REEEESS 2");
                //  jArray = new JSONArray(result.toString());
                JSONParser parser = new JSONParser();
                String retVal = parser.parse(result.toString()).toString();

                Object json = new JSONTokener(retVal).nextValue();
                if (json instanceof JSONObject)
                    jObj = new JSONObject(retVal);
                else {
                    jObj = new JSONObject();
                    jObj.put("data", new JSONArray(retVal));
                }
            }
        } catch (java.net.ConnectException e) {
            Log.e("kZingwa", "Failed to Connect with service.");
        } catch (SocketTimeoutException e) {
            Log.e("kZingwa", "Socket Timeout Exception. Unable to Connect with Service."+ e.getMessage());
        } catch (JSONException e) {
            Log.e("error","" +e);
        } catch (IOException e) {
            Log.e("error", "" + e);
        } catch (Exception e) {
            Log.e("error", "" + e);
        } finally {
            if (conn != null) conn.disconnect();
        }
        return jObj;
    }*/
}
