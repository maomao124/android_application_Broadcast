package mao.android_application_broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{

    private TextView textView;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.TextView);

        findViewById(R.id.Button1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent("mao.android_application_broadcast.b");
                sendBroadcast(intent);
            }
        });
    }


    private class MyReceiver extends BroadcastReceiver
    {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent == null)
            {
                return;
            }
            if (!intent.getAction().equals("mao.android_application_broadcast.b"))
            {
                return;
            }
            textView.setText(textView.getText() + "\n" + new Date().getTime() + ":收到广播");
        }
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter("mao.android_application_broadcast.b");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(receiver);
    }
}