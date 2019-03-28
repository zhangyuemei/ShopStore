package com.hswt.shopstore.shopstore.aboutimageview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import android.view.View;
import android.view.ViewConfiguration;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhangyuemei on 2019/2/26.
 * Description:  加载大图
 * 使用BitmapRegionDecoder用法（onmeasure,ondraw,ontouchevent）
 */
@SuppressLint("AppCompatCustomView")
public class LargeImageView extends View implements GestureDetector.OnGestureListener {
    private static final String TAG = "LargeImageView";

    private BitmapRegionDecoder mDecoder;

    //绘制的区域
    private volatile Rect mRect = new Rect();

    private int mScaledTouchSlop;

    //分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    //图片的宽度和高度
    private int mImageWidth, mImageHeight;

    //手势控制器
    private GestureDetector gestureDetector;
    private BitmapFactory.Options options;
    private Context mContext;

    public LargeImageView(Context context) {
        super(context);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        //设置显示图片的参数，如果对图片质量有要求，就选择ARGB_8888模式
        options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //RGB_565 一个像素占2个字节， RGB_8888 一个像素占4个字节
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInputStream(InputStream inputStream) throws IOException{
        //或许图片的宽高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //这样就不会加载到内存
        //把图片加载一下
        BitmapFactory.decodeStream(inputStream, null, options);

        mImageWidth = options.outWidth;
        mImageHeight = options.outHeight;

        mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
        gestureDetector = new GestureDetector(mContext, this);

    }

    //自定义一个矩形区域
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        //默认显示图片的中心区域，开发者可自行选择
        mRect.left = imageWidth / 2 - width / 2;
        mRect.top = imageHeight / 2 - height / 2;
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = mDecoder.decodeRegion(mRect, options);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    /**
     * 移动时更新图片显示区域
     *
     * @param x
     * @param y
     */
    private void move(int x, int y) {
        int deltaX = x - mLastX;
        int deltaY = y - mLastY;

        Log.d(TAG, "move:" + deltaX + " ," + deltaY);
        //若图片宽度大于屏幕宽度
        if (mImageWidth > getWidth()) {
            //移动rect区域
            mRect.offset(-deltaX, 0);
            //检查是否到达屏幕最右端
            if (mRect.right > mImageWidth) {
                mRect.right = mImageWidth;
                mRect.left = mImageWidth - getWidth();
            }

            if (mRect.left < 0) {
                mRect.left = 0;
                mRect.right = getWidth();
            }
            invalidate();
        }

        if (mImageHeight > getHeight()) {
            mRect.offset(0, -deltaY);
            //检查是否到达屏幕最下方
            if (mRect.bottom > mImageHeight) {
                mRect.bottom = mImageHeight;
                mRect.top = mImageHeight - getHeight();
            }
            if (mRect.top<0){
                mRect.top=0;
                mRect.bottom=getHeight();
            }

            invalidate();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把触摸事件交给手势控制器处理
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        //记录手指点下去的坐标
        mLastX = (int) motionEvent.getRawX();
        mLastY = (int) motionEvent.getRawY();

        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        //获取移动后的坐标
        int x = (int) motionEvent1.getRawX();
        int y = (int) motionEvent1.getRawY();

        move(x, y);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
