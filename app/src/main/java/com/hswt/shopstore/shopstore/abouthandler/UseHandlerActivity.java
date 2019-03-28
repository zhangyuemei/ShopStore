package com.hswt.shopstore.shopstore.abouthandler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hswt.shopstore.shopstore.R;

/**
 * handler使用
 */
public class UseHandlerActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private int[] imgs = {R.mipmap.funny_img1, R.mipmap.funny_img2, R.mipmap.funny_img3};
    private int index;
    Handler handler = new Handler();
    MyRunnable myRunnable = new MyRunnable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_handler);

        initView();
    }

    private void initView() {
        imageView = findViewById(R.id.iv_use_handler_a);
        textView = findViewById(R.id.tv_use_handler_a);

        handler.postDelayed(myRunnable, 1000);
/*

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("update");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
*/


    }


    class MyRunnable implements Runnable {

        @Override
        public void run() {
            index++;
            index = index % 3;
            imageView.setImageResource(imgs[index]);
            handler.postDelayed(myRunnable, 1000);

        }
    }





}
