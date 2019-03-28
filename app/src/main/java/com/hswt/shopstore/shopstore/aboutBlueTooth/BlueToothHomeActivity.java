package com.hswt.shopstore.shopstore.aboutBlueTooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hswt.shopstore.shopstore.Interfaces.IContentDialogBack;
import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.app.AppManager;

import com.hswt.shopstore.shopstore.utils.MyToast;
import com.hswt.shopstore.shopstore.utils.MyUtils;
import com.hswt.shopstore.shopstore.utils.dialog.EditContentDialog;
import com.hswt.shopstore.shopstore.utils.dialog.SelectAndSearchDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hswt.shopstore.shopstore.utils.ClsUtils.createBond;

/**
 * 蓝牙设置相关
 */
public class BlueToothHomeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_open_blue_tooth_h)
    Button btnOpenBlueTooth;
    @BindView(R.id.btn_close_blue_tooth_h)
    Button btnCloseBlueTooth;

    @BindView(R.id.btn_find_device_blue_tooth_h)
    Button btnfindDevice;
    @BindView(R.id.text_choosed_device_blue_tooth_h)
    TextView tvChoosedDevice;
    @BindView(R.id.btn_be_found_blue_tooth_h)
    Button btnBeFoundBlueTooth;
    @BindView(R.id.text_gain_device_blue_tooth_h)
    TextView tvGainBondDevice;

    BluetoothDevice gainBondDevic=null;

    @BindView(R.id.btn_want_bond_blue_tooth_h)
    Button btnWantBondBlueTooth;
    @BindView(R.id.edit_blue_tooth_h)
    EditText editText;
    @BindView(R.id.tv_received_message_blue_tooth_h)
    TextView tvReceivedMessage;

    BluetoothAdapter mBluetoothAdapter;

    private static final int REQUEST_OPEN_BLUE_TOOTH = 0000;

    private final int PERMISSION_REQUEST_COARSE_LOCATION = 0xb01;

    MineBroadcastReceiver mineBroadcastReceiver;

    SelectAndSearchDialog selectAndSearchDialog;

    String aimDeviceAddress = null;

    List<BluetoothDevice> myDeviceList= new ArrayList<>();

    //套接字
    private BluetoothSocket btSocket = null;

    //输出流
    private OutputStream outStream = null;
    //输入流
    private InputStream inStream = null;
    MyHandler hander;
    ConnectAsyncTask connectAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth_home);
        AppManager.getAppManager().addActivity(this);

        initToolBar();
        init();
    }

    public void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //隐藏系统默认的Title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("新建策划");
        // toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //显示左侧返回小箭头
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getAppManager().finishActivity();
            }
        });

        //设置透明状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            toolbar.setPadding(0, MyUtils.getStatusBarHeight(this), 0, 0);    //给Toolbar设置paddingTop
        }

