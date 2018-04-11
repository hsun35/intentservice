package com.example.hefen.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

public class SimpleIntentService extends IntentService {
    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";

    private Handler mHandler;

    public SimpleIntentService() {
        super("SimpleIntentService");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        String msg = intent.getStringExtra(PARAM_IN_MSG);

        for(int i = 0;i<2;i++){
            SystemClock.sleep(4000); // 30 seconds
            String resultTxt = msg + " "
                    + DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity.ResponseReceiver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
            //sendBroadcast(broadcastIntent);
            Log.i("mylog", "broadcast");
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            if(Looper.myLooper() == Looper.getMainLooper()) {
                Log.i("mylog", "mainthread");
            } else {
                Log.i("mylog", "other thread");
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "handler" , Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
