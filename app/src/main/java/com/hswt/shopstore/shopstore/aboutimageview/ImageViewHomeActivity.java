package com.hswt.shopstore.shopstore.aboutimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.aboutimageview.view.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewHomeActivity extends AppCompatActivity {

    private LargeImageView largeIv;

    @BindView(R.id.rv_imageview_home_a)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_home);

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        largeIv = findViewById(R.id.largeIv);

        try {
            InputStream inputStream = getAssets().open("sign.jpg");
            largeIv.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setData(){
         recyclerView.addItemDecoration(null);
         recyclerView.setItemAnimator(null);

    }


}