/*        sureIv.setImageResource(R.mipmap.sure);
        sureIv.setVisibility(View.VISIBLE);*/
    }



    private void init() {
        ButterKnife.bind(this);

        //解决6.0以上扫描不到蓝牙设备的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }


        //获取本地蓝牙设备
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //判断蓝牙功能是否存在
        if (mBluetoothAdapter == null) {
            MyToast.showToast(this, "该设备不支持蓝牙");
            return;
        }

        //获取名字，Mac
        String Name = mBluetoothAdapter.getName();
        String address = mBluetoothAdapter.getAddress();
        Log.e("BlueToothHomeActivity", "名称：" + Name + "  Mac地址：" + address);

        //获取蓝牙状态
        switch (mBluetoothAdapter.getState()) {
            case BluetoothAdapter.STATE_ON:
                MyToast.showToast(BlueToothHomeActivity.this, "蓝牙打开状态");
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                MyToast.showToast(BlueToothHomeActivity.this, "蓝牙正在打开");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                MyToast.showToast(BlueToothHomeActivity.this, "蓝牙正在关闭");
                break;
            case BluetoothAdapter.STATE_OFF:
                MyToast.showToast(BlueToothHomeActivity.this, "蓝牙关闭状态");
                break;

        }

    }




    //搜索设备
    private void ToFindDevice() {

        mineBroadcastReceiver = new MineBroadcastReceiver();
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mineBroadcastReceiver, filter); // Don't forget to unregister during onDestroy

        IntentFilter filterStart = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(mineBroadcastReceiver, filterStart);

        IntentFilter filterUpdate = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mineBroadcastReceiver, filterUpdate);

        if (mBluetoothAdapter != null) {  //有蓝牙功能
            if (!mBluetoothAdapter.isEnabled()) {  //蓝牙未开启
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mBluetoothAdapter.enable();  //开启蓝牙（有另一种方法开启）
                    }
                }).start();
            } else {
                if (!mBluetoothAdapter.isDiscovering()) {  //如果没有在扫描设备
                    mBluetoothAdapter.startDiscovery();  //扫描附近蓝牙设备
                } else {
                    MyToast.showToast(this, "正在扫描");
                }
            }
        } else {  //无蓝牙功能
            MyToast.showToast(this, "该设备不支持蓝牙");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_OPEN_BLUE_TOOTH == requestCode) {
            if (resultCode == RESULT_CANCELED) {
                MyToast.showToast(this, "请求失败");
            } else {
                MyToast.showToast(this, "请求成功");
            }
        }

    }

    @OnClick({R.id.btn_open_blue_tooth_h, R.id.btn_close_blue_tooth_h, R.id.btn_find_device_blue_tooth_h, R.id.btn_be_found_blue_tooth_h,
            R.id.btn_gain_blue_tooth_h,R.id.btn_want_bond_blue_tooth_h,R.id.btn_send_message_blue_tooth_h})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_blue_tooth_h:

                //判断蓝牙功能当前是否打开状态
                if (mBluetoothAdapter.isEnabled()) {
                    MyToast.showToast(BlueToothHomeActivity.this, "当前设备蓝牙已处于打开状态");
                } else {
                    //打开蓝牙
                    //    boolean isOpen = mBluetoothAdapter.enable();
                    //    MyToast.showToast(BlueToothHomeActivity.this, "当前设备蓝牙状态：" + isOpen);

                    //调用系统API 打开
                    Intent openIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(openIntent, REQUEST_OPEN_BLUE_TOOTH);

                }

                break;

            case R.id.btn_close_blue_tooth_h:
                //关闭蓝牙
                //判断蓝牙功能当前是否打开状态
                if (mBluetoothAdapter.isEnabled()) {
                    boolean isClose = mBluetoothAdapter.disable();
                    if (isClose) {
                        MyToast.showToast(BlueToothHomeActivity.this, "当前设备蓝牙已关闭！");
                    }

                } else {
                    MyToast.showToast(BlueToothHomeActivity.this, "当前设备蓝牙处于关闭状态");

                }

                break;
            case R.id.btn_find_device_blue_tooth_h:
                ToFindDevice();

                break;
            case R.id.btn_be_found_blue_tooth_h:
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
                break;

            case R.id.btn_gain_blue_tooth_h:
                // 获取已配对设备
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        //tvGainBondDevice.setText("");
                        tvGainBondDevice.append("\n" + device.getName() + " " + device.getAddress() + " " + device.getBondState());
                        gainBondDevic=device;
                        if (gainBondDevic!=null){
                            tvGainBondDevice.setOnClickListener(this);
                        }

                    }
                }
                break;
            case R.id.text_gain_device_blue_tooth_h:  //点击已配对的设备
                connectAsyncTask= (ConnectAsyncTask) new ConnectAsyncTask(btSocket,mBluetoothAdapter,outStream,hander).execute(gainBondDevic.getAddress());
                break;
            case R.id.btn_want_bond_blue_tooth_h:  //设置想要配对的设备
                setWantBondDevice();
                break;
            case R.id.btn_send_message_blue_tooth_h:  //发送消息
                new SendMessageAsyncTask(btSocket,outStream).execute(editText.getText().toString());
                break;
        }

    }


    private void setWantBondDevice() {
        final EditContentDialog editContentDialog = new EditContentDialog(this);
        editContentDialog.setOncontentCallBack(new IContentDialogBack() {
            @Override
            public void getPosition(int position) {
                //在这里忽略
            }

            @Override
            public void getContent(String content) {
                //  mPresenter.addHallRequest(content);
                aimDeviceAddress = content;
                editContentDialog.dismiss();
                MyToast.showToast(BlueToothHomeActivity.this, "您已设置目标设备地址，请点击扫描设备按钮进行查找");
            }
        });
        editContentDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                aimDeviceAddress = null;
                editContentDialog.dismiss();
            }
        });
        editContentDialog.show();
    }


    //自定义广播接收器
    private class MineBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                //通过此方法获取搜索到的蓝牙设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // 获取搜索到的蓝牙绑定状态,看看是否是已经绑定过的蓝牙
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    // 如果没有绑定过则将蓝牙名称和地址显示在TextView上
                    tvChoosedDevice.setText("未配对蓝牙：" + device.getName() + ":"
                            + device.getAddress() + "\n");
                }else{
                    tvGainBondDevice.setText("已配对蓝牙：" + device.getName() + ":"
                            + device.getAddress() + "\n");
                }
                //如果设置了指定地址的蓝牙设备，
                if (aimDeviceAddress!=null) {
                    // 指定地址的蓝牙和搜索到的蓝牙相同,则我们停止扫描
                    if (aimDeviceAddress.equals(device.getAddress())) {
                        mBluetoothAdapter.cancelDiscovery();//停止扫描蓝牙

                        //根据蓝牙地址创建蓝牙对象
                        BluetoothDevice btDev = mBluetoothAdapter.getRemoteDevice(device.getAddress());
                        //通过反射来配对对应的蓝牙
                        try {
                            ////通过工具类ClsUtils,调用createBond方法
                            createBond(btDev.getClass(), btDev);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                myDeviceList.add(device);
                Log.e("Tag", "扫描到的：" + device.getName() + "" + device.getAddress());
                Log.e("Tag", "myDeviceList.size: "+ myDeviceList.size());


            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {

                //搜索完成
                MyToast.showToast(BlueToothHomeActivity.this, "扫描结束");

                if (myDeviceList.size()>0){
                    selectDialog(myDeviceList);
                }else{
                    MyToast.showToast(BlueToothHomeActivity.this, "未扫描到设备");
                }

            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                //获取发生改变的蓝牙对象
                BluetoothDevice device1 = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //根据不同的状态显示提示
                switch (device1.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        Log.d("yxs", "正在配对......");
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        Log.d("yxs", "完成配对");
                        Toast.makeText(context, "签到成功", Toast.LENGTH_SHORT).show();
                        //  handler.sendEmptyMessageDelayed(1, 2000);
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        Log.d("yxs", "取消配对");
                    default:
                        break;
                }
            }


        }

    }


    public void selectDialog(final List<BluetoothDevice> myDeviceList) {
        selectAndSearchDialog = new SelectAndSearchDialog(BlueToothHomeActivity.this, myDeviceList);
        selectAndSearchDialog.setSelectDialogBack(new IContentDialogBack() {
            @Override
            public void getPosition(int position) {
                tvChoosedDevice.setText(myDeviceList.get(position).getName());

               connectAsyncTask= (ConnectAsyncTask) new ConnectAsyncTask(btSocket,mBluetoothAdapter,outStream,hander).execute(myDeviceList.get(position).getAddress());
/*

                //根据蓝牙地址创建蓝牙对象
                BluetoothDevice btDev = mBluetoothAdapter.getRemoteDevice(myDeviceList.get(position).getAddress());
                //通过反射来配对对应的蓝牙
                try {
                    ////通过工具类ClsUtils,调用createBond方法
                    createBond(btDev.getClass(), btDev);
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/


            }

            @Override
            public void getContent(String content) {
                if (content != null && !content.equals("")) {
        /*            addProjectPresenter.getHallNameRequest(content);
                    currentStatus = "search";*/
                }

            }
        });
        selectAndSearchDialog.show();
    }



    //更新界面的Handler类
    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch(msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    String data = bundle.getString("msg");
                    tvReceivedMessage.setText(data);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mineBroadcastReceiver);

        connectAsyncTask.cancel();
        if (btSocket!=null){
            try {
                btSocket.close();
                btSocket=null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

 /*       if(rThread!=null)
        {

            btSocket.close();
            btSocket=null;

            rThread.join();
        }

        this.finish();*/

    }


}
