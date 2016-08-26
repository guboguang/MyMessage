package com.example.hasee.mymessage;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

/**
 * Created by hasee on 2016/8/24.
 */
public class MyService extends Service {

    private Messenger messenger;
    private Handler handler;

    public MyService() {
    }

    @Override
    public void onCreate() {

        //1.创建个一Handler对象，重写handlerMessage方法。
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.arg1==1){
                   String str = (String) msg.obj;
                    Log.e("print","======创建个一Handler对象，重写handlerMessage方法======="+str);
                }

            }


        };

        //创建一个Messager对象传入handler对象。
         messenger = new Messenger(handler);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.arg1 = 111;
                handler.sendMessage(msg);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //通过messager.getBinder()方法返回一个IBinder对象给客户端。
        return messenger.getBinder();
    }

    private class MyBinder extends Binder {

    }
}