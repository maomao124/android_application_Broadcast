package mao.android_application_broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{

    private TextView textView;
    private MyReceiver receiver;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.TextView);

        checkBox = findViewById(R.id.CheckBox);

        findViewById(R.id.Button1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent("mao.android_application_broadcast.b");
                sendBroadcast(intent);
            }
        });

        findViewById(R.id.Button2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent("mao.android_application_broadcast.b2");
                sendOrderedBroadcast(intent, null);
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


    private class OrderAReceiver extends BroadcastReceiver
    {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent == null)
            {
                return;
            }
            if (!intent.getAction().equals("mao.android_application_broadcast.b2"))
            {
                return;
            }
            textView.setText(textView.getText() + "\n" + new Date().getTime() + ":接收器A收到广播");
            if (checkBox.isChecked())
            {
                abortBroadcast();
            }
        }
    }

    private class OrderBReceiver extends BroadcastReceiver
    {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent == null)
            {
                return;
            }
            if (!intent.getAction().equals("mao.android_application_broadcast.b2"))
            {
                return;
            }
            textView.setText(textView.getText() + "\n" + new Date().getTime() + ":接收器B收到广播");
        }
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter("mao.android_application_broadcast.b");
        registerReceiver(receiver, filter);

        OrderAReceiver orderAReceiver = new OrderAReceiver();
        OrderBReceiver orderBReceiver = new OrderBReceiver();

        IntentFilter intentFilterA = new IntentFilter("mao.android_application_broadcast.b2");
        intentFilterA.setPriority(2);
        IntentFilter intentFilterB = new IntentFilter("mao.android_application_broadcast.b2");
        intentFilterB.setPriority(1);

        registerReceiver(orderAReceiver, intentFilterA);
        registerReceiver(orderBReceiver, intentFilterB);

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(receiver);
    }
}