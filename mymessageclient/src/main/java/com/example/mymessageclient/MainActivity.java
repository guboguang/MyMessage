package com.example.mymessageclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bindService;
    private Button startMethod;
    private Button ingerfacebindService;
    private Button ingerfaceMethod;
    private Messenger messenger;
    ServiceConnection cconn = new ServiceConnection() {



        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        event();
    }

    private void event() {
        ingerfacebindService.setOnClickListener(this);
    }

    private void findView() {
        bindService = (Button) findViewById(R.id.bindService);
        startMethod = (Button) findViewById(R.id.startMethod);
        ingerfacebindService = (Button) findViewById(R.id.ingerfacebindService);
        ingerfaceMethod = (Button) findViewById(R.id.ingerfaceMethod);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ingerfacebindService:

                Intent intent = new Intent();

                bindService(intent, cconn, BIND_AUTO_CREATE);
                break;

            case R.id.ingerfaceMethod:
                if(messenger!=null){
                    Message msg = new Message();
                    msg.arg1= 1;
                    msg.obj = "很简单";
                    try {
                        messenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(cconn);
        super.onDestroy();
    }
}
