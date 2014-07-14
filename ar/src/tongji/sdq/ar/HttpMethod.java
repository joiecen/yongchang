/***Designed by ShiDanqing.
 ***Tongji University***/
package tongji.sdq.ar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Message;

public class HttpMethod {
    private static String textstring;

    public static Message postHttpProcess(String httpUrl, HashMap<String, String> postMap) {
        // TODO Auto-generated method stub
        HttpPost httpRequest = new HttpPost(httpUrl);
        List<NameValuePair> postList = new ArrayList<NameValuePair>();

        Iterator iter = postMap.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String val = postMap.get(key);
            postList.add(new BasicNameValuePair(key, val));
        }

        HttpEntity httpentity = null;
        try {
            httpentity = new UrlEncodedFormEntity(postList, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpRequest.setEntity(httpentity);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpRequest);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            StringBuilder builder = null;
            try {
                builder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent()));
                for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
                        .readLine()) {
                    builder.append(s);
                }
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            textstring = builder.toString();
        } else {
            textstring = "wrong";
        }

        Message msg = new Message();
        Bundle sendBundle = new Bundle();
        sendBundle.putString("receiveString", textstring);
        msg.setData(sendBundle);

        return msg;
    }
}
