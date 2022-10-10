package mao.android_application_broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver
{

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(TAG, "onReceive: ");
        if (intent==null)
        {
            return;
        }
        if (!intent.getAction().equals("mao.android_application_broadcast.receiver.MyReceiver"))
        {
            return;
        }
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }
}