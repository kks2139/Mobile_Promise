package com.example.promise.services;

/**
 * Created by Yeohwankyoo on 2016-06-14.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.example.promise.MainActivity;
import com.example.promise.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Priyank(@priyankvex) on 9/9/15.
 * Service that is invoked when message is received by the device.
 */
public class MyGcmListenerService extends GcmListenerService {

    /**
     * This method run executed when message is received.
     * @param from Sender ID of the sender.
     * @param data message that has been received.
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        Log.d("test", data.toString());
        String message = data.getString("message");
        Log.d("test2", message);
        try{
            JSONObject json = new JSONObject(message);

            String year = json.getString("year");
            String month = json.getString("month");
            String date = json.getString("date");
            String hour = json.getString("hour");
            String longti = json.getString("longti");
            String lati = json.getString("lati");
            String room = json.getString("room");
            int count = json.getInt("count");
            //ArrayList<String> memberID = new ArrayList<String>();
            String[] memberID = new String[count];
            //JSONObject json2 = json.getJSONObject("memberID");
            //for(int i= 0; i<count; i++)
            //{
            //}
            /*
            *        jsonObject.accumulate("memberID", userID);
            jsonObject.accumulate("year", t[0]);
            jsonObject.accumulate("month", t[1]);
            jsonObject.accumulate("data", t[2]);
            jsonObject.accumulate("hour", t[3]);
            jsonObject.accumulate("minute", t[4]);
            jsonObject.accumulate("lati", lati);
            jsonObject.accumulate("longti", longti);
            jsonObject.accumulate("room", et.getText().toString());
            jsonObject.accumulate("count", count);
            * */
            sendNotification(year, month, date, message);
           Toast.makeText(getApplicationContext(), "longti: "+ longti + " lati: " + lati + " rom :" +room, Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            Log.d("Error: ", "JSON Parsing Error");
        }
        ;
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String year, String month, String date, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.promise_gcm)
                .setContentTitle("Promise")
                .setContentText("We will go to " + year + month + date)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
