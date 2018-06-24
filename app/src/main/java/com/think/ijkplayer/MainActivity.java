package com.think.ijkplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.think.ijkplayer.media.IMediaController;
import com.think.ijkplayer.media.IRenderView;
import com.think.ijkplayer.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private IjkVideoView videoView;
    private String url5 = "http://1251603248.vod2.myqcloud.com/4c9adaa7vodtransgzp1251603248/30e0819d9031868223192061218/v.f40.mp4";
    private String TAG = this.getClass().getName();
    private String tagStr = "***--->***";
    protected IMediaPlayer.OnPreparedListener l;
    protected IMediaController controller = new IMediaController() {
        @Override
        public void hide() {
            Log.i(TAG, "hide: " + tagStr);
        }

        @Override
        public boolean isShowing() {
            Log.i(TAG, "isShowing: " + tagStr);
            return false;
        }

        @Override
        public void setAnchorView(View view) {
            Log.i(TAG, "setAnchorView: " + tagStr);
        }

        @Override
        public void setEnabled(boolean enabled) {
            Log.i(TAG, "setEnabled: " + enabled + tagStr);
        }

        @Override
        public void setMediaPlayer(MediaController.MediaPlayerControl player) {
            Log.i(TAG, "setMediaPlayer: " + player + tagStr);
        }

        @Override
        public void show(int timeout) {
            Log.i(TAG, "show: " + timeout + tagStr);
        }

        @Override
        public void show() {
            Toast.makeText(MainActivity.this, "111111", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "show: " + 0 + tagStr);
        }

        @Override
        public void showOnce(View view) {
            Log.i(TAG, "showOnce: " + 0 + tagStr);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_play).setOnClickListener(this);
        videoView = (IjkVideoView) findViewById(R.id.video_view);

        //videoView.setVideoURI(Uri.parse("http://zv.3gv.ifeng.com/live/zhongwen800k.m3u8"));

//        videoView.setVideoURI(Uri.parse(url5));

        //---/mnt/shared/Other/v.f40.mp4
//        videoView.start();
        /**
         * 参数aspectRatio(缩放参数)参见IRenderView的常量：IRenderView.AR_ASPECT_FIT_PARENT,
         IRenderView.AR_ASPECT_FILL_PARENT,
         IRenderView.AR_ASPECT_WRAP_CONTENT,
         IRenderView.AR_MATCH_PARENT,
         IRenderView.AR_16_9_FIT_PARENT,
         IRenderView.AR_4_3_FIT_PARENT
         */

        //设置媒体播放控制监听

//        videoView.setMediaController(controller);

        //注册一个回调函数，在视频预处理完成后调用。在视频预处理完成后被调用。
        // 此时视频的宽度、高度、宽高比信息已经获取到，此时可调用seekTo让视频从指定位置开始播放。
        l = new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                Log.i(TAG, "onPrepared: ");
            }
        };
        videoView.setOnPreparedListener(l);

        //播放完成回调,setOnCompletionListener
        videoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                Log.i(TAG, "onCompletion: 播放完成." + tagStr);
            }
        });
        //播放错误回调
        videoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                Log.i(TAG, "onError: 播放出错误了" + tagStr);
                return false;
            }
        });


    }

    private void show() {
        //弹出对话框.
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("ijkPlayer的配置!");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(new String[]{
                "0缩放适应父控件",
                "1缩放填充父控件",
                "2缩放包裹",
                "3缩放填充",
                "4缩放16_9",
                "5缩放4_3",
                "6获取总长度",
                "7当前播放位置",
                "8设置播放位置",
                "9获取缓冲百分比",
                "10设置视频路径",
                "11设置视频URI"

        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //设置缩放状态,适应父控件
                        videoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
                        //改变视频缩放状态
                        videoView.toggleAspectRatio();
                        break;
                    case 1:
                        //设置缩放状态,填充父控件
                        videoView.setAspectRatio(IRenderView.AR_ASPECT_FILL_PARENT);
                        videoView.toggleAspectRatio();
                        break;
                    case 2:
                        //设置缩放状态,包裹
                        videoView.setAspectRatio(IRenderView.AR_ASPECT_WRAP_CONTENT);
                        videoView.toggleAspectRatio();
                        break;
                    case 3:
                        //设置比例填充
                        videoView.setAspectRatio(IRenderView.AR_MATCH_PARENT);
                        videoView.toggleAspectRatio();
                        break;
                    case 4:
                        // //设置比例16.9
                        videoView.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
                        videoView.toggleAspectRatio();
                        break;
                    case 5:
                        // //设置比例4.3
                        videoView.setAspectRatio(IRenderView.AR_4_3_FIT_PARENT);
                        videoView.toggleAspectRatio();
                        break;
                    case 6:
                        //获取播放时间的总长度,742632***--->***
                        int duration = videoView.getDuration();
                        String msg = "onClick: 总长度是" + duration + tagStr;
                        Log.i(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        //获取当前播放位置,281412
                        int currentPosition = videoView.getCurrentPosition();
                        String msg1 = "onClick: 当前播放位置:" + currentPosition + tagStr;
                        Log.i(TAG, msg1);
                        Toast.makeText(MainActivity.this, msg1, Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        ////设置播放位置。单位毫秒
                        if (videoView.isPlaying()) {//判断是否在播放.
                            videoView.seekTo(1000 * 1000);
                        }
                        break;
                    case 9:
                        ////获取缓冲百分比。
                        int percentage = videoView.getBufferPercentage();
                        String msg2 = "onClick: 缓冲百分比" + percentage + tagStr;
                        Log.i(TAG, msg2);
                        Toast.makeText(MainActivity.this, msg2, Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        //"设置视频路径",
//                        String path = "/mnt/shared/Other/v.f40.mp4";
//                        videoView.setVideoURI(Uri.parse(path));
//                        File file = new File(path);
//                        videoView.setVideoURI(Uri.fromFile(file));
//                        videoView.setVideoPath(path);
//                        String vfile = Environment.getExternalStorageDirectory() + "/v.f40.mp4";
                        String vfile = Environment.getExternalStorageDirectory() + "/Pictures/v.f40.mp4";

                        Log.i(TAG, "onClick: " + tagStr + vfile);
                        videoView.setVideoPath(vfile);
                        // 设置VideView与MediaController建立关联
                        videoView.setMediaController(controller);
                        //videoView.setMediaController(new AndroidMediaController(MainActivity.this));

                        // 设置MediaController与VideView建立关联
                        controller.setMediaPlayer(videoView);
                        // 让VideoView获取焦点
                        videoView.requestFocus();
                        // 开始播放
                        videoView.start();
                        break;
                    case 11:
                        //"设置视频URI"
                        String url = "http://1251603248.vod2.myqcloud.com/4c9adaa7vodtransgzp1251603248/9689df9f9031868223247602092/v.f40.mp4";
                        videoView.setVideoURI(Uri.parse(url));
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_show) {
            show();
        } else if (v.getId() == R.id.btn_play) {
            play();
        }
    }

    private void play() {
        //弹出对话框.
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("ijkPlayer操作!");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(new String[]{"1播放视频",
                "2停止释放",
                "3封装后的ijkplayer",
                "4视频播放监听"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //播放
                        videoView.start();
                        break;
                    case 1:
                        //停止视频播放，并释放资源。
                        videoView.stopPlayback();
                        break;
                    case 2:
                        //使用封装后的ijkplayer.
                        MainActivity.this.startActivity(new Intent(MainActivity.this, PlayerManagerActivity.class));
                        break;
                    case 3:
                        //4视频播放监听
                        MainActivity.this.startActivity(new Intent(MainActivity.this, PlayerStateActivity.class));
                        break;

                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }


}
