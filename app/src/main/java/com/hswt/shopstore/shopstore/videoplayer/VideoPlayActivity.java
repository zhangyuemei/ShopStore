package com.hswt.shopstore.shopstore.videoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.app.AppManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 视频播放首页
 */
public class VideoPlayActivity extends AppCompatActivity {

    /*  @BindView(R.id.videoview_video_play_a)
      VideoView videoView;
  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        AppManager.getAppManager().addActivity(this);

        init();
    }

    private void init() {
        ButterKnife.bind(this);

      //  videoView.setVideoPath("");
        // videoView.setVideoURI();
        MediaController controller = new MediaController(this);

    }


}
