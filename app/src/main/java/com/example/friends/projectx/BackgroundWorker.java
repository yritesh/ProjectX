package com.example.friends.projectx;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Friends on 21-02-2017.
 */

public class BackgroundWorker extends AsyncTask<String,String,String> {
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker (Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[2];
        String username = params[0];
        String password = params[1];
        String login_url = "http://192.168.0.107/login.php";
        if(type.equals("login")){

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Failed!");
    }

    @Override
    protected void onPostExecute(String result) {

        JSONObject mainObject = null;
        try {
            mainObject = new JSONObject(result);
            String exactStatus = mainObject.getString("status");
            if(exactStatus.contentEquals("Successful")) {
                Intent i = new Intent(context, afterLoginActivity.class);
                context.startActivity(i);
            }else {
                alertDialog.setMessage("Wrong Login Credentials");
                alertDialog.show();

            }
            Intent intent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
            Notification noti = new Notification.Builder(context)
                    .setTicker("Ticker Title")
                    .setContentTitle("Notification test")
                    .setContentText("Login status: "+ exactStatus)
                    .setSmallIcon(R.drawable.noti)
                    .setLargeIcon(((BitmapDrawable)context.getResources().getDrawable(R.drawable.noti)).getBitmap())
                    .setContentIntent(pendingIntent).getNotification();

            noti.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0,noti);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
