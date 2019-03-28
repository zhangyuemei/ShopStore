package com.hswt.shopstore.shopstore.aboutBlueTooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zhangyuemei on 2019/2/25.
 * Description:
 */
public class SendMessageAsyncTask extends  AsyncTask<String,String,String> {

    private BluetoothSocket mSocket;
    private OutputStream outputStream;


    public SendMessageAsyncTask(BluetoothSocket mSocket, OutputStream outputStream) {

        this.mSocket=mSocket;
        this.outputStream=outputStream;
    }

    @Override
    protected String doInBackground(String... strings) {
        if(mSocket==null)
        {
            return "还没有创建连接";
        }

        if(strings[0].length()>0)//不是空白串
        {
            //String target=arg0[0];

            byte[] msgBuffer = strings[0].getBytes();

            try {
                //  将msgBuffer中的数据写到outStream对象中
                outputStream.write(msgBuffer);

            } catch (IOException e) {
                Log.e("error", "ON RESUME: Exception during write.", e);
                return "发送失败";
            }

        }

        return "发送成功";
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }


}
