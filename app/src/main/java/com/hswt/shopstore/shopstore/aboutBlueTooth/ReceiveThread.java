package com.hswt.shopstore.shopstore.aboutBlueTooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by zhangyuemei on 2019/2/25.
 * Description:
 */
class ReceiveThread extends Thread {
  // private final BluetoothServerSocket mServerSocket;
    //这条是蓝牙串口通用的UUID，不要更改
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");  //UUID代表了和客户端连接的一个标识（128位格式的字符串ID，相当于pin码）

    String ReceiveData;
    Handler handler;
    private BluetoothSocket mSocket;

    public ReceiveThread(BluetoothSocket mSocket, Handler handler) {

/*       BluetoothServerSocket temporary=null;

        try {
            temporary= adapter.listenUsingRfcommWithServiceRecord("name",MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mServerSocket=temporary;*/
        this.mSocket=mSocket;
        this.handler=handler;
    }

    @Override
    public void run() {
        super.run();

        String buffer="";

        InputStream inStream;
/*        BluetoothSocket socket=null;

        try {
            socket=mServerSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        if (mSocket!=null){
            //定义一个存储空间buff
            byte[] buff = new byte[1024];
            try {
                inStream = mSocket.getInputStream();
                Log.e("tag","waitting for instream");
                inStream.read(buff); //读取数据存储在buff数组中
                processBuffer(buff, 1024);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        }



    /** Will cancel the listening socket, and cause the thread to finish */
/*
    public void cancel() {
        try {
            mServerSocket.close();
        } catch (IOException e) {

        }
    }
*/


    private void processBuffer(byte[] buff,int size)
    {
        int length=0;
        for(int i=0;i<size;i++)
        {
            if(buff[i]>'\0')
            {
                length++;
            }
            else
            {
                break;
            }
        }

        byte[] newbuff=new byte[length];  //newbuff字节数组，用于存放真正接收到的数据

        for(int j=0;j<length;j++)
        {
            newbuff[j]=buff[j];
        }

       ReceiveData=ReceiveData+ new String(newbuff);
        Log.e("Data",ReceiveData);

        //传递的数据
        Bundle bundle = new Bundle();
        bundle.putString("msg", ReceiveData);

        Message msg=Message.obtain();
        msg.what=1;
        msg.setData(bundle);   //message.obj=bundle  传值也行
        handler.sendMessage(msg);  //发送消息:系统会自动调用handleMessage( )方法来处理消息*/
    }


}
