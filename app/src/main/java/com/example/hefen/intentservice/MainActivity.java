package com.example.hefen.intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hefen.intentservice.R;

  public class MainActivity extends AppCompatActivity {
    EditText input;
    TextView output;
    Button btn;
    Button btn_b;
    private ResponseReceiver receiver;
      static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.editTextInput);
        output = findViewById(R.id.textViewOutput);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strInputMsg = input.getText().toString();
                Intent msgIntent = new Intent(MainActivity.this, SimpleIntentService.class);
                msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, strInputMsg);
                startService(msgIntent);
            }
        });
        btn_b = findViewById(R.id.button2);
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        //registerReceiver(receiver, filter);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

      @Override
      protected void onStop() {
            Log.i("mylog", "onStop");
          super.onStop();
      }

      @Override
      protected void onStart() {
          Log.i("mylog", "onStart");
          super.onStart();
      }

      @Override
      protected void onRestart() {
          Log.i("mylog", "onRestart");
          super.onRestart();
      }

      @Override
      protected void onPause() {
          Log.i("mylog", "onPause");
          super.onPause();
      }

      @Override
      protected void onResume() {
        Log.i("mylog", "resume");
          super.onResume();
      }
      @Override
      public void onSaveInstanceState(Bundle savedInstanceState) {
          // Save the user's current game state
          //savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
          //savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);
          Log.i("mylog", "onsaveinstancestate");

          // Always call the superclass so it can save the view hierarchy state
          super.onSaveInstanceState(savedInstanceState);
      }
      public void onRestoreInstanceState(Bundle savedInstanceState) {
          Log.i("mylog", "onrestoreinstancestate");
          // Always call the superclass so it can restore the view hierarchy
          super.onRestoreInstanceState(savedInstanceState);

          // Restore state members from saved instance
          //mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
          //mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
      }
      public class ResponseReceiver extends BroadcastReceiver {

          public static final String ACTION_RESP =
                  "com.mamlambo.intent.action.MESSAGE_PROCESSED";

          @Override
          public void onReceive(Context context, Intent intent) {
              //TextView result = (TextView) findViewById(R.id.txt_result);
              Log.i("mylog", "receive");
              String text = intent.getStringExtra(SimpleIntentService.PARAM_OUT_MSG);
            if(Looper.myLooper() == Looper.getMainLooper()) {
                Log.i("mylog", "mainthread");
            } else {
                Log.i("mylog", "other thread");
            }

              output.setText(String.valueOf(count));
              count += 1;
          }
      }
}
