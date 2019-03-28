package com.hswt.shopstore.shopstore.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hswt.shopstore.shopstore.Interfaces.IContentDialogBack;
import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.app.MyApplication;
import com.hswt.shopstore.shopstore.utils.MyToast;
import com.hswt.shopstore.shopstore.utils.StringUtil;

/**
 * Created by zhangyuemei on 2018/7/24.
 * Description:
 */

public class EditContentDialog extends Dialog {
    /**
     * 上下文对象 *
     */
    Activity context;

    private TextView btn_save, btn_cancel;

    public EditText textContent;

    private View.OnClickListener mClickListener;

    private IContentDialogBack iContentDialogBack;

    public EditContentDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    public EditContentDialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.edit_content_dialog);

        textContent = (EditText) findViewById(R.id.et_content_refuse_a);

        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        final Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        dialogWindow.setBackgroundDrawableResource(R.drawable.shape_edit_text);
        // 根据id在布局中找到控件对象
        btn_save = (TextView) findViewById(R.id.save_edit_content_dialog);
        btn_cancel = (TextView) findViewById(R.id.cancel_edit_content_dialog);

        // 为按钮绑定点击事件监听器
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textContent.getText().toString().trim().equals("")){
                    MyToast.showToast(context,"请填写需要添加的内容！");
                    return;
                }/*else if(!StringUtil.checkNameChese(textContent.getText().toString().trim())){
                    MyToast.showToast(MyApplication.getmContext(), "只能填写汉字！");
                    return;
                }*/

                iContentDialogBack.getContent(textContent.getText().toString().trim());
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               cancel();
            }
        });

        this.setCancelable(true);
    }

    public void setOncontentCallBack(IContentDialogBack iContentDialogBack){
        this.iContentDialogBack=iContentDialogBack;
    }

}
