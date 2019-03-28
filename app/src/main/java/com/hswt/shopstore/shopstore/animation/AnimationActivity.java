package com.hswt.shopstore.shopstore.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.hswt.shopstore.shopstore.R;

public class AnimationActivity extends AppCompatActivity {
    private ImageView imageView, imageView2;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();
        initTweenAnimView();
    }

    private void initView() {
        imageView = findViewById(R.id.img_animation_a);

        setXmlFrameAnim1();
        // setXmlFrameAnim2();

        Button playButton = findViewById(R.id.bu_play_animation);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationDrawable.start();
            }
        });

        Button stopButton = findViewById(R.id.bu_stop_animation);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationDrawable.stop();
            }
        });

    }

    private void initTweenAnimView() {
        imageView2 = findViewById(R.id.img_tween_anim_a);
        imageView2.setImageResource(R.mipmap.tween_animation_img);
        //透明度
        Button alphaButton = findViewById(R.id.bu_alpha_animation);
        alphaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAlpha();
            }
        });

        //平移
        Button translateButton = findViewById(R.id.bu_transtion_animation);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTranslation();
            }
        });

        //旋转
        Button rotateButton = findViewById(R.id.bu_rotate_animation);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRotate();
            }
        });

        //拉伸
        Button stretchButton = findViewById(R.id.bu_stretch_animation);
        stretchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changesStretch();
            }
        });
    }

    private void changeAlpha() {
        //透明度变化
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);//第一个参数开始的透明度，第二个参数结束的透明度
        alphaAnimation.setDuration(6000);//多长时间完成这个动作
        imageView2.startAnimation(alphaAnimation);
    }

    private void changeTranslation() {
        //平移
        TranslateAnimation animation = new TranslateAnimation(0, imageView2.getMeasuredWidth(), 0, 0);
        //前两个参数是设置x轴的起止位置，后两个参数设置y轴的起止位置
        animation.setDuration(3000);
        imageView2.startAnimation(animation);

    }

    private void changeRotate() {
        //旋转中心是（0,0）
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
        // 第一个参数是旋转中心，第二个是旋转角度，旋转中心（0~100%）
        rotateAnimation.setDuration(3000);
        imageView2.startAnimation(rotateAnimation);

    }

    private void changesStretch() {
        //拉伸
        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1, 0.5f, 1, 0.5f);//默认从（0,0）
        scaleAnimation1.setDuration(3000);
        imageView2.startAnimation(scaleAnimation1);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ScaleAnimation scaleAnimation2 = new ScaleAnimation(1, 2, 1, 2);
                scaleAnimation2.setDuration(3000);
                imageView2.startAnimation(scaleAnimation2);
            }
        }, 3500);


    }

    /**
     * 通过XML添加帧动画方法一
     */
    private void setXmlFrameAnim1() {

        // 把动画资源设置为imageView的背景,也可直接在XML里面设置
        imageView.setBackgroundResource(R.drawable.animation_list);
        animationDrawable = (AnimationDrawable) imageView.getBackground();
    }


    /**
     * 通过XML添加帧动画方法二
     */
    private void setXmlFrameAnim2() {

        // 通过逐帧动画的资源文件获得AnimationDrawable示例
        animationDrawable = (AnimationDrawable) getResources().getDrawable(
                R.drawable.animation_list);
        imageView.setBackground(animationDrawable);
    }





}
