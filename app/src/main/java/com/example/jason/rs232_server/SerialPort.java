package com.example.jason.rs232_server;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {
    private String TAG = "SerialPort";
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;
    public msgcallback msgcallback;

    public SerialPort(Context context, File device, int baudrate, int flags) throws SecurityException, IOException {
        Log.i(TAG, "device.getAbsolutePath() : " + device.getAbsolutePath());

        msgcallback = (msgcallback) context;

        mFd = open(device.getAbsolutePath(), baudrate, flags);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int size;

                    try {
                        byte[] buffer = new byte[64];
                        if (mFileInputStream == null) return;
                        size = mFileInputStream.read(buffer);
                        if(size != 0){
                            Log.i(TAG,"size = " + size);
                            String received_messages = new String(buffer,0,size);
                            Log.i(TAG,"We got Messages : " + received_messages);
                            msgcallback.ReceiveMsgs(received_messages);
                        }
                        //onDataReceived(buffer, size);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }).start();


    }

    public int Send(final byte[] buffer, final int size)
    {
        Log.i(TAG,"Send size = " + size);
        try {
            if (mFileOutputStream != null) {
                mFileOutputStream.write(buffer);
            } else {
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 1;
    }



    // Getters and setters
    public InputStream getInputStream() {
        return mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }



    private native static FileDescriptor open(String path, int baudrate, int flags);
    public native void close();
    static {
        System.loadLibrary("rs232");
    }

}

