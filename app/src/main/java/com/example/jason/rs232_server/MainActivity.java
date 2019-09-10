package com.example.jason.rs232_server;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, msgcallback {
    private final static String TAG = "RS232-APP";
    private SerialPort mSerialPort;
    private EditText editText;
    private Button sendBtn;
    private TextView recvMsg;
    private String stringRecvMsg;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"Jason test");
    }

    @Override
    protected void onResume() {

        super.onResume();
        try {
            mSerialPort = null;

            mSerialPort = new SerialPort(this,new File("/dev/ttyS0"), 115200, 0);
        }catch (IOException e){}
        if(mSerialPort != null) {
            Log.i(TAG, "mSerialPort create ok!");
        }else {
            Log.i(TAG, "mSerialPort fail!");
        }
        initView();
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG,"back pressed!");
        /*finish();
        this.onDestroy();*/
        exit(0);



    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onPause() {
        mSerialPort.close();
        super.onPause();
        //releaseView();

        //handler.removeCallbacksAndMessages(null);
    }

    private void initView() {
        editText = (EditText)findViewById(R.id.editText);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this);
        recvMsg = (TextView)findViewById(R.id.TextRecvMsg);
    }

    private void releaseView() {
        editText = null;
        sendBtn = null;
        recvMsg = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendBtn:
                Log.i(TAG, "send Data!");
                sendData(String.valueOf(editText.getText()));
                break;

        }
    }

    private void sendData(String data) {
        Log.i(TAG,"send data : " + data);
        mSerialPort.Send(data.getBytes(),data.getBytes().length);

    }

    @Override
    public void ReceiveMsgs(String msgs)
    {
        Log.i(TAG,"msgs = " + msgs);
        stringRecvMsg = msgs;
        Log.i(TAG,"recvMsg = " + recvMsg.toString());
        Log.i(TAG,"recvMsg = " + recvMsg.toString());



        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            recvMsg.setText(stringRecvMsg);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.i(TAG,"recvMsg = AA" );
                    }
                });
            }
        }).start();


    }
}
