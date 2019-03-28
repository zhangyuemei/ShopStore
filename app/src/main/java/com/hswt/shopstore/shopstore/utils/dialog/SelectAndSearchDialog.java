package com.hswt.shopstore.shopstore.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.hswt.shopstore.shopstore.Interfaces.IContentDialogBack;
import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.adapter.SelectDeviceAdapter;
import com.hswt.shopstore.shopstore.app.AppManager;
import com.hswt.shopstore.shopstore.bean.MyDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择对话框
 */
public class SelectAndSearchDialog extends Dialog {

    private IContentDialogBack iContentDialogBack;

    private Context context;
    private ListView hallListview;
    private EditText searchEt;

    private SelectDeviceAdapter selectDeviceAdapter;
    List<BluetoothDevice> list=new ArrayList<>();

    public SelectAndSearchDialog(@NonNull Activity context, List<BluetoothDevice> list) {
        super(context);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.select_and_search_dialog);

        searchEt = findViewById(R.id.et_content_search_a);
        hallListview = findViewById(R.id.select_hall_lv);

        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */

        final Window dialogWindow = this.getWindow();
        WindowManager m = AppManager.getAppManager().currentActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.55); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        dialogWindow.setBackgroundDrawableResource(R.drawable.shape_edit_text);

        setData();

    }

    public void setData() {
        selectDeviceAdapter = new SelectDeviceAdapter(context, list);
        hallListview.setAdapter(selectDeviceAdapter);
        hallListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                iContentDialogBack.getPosition(i);
                cancel();
                list.clear();
            }
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    iContentDialogBack.getContent(editable.toString());
                    searchEt.setText("");
                }

            }
        });

    }

    public void setRefresh() {
        selectDeviceAdapter.notifyDataSetChanged();
    }


    /**
     * 设置回调接口
     *
     * @param iContentDialogBack 回调接口
     */
    public void setSelectDialogBack(IContentDialogBack iContentDialogBack) {
        this.iContentDialogBack = iContentDialogBack;
    }

}
