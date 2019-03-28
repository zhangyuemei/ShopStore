package com.hswt.shopstore.shopstore.aboutBlueTooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.hswt.shopstore.shopstore.app.MyApplication;
import com.hswt.shopstore.shopstore.utils.MyToast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by zhangyuemei on 2019/2/25.
 * Description:
 */
public class ConnectAsyncTask extends AsyncTask<String,String,String> {

    private BluetoothSocket mSocket;
    private BluetoothAdapter mBluetoothAdapter;
    private OutputStream outputStream;
    Handler handler;
    ReceiveThread  rThread;

    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");  //UUID代表了和客户端连接的一个标识（128位格式的字符串ID，相当于pin码）

    public ConnectAsyncTask(BluetoothSocket mSocket, BluetoothAdapter mBluetoothAdapter, OutputStream outputStream, Handler handler) {

        this.mSocket=mSocket;
        this.mBluetoothAdapter=mBluetoothAdapter;
        this.outputStream=outputStream;

        this.handler=handler;
    }

    @Override
    protected String doInBackground(String... strings) {

        //根据蓝牙地址创建蓝牙对象
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(strings[0]);

        try {
           // 获取到设备后调用如下方式进行连接
            mSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            mSocket.connect();
        } catch (IOException e) {
            //e.printStackTrace();
            try {
                mSocket.close();
                return "Socket 创建失败";
            } catch (IOException e1) {
                // e1.printStackTrace();
                Log.e("error", "ON RESUME: Unable to close socket during connection failure", e1);
                return "Socket 关闭失败";

            }

        }

        //取消搜索
        mBluetoothAdapter.cancelDiscovery();
        try {
            outputStream = mSocket.getOutputStream();
        } catch (IOException e) {
          //  e.printStackTrace();
            Log.e("error", "ON RESUME: Output stream creation failed.", e);
            return "Socket 流创建失败";
        }

        return "蓝牙连接正常,Socket 创建成功";

    }


    @Override
    protected void onPostExecute(String s) {
        Log.e("ConnectAsyncTask",s);
        MyToast.showToast(MyApplication.getmContext(),s);

        //连接成功则启动监听
        rThread=new ReceiveThread(mSocket, handler);
        rThread.start();
        Log.e("tag","rThread.start()");
       // statusLabel.setText(result);

        super.onPostExecute(s);
    }



    public void cancel() {
        if(rThread!=null){
            try {
                rThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
