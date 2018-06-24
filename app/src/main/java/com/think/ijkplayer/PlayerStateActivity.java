package com.think.ijkplayer;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.think.ijkplayer.common.PlayerManager;

public class PlayerStateActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button btnPlay;
    protected Button btnShow;
    protected TextView tvCurr;
    protected TextView tvTotal;
    protected PlayerManager player;
    private String TAG = this.getClass().getSimpleName();
    private String tagSTR = "***--->";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_player_state);
        initView();
        initPlayer();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_play) {

        } else if (view.getId() == R.id.btn_show) {

        }
    }

    private void initView() {
        btnPlay = (Button) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(PlayerStateActivity.this);
        btnShow = (Button) findViewById(R.id.btn_show);
        btnShow.setOnClickListener(PlayerStateActivity.this);
        tvCurr = (TextView) findViewById(R.id.tv_curr);
        tvTotal = (TextView) findViewById(R.id.tv_total);
    }

    private void initPlayer() {
        //构造函数的参数:activity
        //构造函数中从布局实例化了IjkVideoView.
        player = new PlayerManager(this);
        String vfile = Environment.getExternalStorageDirectory() + "/v.f40.mp4";
        //错误路径,会回调onError().
//        String vfile = Environment.getExternalStorageDirectory() + "/v.f41.mp4";
        Log.i(TAG, "onClick: " + tagSTR + vfile);
        player.setFullScreenOnly(true);
        player.playInFullScreen(true);
        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);

        player.play(vfile);
        //设置播放状态的监听.
        player.setPlayerStateListener(new PlayerManager.PlayerStateListener() {
            @Override
            public void onComplete() {
                //播放完成后.
                Log.i(TAG, "onComplete: " + tagSTR + "播放完毕了!");
            }

            @Override
            public void onError() {
                //播放错误时候
                Log.i(TAG, "onError: " + tagSTR + "播放出错了!");
            }

            @Override
            public void onLoading() {
                //加载的时候
                Log.i(TAG, "onLoading: " + tagSTR + "播放加载中!");
            }

            @Override
            public void onPlay() {
                //播放中的时候
                Log.i(TAG, "onPlay: " + tagSTR + "开始播放了");
                tvTotal.setText(getTextTime(player.getDuration()));
            }
        });
    }

    private String getTextTime(int timeMillisecond) {
        int duration = timeMillisecond / 1000;
        int timeSecond = duration % 60;
        int timeMinute = (duration - timeSecond) / 60;
        String totalTime = timeMinute + ":" + timeSecond;
        return totalTime;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //消费触摸事件,播放器左侧上下滑动调节亮度,右侧滑动调节声音.
        if (player.gestureDetector.onTouchEvent(event))
            return true;
        return super.onTouchEvent(event);
    }
}
