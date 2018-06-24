package com.think.ijkplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.think.ijkplayer.common.PlayerManager;

public class PlayerManagerActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView tvCurr;
    protected TextView tvTotal;
    private String url1 = "rtmp://203.207.99.19:1935/live/CCTV5";
    private String url2 = "http://zv.3gv.ifeng.com/live/zhongwen800k.m3u8";
    private String url3 = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    private String url4 = "http://42.96.249.166/live/24035.m3u8";
    private String url5 = "http://1251603248.vod2.myqcloud.com/4c9adaa7vodtransgzp1251603248/30e0819d9031868223192061218/v.f40.mp4";
    private PlayerManager player;
    private String TAG = this.getClass().getName();
    private String tagSTR = "***--->+***";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_player_manager);
        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_restart).setOnClickListener(this);
        initView();

        initPlayer();
        //new PlayerManager.Query(this).id(R.id.btn_restart).text("kaishi");
    }

    private void initPlayer() {
        //构造函数的参数:activity
        //构造函数中从布局实例化了IjkVideoView.
        player = new PlayerManager(this);
        String vfile = Environment.getExternalStorageDirectory() + "/v.f40.mp4";

        Log.i(TAG, "onClick: " + tagSTR + vfile);
        player = player.onControlPanelVisibilityChange(new PlayerManager.OnControlPanelVisibilityChangeListener() {
            @Override
            public void change(boolean isShowing) {
                if (isShowing){
                    Toast.makeText(PlayerManagerActivity.this,"111",Toast.LENGTH_SHORT).show();
                }
            }
        });
        player.play(vfile);
        //视频总时间长度.
        //tvTotal.setText(getTextTime(player.getDuration()));
        //转换时间格式:
        String s = player.generateTime(player.getDuration());
        tvTotal.setText(s);

        //设置播放状态的监听.
//        player.setPlayerStateListener(this);
        //播放视频,参数:网络地址url.
        //player.play(url5);


    }


    private void show() {
        //弹出对话框.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ijkPlayer的配置!");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(new String[]{"0原大小在中心",
                "1等比例放大填满view",
                "2按比例缩放完全显示在view中",
                "3拉伸画面填满整个View",
                "4拉伸画面到16:9",
                "5拉伸画面到4:3",
                "6设置横向全屏",
                "7退出全屏",
                "8设置纵向播放"
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //可能会剪裁,保持原视频的大小，显示在中心,当原视频的大小超过view的大小超过部分裁剪处理
                        player.setScaleType(PlayerManager.SCALETYPE_FITPARENT);
                        break;
                    case 1:
                        //可能会剪裁,等比例放大视频，直到填满View为止,超过View的部分作裁剪处理
                        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
                        break;
                    case 2:
                        //将视频的内容完整居中显示，如果视频大于view,则按比例缩视频直到完全显示在view中
                        player.setScaleType(PlayerManager.SCALETYPE_WRAPCONTENT);
                        break;
                    case 3:
                        //不剪裁,非等比例拉伸画面填满整个View
                        player.setScaleType(PlayerManager.SCALETYPE_FITXY);
                        break;
                    case 4:
                        //不剪裁,非等比例拉伸画面到16:9,并完全显示在View中
                        player.setScaleType(PlayerManager.SCALETYPE_16_9);
                        break;
                    case 5:
                        //不剪裁,非等比例拉伸画面到4:3,并完全显示在View中
                        player.setScaleType(PlayerManager.SCALETYPE_4_3);
                        break;
                    case 6:
                        //设置横向全屏播放
                        //横向播放
//                        player.playInFullScreen(true);
                        //参数:true为全屏,false反之.
                        player.setFullScreenOnly(true);
                        player.playInFullScreen(true);

                        break;
                    case 7:
                        //退出全屏
                        //参数:true为全屏,false反之.
                        player.setFullScreenOnly(false);
                        player.playInFullScreen(false);
                        break;
                    case 8:
                        //设置屏幕方向,参数:true为横向,false纵向.
                        player.playInFullScreen(false);
                        player.playInFullScreen(false);
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show:
                show();
                break;
            case R.id.btn_play:
                play();
                break;
            case R.id.btn_restart:
                String vfile = Environment.getExternalStorageDirectory() + "/v.f40.mp4";
                player.play(vfile);
                break;

        }
    }

    private void play() {
        //弹出对话框.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ijkPlayer的配置!");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(new String[]{
                "0继续播放",
                "1暂停播放",
                "2停止播放",
                "3暂停播放,保留当前进度",
                "4恢复播放,读取进度记录",
                "5获取当前播放位置",
                "6获取视频总时间",
                "7设备支持情况",
                "8横屏非全屏切换竖屏"
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //继续播放
                        //,若没有播放,则播放.参数:是否播放中,true播放中,false没有在播放.
                        if (!player.isPlaying()) {
                            player.start();
                        } else {
                            Toast.makeText(PlayerManagerActivity.this, "视频正播放中", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        //暂停播放
                        if (player.isPlaying()) {//是否播放中,true播放中,false没有在播放.
                            player.pause();
                        } else {
                            Toast.makeText(PlayerManagerActivity.this, "视频不在播放中...", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 2:
                        //停止播放.
                        if (player.isPlaying()) {
                            player.stop();
                        } else {
                            Toast.makeText(PlayerManagerActivity.this, "视频不在播放中...", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 3:
                        //暂停播放,保留当前进度
                        player.onPause();
                        break;
                    case 4:
                        //恢复播放,读取进度记录
                        player.onResume();
                        break;
                    case 5:
                        //获取当前播放位置.(毫秒钱换成秒)
                        getCurrentPosition();
                        break;
                    case 6:
                        //获取视频总时间:s
                        getDuration();
                        break;
                    case 7:
                        //设备是否支持播放
                        support();
                        break;
                    case 8:
                        //横屏,非全屏,切换竖屏
                        player.onBackPressed();
                        //获取直播实例,参数true为直播.
                        //PlayerManager playerManager = player.live(true);
                        /*player.onComplete(new PlayerManager.OnCompleteListener() {
                            @Override
                            public void onComplete() {

                            }
                        });*/
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void support() {
        if (player.isPlayerSupport()) {
            Toast.makeText(PlayerManagerActivity.this, "支持播放!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PlayerManagerActivity.this, "不支持播放!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDuration() {
        int timeMillisecond = player.getDuration();
        tvTotal.setText(getTextTime(timeMillisecond));
        Toast.makeText(PlayerManagerActivity.this, "总时间:" + getTextTime(timeMillisecond), Toast.LENGTH_LONG).show();
    }

    private void getCurrentPosition() {
        //String currTime = getTextTime(player.getCurrentPosition());
        String currTime = player.generateTime(player.getCurrentPosition());
        Toast.makeText(PlayerManagerActivity.this, "当前进度:" + currTime, Toast.LENGTH_LONG).show();
        tvCurr.setText(currTime);
    }

    private String getTextTime(int timeMillisecond) {
        int duration = timeMillisecond / 1000;
        int timeSecond = duration % 60;
        int timeMinute = (duration - timeSecond) / 60;
        String totalTime = timeMinute + ":" + timeSecond;
        return totalTime;
    }

    //设置Activity的android:configChanges="orientation|keyboardHidden"时，切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.i(TAG, "onConfigurationChanged: " + tagSTR + "屏幕方向变换了");
    }

    private void initPlayer(boolean isHorizontal) {
        player = new PlayerManager(this);
        if (isHorizontal) {
            player.setFullScreenOnly(isHorizontal);
            player.playInFullScreen(isHorizontal);
        }
        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
        //player.setPlayerStateListener(this);
        player.play(url5);
    }

    private void initView() {
        tvCurr = (TextView) findViewById(R.id.tv_curr);
        tvTotal = (TextView) findViewById(R.id.tv_total);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止播放释放资源。
        player.onDestroy();
    }
}
